package com.shandian.CommonUtility.msg.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * 神州鹰掌通家园项目组 Title: 消息实体类 Description: 存放消息 Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * 
 * @author 刘平贵 创建时间:2017年7月12日下午5:05:02
 *
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 7353756232934453383L;
    private String msgId = UUID.randomUUID().toString().trim().replaceAll("-", "").toUpperCase(); // 消息ID
    private String msgType; // 消息类型
    private String msgBody; // 消息实体内容
    private Integer msgSendTimes;// 消息投递次数
    private String msgIp;// 消息来源IP

    public String getMsgIp() {
        return msgIp;
    }

    public void setMsgIp(String msgIp) {
        this.msgIp = msgIp;
    }

    private String msgSys;// 消息来源系统

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgSendTimes() {
        if (msgSendTimes == null) {
            return 0;
        }
        return msgSendTimes;
    }

    public void setMsgSendTimes(Integer msgSendTimes) {
        this.msgSendTimes = msgSendTimes;
    }

    public String getMsgSys() {
        return msgSys;
    }

    public void setMsgSys(String msgSys) {
        this.msgSys = msgSys;
    }
}
