package com.shandian.util;

import java.io.UnsupportedEncodingException;

import org.springframework.security.crypto.codec.Base64;


/**
 * 神州鹰掌通家园项目组
 * Title:   
 * Description:   
 * Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * @author 涂燕东
 * 创建时间:2015-9-24下午03:56:54
 *
 */
public class Base64Util {
	
	/**
	 *方法描述：解码
	 *@author: 涂燕东
	 *@date： 日期：2015-9-24 时间：下午04:08:09
	 *@param encoded
	 *@return
	 *@version 3.1.4
	 */
    public static String decode(String encoded) {
    	try {
    		if(encoded == null || "".equals(encoded)) {
    			return "";
    		}
    		byte[] decode = Base64.decode(encoded.getBytes("UTF-8"));
			return new String(decode,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    /**
     *方法描述：编码
     *@author: 涂燕东
     *@date： 日期：2015-9-24 时间：下午04:07:58
     *@param en
     *@return
     *@version 3.1.4
     */
    public static String encode(String en) {
		try {
			byte[] bytes = en.getBytes("UTF-8");
			byte[] encode = Base64.encode(bytes);
	    	return new String(encode,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return "";
    }
 
    /**
     *方法描述：安全编码
     *@author: 涂燕东
     *@date： 日期：2015-9-24 时间：下午04:07:58
     *@param en
     *@return
     *@version 3.1.4
     */
    public static String safeEncode(String en) {
    	String ecd = encode(en);
    	String temp1 = ecd.replaceAll("\\+", "-");
    	String temp2 = temp1.replaceAll("/", "_");
    	return temp2;
    }
    
}
