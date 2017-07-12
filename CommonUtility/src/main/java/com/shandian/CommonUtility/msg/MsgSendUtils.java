package com.shandian.CommonUtility.msg;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shandian.CommonUtility.msg.model.Message;
import com.shandian.CommonUtility.msg.model.MessageParam;

/**
 * 
 * 神州鹰掌通家园项目组 Title: 消息发送工具类 Description: Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * 
 * @author 刘平贵 创建时间:2017年7月13日上午12:06:52
 *
 */
public class MsgSendUtils {
    private static Logger logger = LoggerFactory.getLogger(MsgSendUtils.class.getName());
    // 执行结果
    private static String result;
    // 错误信息
    private static String error;

    /**
     * 发送消息
     *
     * @author: chengyugui@szy.cn
     * @param param
     * @param sMessage
     * @return
     * @taskID:TASK5446
     * @version: 1.6.2
     */
    public static boolean sendMsg(MessageParam param, Message sMessage) {
        return sendMsgService(param, sMessage);
    }

    /**
     * 消息发送服务,根据指定消息发送方式（MNS/ONS）和消息发送类型进行消息发送
     *
     * @author: chengyugui@szy.cn
     * @param param
     * @param sMessage
     * @return
     * @taskID:TASK5446
     * @version: 1.6.2
     */
    private static boolean sendMsgService(MessageParam param, Message sMessage) {
        // 1.校验消息
        if (!checkMsg(sMessage)) {
            return false;
        }
        // 2.校验消息服务连接参数
        if (!checkParam(param)) {
            return false;
        }
        // 3.发送消息
        String sendMsgWay = param.getSendMsgWay();
        if ("ONS".equalsIgnoreCase(sendMsgWay)) {
            // 使用ONS进行消息投递
            boolean result = MsgSendOnsUtils.sendMsgByONS(param, sMessage);
            if (!result) {
                error = MsgSendOnsUtils.getError();
                return false;
            }
        } else {
            // 使用MNS进行消息投递
            boolean result = MsgSendMnsUtils.sendMsgByMNS(param, sMessage);
            if (!result) {
                error = MsgSendOnsUtils.getError();
                return false;
            }
        }
        return true;
    }

    private static boolean checkMsg(Message msg) {
        if (msg == null) {
            error = "消息发送失败，传入消息为空";
            return false;
        }
        String msgId = msg.getMsgId();
        String msgBody = msg.getMsgBody();
        if (StringUtils.isBlank(msgId)) {
            error = "消息发送失败，传入消息中msgId为空";
            return false;
        }
        if (StringUtils.isBlank(msgBody)) {
            error = "消息发送失败，传入消息中消息体为空";
            return false;
        }
        return true;
    }

    /**
     * 入参校验
     * 
     * @author: 程聿贵
     * @date: 2016-6-3 下午04:13:03
     * @param accessKey
     * @param secretKey
     * @param accountEndpoint
     * @param queueName
     * @return
     * @taskID:
     * @version: 1.4.4
     */
    private static boolean checkParam(MessageParam param) {
        if (param == null) {
            error = "消息发送失败，消息访问参数param为空";
            return false;
        }
        String sendMsgWay = param.getSendMsgWay();
        if (StringUtils.isBlank(sendMsgWay)) {
            // 若未设置消息发送方式，则默认使用MNS消息服务技术进行消息发送
            sendMsgWay = "MNS";
            param.setSendMsgWay(sendMsgWay);
        }
        String accessKey = param.getAccessKey();
        String secretKey = param.getSecretKey();
        if (StringUtils.isBlank(accessKey)) {
            error = "消息发送失败，未设置阿里云的accessKey";
            logger.error(error);
            return false;
        }
        if (StringUtils.isBlank(secretKey)) {
            error = "消息发送失败，未设置阿里云的secretKey";
            logger.error(error);
            return false;
        }
        return true;
    }

    public static String getResult() {
        return result;
    }

    public static String getError() {
        return error;
    }
}
