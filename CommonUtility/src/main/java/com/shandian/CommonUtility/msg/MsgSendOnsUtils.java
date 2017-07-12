package com.shandian.CommonUtility.msg;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.shandian.CommonUtility.msg.model.Message;
import com.shandian.CommonUtility.msg.model.MessageParam;
import com.shandian.util.JsonUtils;
import com.shandian.util.ONSProducer;

/**
 * 
 * 神州鹰掌通家园项目组 Title:ONS消息发送工具类 Description:根据指定消息发送类型(同步/异步)进行消息推送 Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * 
 * @author 刘平贵 创建时间:2017年7月12日下午11:43:05
 *
 */
public class MsgSendOnsUtils {
    private static Logger logger = LoggerFactory.getLogger(MsgSendOnsUtils.class);
    // 执行结果
    private static String result;
    // 错误信息
    private static String error;

    public static String getResult() {
        return result;
    }

    public static String getError() {
        return error;
    }

    /**
     * 
     * 方法描述：发送消息到ONS系统
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午11:44:09
     * @param param
     * @param sMessage
     * @return
     * @version 1.0
     */
    public static boolean sendMsgByONS(MessageParam param, Message sMessage) {
        if (!checkOnsParam(param)) {
            return false;
        }
        if (param.getSendMsgType() == 1) {
            // 同步方式投递
            return syncSendMsgToONS(param, sMessage);
        } else {
            // 异步方式投递
            return asyncSendMsgToONS(param, sMessage);
        }
    }

    private static boolean syncSendMsgToONS(MessageParam param, Message sMessage) {
        String topic = param.getOnsTopic();
        String pid = param.getOnsPid();
        String accessKey = param.getAccessKey();
        String secretKey = param.getSecretKey();
        String tags = param.getOnsTags();
        try {
            com.aliyun.openservices.ons.api.Message msg = new com.aliyun.openservices.ons.api.Message(topic, tags,
                        JsonUtils.getJson(sMessage).getBytes("GBK"));
            msg.setKey(tags + "_" + sMessage.getMsgType());
            long t1 = System.currentTimeMillis();
            com.aliyun.openservices.ons.api.Producer producer = ONSProducer.getProducer(accessKey, secretKey, pid);
            long t2 = System.currentTimeMillis();
            SendResult result = producer.send(msg);
            long t3 = System.currentTimeMillis();
            if (t3 - t1 > 1000) {
                String messageId = "";
                if (result != null && result.getMessageId() != null) {
                    messageId = result.getMessageId();
                }
                logger.error("发送消息到ONS耗时过长，总耗时:{} 获取连接耗时：{} 发送耗时：{},messageId:{}", (t3 - t1), (t2 - t1), (t3 - t2),
                            messageId);
            }
        } catch (Exception e) {
            logger.error("发送消息到ONS失败:{}", e);
            return false;
        }
        return true;
    }

    /**
     *
     * @author: chengyugui@szy.cn
     * @param param
     * @param mMessage
     * @return
     * @taskID:TASK5446
     * @version: 1.6.2
     */
    private static boolean asyncSendMsgToONS(MessageParam param, Message message) {
        String topic = param.getOnsTopic();
        String pid = param.getOnsPid();
        String accessKey = param.getAccessKey();
        String secretKey = param.getSecretKey();
        String tags = param.getOnsTags();
        SendMsgCallBack callback = param.getCallBack();
        long t1 = System.currentTimeMillis();
        com.aliyun.openservices.ons.api.Message onsMsg = new com.aliyun.openservices.ons.api.Message(topic, tags,
                    JsonUtils.getJson(message).getBytes());
        com.aliyun.openservices.ons.api.Producer producer = ONSProducer.getProducer(accessKey, secretKey, pid);
        // 异步发送消息, 发送结果通过callback返回给客户端。
        OnsSendCallback onsSendCallback = new OnsSendCallback(t1, message, callback);
        producer.sendAsync(onsMsg, onsSendCallback);
        return true;
    }

    /**
     * 
     * 神州鹰掌通家园项目组 Title: 内部类，处理异步发送消息回调业务 Description: Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
     * 
     * @author 刘平贵 创建时间:2017年7月13日上午12:05:21
     *
     */
    protected static class OnsSendCallback implements SendCallback {
        private long startTime;
        private Message mMessage;
        private SendMsgCallBack smCallBack;

        public OnsSendCallback(Long startTime, Message message, SendMsgCallBack smCallBack) {
            this.startTime = startTime;
            this.mMessage = message;
            this.smCallBack = smCallBack;
        }

        @Override
        public void onSuccess(final SendResult sendResult) {
            if (smCallBack == null) {
                // 消费发送成功
                System.out.println("send message success. topic=" + sendResult.getTopic() + ", msgId="
                            + sendResult.getMessageId());
                long t2 = System.currentTimeMillis();
                if (t2 - startTime > 1000) {
                    logger.warn("发送消息到ONS(异步)耗时过长 " + (t2 - startTime) + "  requestId:" + sendResult.getMessageId());
                }
            } else {
                smCallBack.onSuccess(mMessage);
            }
        }

        @Override
        public void onException(OnExceptionContext context) {
            // 消息发送失败
            if (smCallBack == null) {
                System.out.println("send message failed. topic=" + context.getTopic() + ", msgId="
                            + context.getMessageId());
            } else {
                smCallBack.onFail(mMessage, context.getException());
            }
        }
    }

    /**
     * 
     * 方法描述：入参校验
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月13日 时间：上午12:05:37
     * @param param
     * @return
     * @version 1.0
     */
    private static boolean checkOnsParam(MessageParam param) {
        String tags = param.getOnsTags();
        String topic = param.getOnsTopic();
        String pid = param.getOnsPid();
        if (StringUtils.isBlank(tags)) {
            error = "ONS消息发送失败，未配置ONS的tags参数";
            logger.error(error);
            return false;
        }
        if (StringUtils.isBlank(topic)) {
            error = "ONS消息发送失败，未配置ONS的topic参数";
            return false;
        }
        if (StringUtils.isBlank(pid)) {
            error = "ONS消息发送失败，未配置ONS的pid参数";
            return false;
        }
        return true;
    }
}
