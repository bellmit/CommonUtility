package com.shandian.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

public class ONSProducer {

	private static Map<String,Producer> producerMap = new  HashMap<String, Producer>();
	
	@Deprecated
	public static Producer init(String producerId){
		
		Producer producer = producerMap.get(producerId);
		
		if(producer == null){
			
			synchronized (ONSProducer.class) {
				
				   producer = producerMap.get(producerId);
				   if(producer != null) return producer;
				
				   Properties properties = new Properties();
			       properties.put(PropertyKeyConst.ProducerId, producerId);
			       properties.put(PropertyKeyConst.AccessKey, "j3NbAVkN6AQREnkh");
			       properties.put(PropertyKeyConst.SecretKey, "EdQ20FR6vrSln4fm02umCLMKyIqazE");
			       producer = ONSFactory.createProducer(properties);
			       //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
			       producer.start();
			       
			       producerMap.put(producerId, producer);
				
			}
			
		}
		
		return producer;
		
	}
	
	
	public static Producer initNew(String accessKey,String  secretKey, String producerId){
		
		Producer producer = producerMap.get(producerId+accessKey);
		
		if(producer == null){
			
			synchronized (ONSProducer.class) {
				
				   producer = producerMap.get(producerId+accessKey);
				   if(producer != null) return producer;
				
				   Properties properties = new Properties();
			       properties.put(PropertyKeyConst.ProducerId, producerId);
			       properties.put(PropertyKeyConst.AccessKey, accessKey);
			       properties.put(PropertyKeyConst.SecretKey, secretKey);
			       //设置发送超时时间为500ms
			       properties.put(PropertyKeyConst.SendMsgTimeoutMillis, 500);
			       producer = ONSFactory.createProducer(properties);
			       
			       //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
			       producer.start();
			       
			       producerMap.put(producerId+accessKey, producer);
				
			}
			
		}
		
		return producer;
		
	}
	
	@Deprecated
	public static Producer getProducer(String producerId){
		
		return init(producerId);
		
	}
	
	
	/**
	 * 
	 *方法描述：获取生产者对象
	 *@author 胡耀忠 hyz
	 *时间： 2016-3-25 下午04:24:23
	 *@param accessKey
	 *@param secretKey
	 *@param producerId
	 *@return
	 *@version 3.3
	 */
	public static Producer getProducer(String accessKey,String  secretKey,String producerId){
		
		return initNew(accessKey, secretKey, producerId);
		
	}
	
	/**
	 * 获取生产者对象--(可以指定发送超时时间)
	 *
	 * @author: ChengYuGui
	 * @date: 2016年11月10日下午3:29:07
	 * @param accessKey
	 * @param secretKey
	 * @param producerId
	 * @param timeout
	 * @return
	 * @version: 1.0.0
	 */
	public static Producer getProducer(String accessKey,String  secretKey,String producerId,long timeout){
		Producer producer = producerMap.get(producerId+accessKey);
		if(producer == null){
			synchronized (ONSProducer.class) {
				   producer = producerMap.get(producerId+accessKey);
				   if(producer != null) return producer;
				   Properties properties = new Properties();
			       properties.put(PropertyKeyConst.ProducerId, producerId);
			       properties.put(PropertyKeyConst.AccessKey, accessKey);
			       properties.put(PropertyKeyConst.SecretKey, secretKey);
			       if(timeout>0){
				       properties.put(PropertyKeyConst.SendMsgTimeoutMillis, timeout);
			       }
			       producer = ONSFactory.createProducer(properties);
			       //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
			       producer.start();
			       producerMap.put(producerId+accessKey, producer);
			}
		}
		return producer;
	}	
		
}
