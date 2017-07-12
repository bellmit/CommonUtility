/**
 * 
 */
package com.shandian.CommonUtility.msg;

import com.shandian.CommonUtility.msg.model.Message;

/**
 * Title:消息异步投递回调接口
 * 
 * Description: 在异步发送消息时，若需要自定义回调处理类，需要实现该接口
 * 
 * Copyrigt © 2011-2014 厦门神州鹰软件科技有限公司&掌通家园. All rights reserved.
 * 
 * 
 * @author chengyugui@szy.cn
 * 
 * 
 * @CreateDate: 2016年7月14日 下午5:30:14
 * 
 * @version:1.6.2
 */
public interface SendMsgCallBack {
    public void onSuccess(Message mMessage);

    public void onFail(Message mMessage, Exception ex);
}
