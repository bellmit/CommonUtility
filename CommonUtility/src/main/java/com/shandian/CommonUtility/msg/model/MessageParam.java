package com.shandian.CommonUtility.msg.model;

import java.io.Serializable;

import com.shandian.CommonUtility.msg.SendMsgCallBack;

/**
 * 
 * 神州鹰掌通家园项目组 Title: 消息服务参数配置类 Description: 该类为访问ONS或者MNS消息服务的必要验证参数存放容器 Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * 
 * @author 刘平贵 创建时间:2017年7月12日下午5:12:22
 *
 */
public class MessageParam implements Serializable {
    private static final long serialVersionUID = -8787462567772580797L;
    private String accessKey;
    private String secretKey;
    private String sendMsgWay;// 发送方式 ONS-使用ONS投递消息; MNS-使用MNS投递消息
    private Integer sendMsgType;// 发送类型 1-同步投递 2-异步投递
    private String mnsAccountEndpoint;// mns
    private String mnsQueueName;// mns队列名
    private Integer mnsMaxConnNum;// 单个mns账号最大的连接数
    private String onsTags;
    private String onsTopic;
    private String onsPid;
    private SendMsgCallBack callBack;// 若为异步方式投递消息，需要自定义回调函数，需要重新设定该回调参数
    private String msgIp;// 消息来源IP
    private String msgSys;// 消息来源系统 Vedio-视频服务 ZTH-中转 HMS-幼教后台 Message-消息服务 Score-积分服务

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSendMsgWay() {
        if (sendMsgWay == null || sendMsgWay.equals("")) {
            return "MNS";// 若未设置使用方式， 则默认使用MNS方式进行消息投递
        }
        return sendMsgWay;
    }

    public void setSendMsgWay(String sendMsgWay) {
        this.sendMsgWay = sendMsgWay;
    }

    public Integer getSendMsgType() {
        if (sendMsgType == null) {
            return 2;// 默认使用异步投递方式进行消息投递
        } else if (sendMsgType == 1 || sendMsgType == 2) {
            return sendMsgType;
        } else {
            return 2;// 若设置的值 非法，则使用默认投递方式--异步投递方式
        }
    }

    public void setSendMsgType(Integer sendMsgType) {
        this.sendMsgType = sendMsgType;
    }

    public SendMsgCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(SendMsgCallBack callBack) {
        this.callBack = callBack;
    }

    public String getMsgSys() {
        return msgSys;
    }

    public void setMsgSys(String msgSys) {
        this.msgSys = msgSys;
    }

    public String getMnsQueueName() {
        return mnsQueueName;
    }

    public Integer getMnsMaxConnNum() {
        if (mnsMaxConnNum == null) {
            mnsMaxConnNum = 100;
        }
        return mnsMaxConnNum;
    }

    public String getOnsTags() {
        return onsTags;
    }

    public String getOnsTopic() {
        return onsTopic;
    }

    public String getOnsPid() {
        return onsPid;
    }

    public String getMsgIp() {
        return msgIp;
    }

    public String getMnsAccountEndpoint() {
        return mnsAccountEndpoint;
    }

    public void setMnsAccountEndpoint(String mnsAccountEndpoint) {
        this.mnsAccountEndpoint = mnsAccountEndpoint;
    }

    public void setMnsQueueName(String mnsQueueName) {
        this.mnsQueueName = mnsQueueName;
    }

    public void setMnsMaxConnNum(Integer mnsMaxConnNum) {
        this.mnsMaxConnNum = mnsMaxConnNum;
    }

    public void setOnsTags(String onsTags) {
        this.onsTags = onsTags;
    }

    public void setOnsTopic(String onsTopic) {
        this.onsTopic = onsTopic;
    }

    public void setOnsPid(String onsPid) {
        this.onsPid = onsPid;
    }

    public void setMsgIp(String msgIp) {
        this.msgIp = msgIp;
    }
}
