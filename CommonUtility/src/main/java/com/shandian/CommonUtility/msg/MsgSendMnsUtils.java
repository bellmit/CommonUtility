/**
 * 
 */
package com.shandian.CommonUtility.msg;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.mns.client.AsyncCallback;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.shandian.CommonUtility.msg.model.Message;
import com.shandian.CommonUtility.msg.model.MessageParam;
import com.shandian.util.JsonUtils;
import com.shandian.util.MNSUtil;

/**
 * 
 * 神州鹰掌通家园项目组 Title:MNS消息发送工具类 Description:根据指定消息发送类型(同步/异步)进行消息推送 Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * 
 * @author 刘平贵 创建时间:2017年7月12日下午11:25:47
 *
 */
public class MsgSendMnsUtils {
    private static Logger logger = LoggerFactory.getLogger(MsgSendMnsUtils.class);
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
     * 方法描述：发送消息到MNS系统
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午11:26:26
     * @param param
     * @param message
     * @return
     * @version 1.0
     */
    public static boolean sendMsgByMNS(MessageParam param, Message message) {
        if (!checkMnsParam(param)) {
            return false;
        }
        if (param.getSendMsgType() == 1) {
            // 同步方式投递
            return syncSendMsgToMNS(param, message);
        } else {
            // 异步方式投递
            return asyncSendMsgToMNS(param, message);
        }
    }

