package com.shandian.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.http.ClientConfiguration;

/**
 * 
 * 神州鹰掌通家园项目组 Title: MNS工具类 Description: Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * 
 * @author 刘平贵 创建时间:2017年7月12日下午5:55:21
 *
 */
public class MNSUtil {
    private static Logger logger = LoggerFactory.getLogger(MNSUtil.class);

    private MNSUtil() {}

    private static CloudAccount cloudAccount = null;

    /**
     *
     * 方法描述： 获取MNS客户端
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:55:36
     * @param accessKey
     * @param secretKey
     * @param accountEndpoint
     * @param maxConnNum
     * @return
     * @version 1.0
     */
    public static MNSClient getMNSClient(String accessKey, String secretKey, String accountEndpoint, int maxConnNum) {
        if (!checkParam(accessKey, secretKey, accountEndpoint)) {
            return null;
        }
        if (maxConnNum < 1) {
            // 默认1000个连接
            maxConnNum = 1000;
        }
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // 单个CloudAccount的最大连接数
        clientConfiguration.setMaxConnections(maxConnNum);
        clientConfiguration.setMaxConnectionsPerRoute(maxConnNum);
        synchronized (CloudAccount.class) {
            if (cloudAccount != null) {
                MNSClient mnsClient = cloudAccount.getMNSClient();
                return mnsClient;
            }
            cloudAccount = new CloudAccount(accessKey, secretKey, accountEndpoint, clientConfiguration);
            MNSClient mnsClient = cloudAccount.getMNSClient();
            return mnsClient;
        }
    }

    public static MNSClient getMNSClient(String accessKey, String secretKey, String accountEndpoint) {
        return getMNSClient(accessKey, secretKey, accountEndpoint, 0);
    }

    /**
     * 
     * 方法描述：入参校验
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:55:59
     * @param accessKey
     * @param secretKey
     * @param accountEndpoint
     * @return
     * @version 1.0
     */
    private static boolean checkParam(String accessKey, String secretKey, String accountEndpoint) {
        if (StringUtils.isBlank(accessKey)) {
            logger.error("MNS消息发送失败，未设置阿里云的accessKey");
            return false;
        }
        if (StringUtils.isBlank(secretKey)) {
            logger.error("MNS消息发送失败，未设置阿里云的secretKey");
            return false;
        }
        if (StringUtils.isBlank(accountEndpoint)) {
            logger.error("MNS消息发送失败，未设置阿里云的accountEndpoint");
            return false;
        }
        return true;
    }

    /**
     * 
     * 方法描述：关闭连接
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:56:19
     * @param mnsClient
     * @version 1.0
     */
    public static void closeMnsClient(MNSClient mnsClient) {
        if (mnsClient != null) {
            mnsClient.close();
        }
    }
}