    /**
     * 
     * 方法描述： 发送消息到MNS--同步方式
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午11:30:54
     * @param param
     * @param sMessage
     * @return
     * @version 1.0
     */
    private static boolean syncSendMsgToMNS(MessageParam param, Message sMessage) {
        String accessKey = param.getAccessKey();
        String secretKey = param.getSecretKey();
        String accountEndpoint = param.getMnsAccountEndpoint();
        String queueName = param.getMnsQueueName();
        String msgText = JsonUtils.getJson(sMessage);
        com.aliyun.mns.model.Message msg = new com.aliyun.mns.model.Message();
        msg.setMessageBody(msgText.getBytes());
        MNSClient client = null;
        SendMsgCallBack callback = param.getCallBack();
        try {
            long t1 = System.currentTimeMillis();
            int maxConnNum = param.getMnsMaxConnNum();
            client = MNSUtil.getMNSClient(accessKey, secretKey, accountEndpoint, maxConnNum);
            CloudQueue queue = client.getQueueRef(queueName);
            // 发送消息-同步方式
            logger.info("发送消息到MNS(同步)-msgId:{} msg:{}", sMessage.getMsgId(), sMessage.getMsgBody());
            msg = queue.putMessage(msg);
            logger.info("发送消息到MNS(同步)成功-msgId：{} mns-requestId:{} mns-messageId：{} ", sMessage.getMsgId(),
                        msg.getRequestId(), msg.getMessageId());
            long t2 = System.currentTimeMillis();
            if (t2 - t1 > 1000) {
                logger.error("发送消息到MNS(同步)耗时过长: " + (t2 - t1) + "  requestId:" + msg.getRequestId());
            }
        } catch (ServiceException e) {
            logger.error("msg:{}", msgText, e);
            logger.error("发送消息到MNS(同步)失败-MNS服务异常-ErrorCode:" + e.getErrorCode() + "  requestId:" + e.getRequestId(), e);
            if (callback != null) {
                callback.onFail(sMessage, e);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送消息到MNS(同步)异常:{}", e);
            if (callback != null) {
                callback.onFail(sMessage, e);
            }
            return false;
        }
        return true;
    }

    /**
     * 
     * 方法描述： 投递消息到MNS--异步方式
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午11:31:11
     * @param param
     * @param message
     * @return
     * @version 1.0
     */
    private static boolean asyncSendMsgToMNS(MessageParam param, Message message) {
        String accessKey = param.getAccessKey();
        String secretKey = param.getSecretKey();
        String accountEndpoint = param.getMnsAccountEndpoint();
        String queueName = param.getMnsQueueName();
        Integer maxConnNum = param.getMnsMaxConnNum();
        com.aliyun.mns.model.Message msg = new com.aliyun.mns.model.Message();
        String msgText = JsonUtils.getJson(message);
        msg.setMessageBody(msgText.getBytes());
        // 若外部未传入自定义回调处理函数,则使用默认回调处理函数
        SendMsgCallBack callback = param.getCallBack();
        try {
            long t1 = System.currentTimeMillis();
            MNSClient client = MNSUtil.getMNSClient(accessKey, secretKey, accountEndpoint, maxConnNum);
            CloudQueue queue = client.getQueueRef(queueName);
            SendMnsCallback mnsCallback = new SendMnsCallback(t1, accessKey, secretKey, accountEndpoint, queueName,
                        message, callback);
            // 发送消息-异步方式
            logger.info("发送消息到MNS(异步)-msgId:{} msg:{}", message.getMsgId(), message.getMsgBody());
            queue.asyncPutMessage(msg, mnsCallback);
        } catch (ServiceException e) {
            logger.error("msg:{}", msgText, e);
            logger.error("发送消息到MNS(异步)失败-MNS服务异常-ErrorCode:" + e.getErrorCode() + "  requestId:" + e.getRequestId(), e);
            if (callback != null) {
                callback.onFail(message, e);
            }
            return false;
        } catch (Exception e) {
            logger.error("发送消息到MNS(异步)异常,msg:{}", msgText, e);
            if (callback != null) {
                callback.onFail(message, e);
            }
            return false;
        }
        return true;
    }

    /**
     * 
     * 神州鹰掌通家园项目组 Title:内部类--MNS消息异步投递回调类 Description: Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
     * 
     * @author 刘平贵 创建时间:2017年7月12日下午11:32:35
     *
     */
    protected static class SendMnsCallback implements AsyncCallback<com.aliyun.mns.model.Message> {
        private long startTime;
        private String mAccessKey;
        private String mSecretKey;
        private String mEndpoint;
        private String mQueueName;
        private Message mMessage;
        private SendMsgCallBack smCallBack;

        public SendMnsCallback(Long startTime, String accessKey, String secretKey, String accountEndpoint,
                    String queueName, Message message, SendMsgCallBack smCallBack) {
            this.startTime = startTime;
            this.mAccessKey = accessKey;
            this.mSecretKey = secretKey;
            this.mEndpoint = accountEndpoint;
            this.mQueueName = queueName;
            this.mMessage = message;
            this.smCallBack = smCallBack;
        }

        /**
         * 
         * 方法描述：消息投递成功回调方法
         *
         * @author: 刘平贵
         * @date： 日期：2017年7月12日 时间：下午11:32:51
         * @param result
         * @version 1.0
         */
        @Override
        public void onSuccess(com.aliyun.mns.model.Message result) {
            if (smCallBack == null) {
                logger.info("发送消息到MNS(异步)成功-msgId：{} mns-requestId:{} mns-messageId：{} ", mMessage.getMsgId(),
                            result.getRequestId(), result.getMessageId());
            } else {
                smCallBack.onSuccess(mMessage);
            }
            long t2 = System.currentTimeMillis();
            if (t2 - startTime > 1000) {
                logger.warn("发送消息到MNS(异步)耗时过长 " + (t2 - startTime) + "  requestId:" + result.getRequestId());
            }
        }

        /**
         * 
         * 方法描述：消息投递失败回调方法
         *
         * @author: 刘平贵
         * @date： 日期：2017年7月12日 时间：下午11:34:09
         * @param ex
         * @version 1.0
         */
        @Override
        public void onFail(Exception ex) {
            if (ex instanceof ClientException) {
                ex.printStackTrace();
                logger.error("发送消息到MNS(异步)失败:网络异常,无法连接到MNS系统." + ex);
            } else if (ex instanceof ServiceException) {
                ServiceException se = (ServiceException) ex;
                se.printStackTrace();
                logger.error("requestId:{} error:{}", se.getRequestId(), se);
                if (se.getErrorCode().equals("QueueNotExist")) {
                    logger.error("发送消息到MNS(异步)失败:MNS队列不存在!");
                } else if (se.getErrorCode().equals("TimeExpired")) {
                    logger.error("发送消息到MNS(异步)失败:请求超时");
                }
            } else {
                logger.error("Unknown exception happened!" + ex);
                ex.printStackTrace();
            }
            if (smCallBack == null) {
                if (mMessage != null && mMessage.getMsgSendTimes() == 2) {
                    // 消息已经重复投递过一次,则不再进行投递，若后续要求该消息不允许丢失 则需要重写此处方法。
                    logger.error("重新投递消息到MNS失败-msg：{} ", JsonUtils.getJson(mMessage));
                } else if (mMessage != null && mMessage.getMsgSendTimes() < 2) {
                    // try {
                    // Thread.sleep(30000);// 休眠30秒
                    //
                    // } catch (InterruptedException e) {
                    // e.printStackTrace();
                    // }
                    logger.error("发送消息到MNS(异步)失败-msg：{} ", JsonUtils.getJson(mMessage));
                } else {
                    logger.error("发送消息到MNS(异步)-消息体为空！");
                }
            } else {
                smCallBack.onFail(mMessage, ex);
            }
        }
    }

    /**
     * 
     * 方法描述：入参校验
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午11:34:52
     * @param param
     * @return
     * @version 1.0
     */
    private static boolean checkMnsParam(MessageParam param) {
        String accountEndpoint = param.getMnsAccountEndpoint();
        String queueName = param.getMnsQueueName();
        if (StringUtils.isBlank(accountEndpoint)) {
            error = "MNS消息发送失败，未设置阿里云的accountEndpoint";
            logger.error(error);
            return false;
        }
        if (StringUtils.isBlank(queueName)) {
            error = "MNS消息发送失败，未设置阿里云的accountEndpoint";
            return false;
        }
        return true;
    }
}
