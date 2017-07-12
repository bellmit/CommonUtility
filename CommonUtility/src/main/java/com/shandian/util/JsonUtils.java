package com.shandian.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * 
 * 神州鹰掌通家园项目组 Title: Json处理工具类 Description: Copyright: Copyright (c) 2015 厦门神州鹰软件科技有限公司
 * 
 * @author 刘平贵 创建时间:2017年7月12日下午5:40:43
 *
 */
public class JsonUtils {
    /**
     * 
     * 方法描述：将对象属性转为json格式字符串
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:41:04
     * @param obj
     * @return
     * @version 1.0
     */
    public static String getJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 
     * 方法描述：将json格式字符串转为对象
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:41:27
     * @param json
     * @param cls
     * @return
     * @version 1.0
     */
    @SuppressWarnings("unchecked")
    public static Object readJson2Entity(String json, @SuppressWarnings("rawtypes") Class cls) {
        try {
            return JSON.<Object>parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * 方法描述： 同上，支持泛型
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:42:11
     * @param json
     * @param cls
     * @return
     * @version 1.0
     */
    public static <T> T readValue(String json, Class<? extends T> cls) {
        try {
            return JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * 方法描述：：将json数组字符串转化为数组
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:42:34
     * @param json
     * @param cls
     * @return
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List readJson2List(String json, Class cls) {
        try {
            return JSON.<Object>parseArray(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * 方法描述：对象转json格式，排除不需要的属性
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:43:24
     * @param obj
     * @param params
     * @return
     * @version 1.0
     */
    public static String getJsonExcludeProperties(Object obj, final String... params) {
        PropertyFilter filter = new PropertyFilter() {
            // 过滤不需要的字段
            @Override
            public boolean apply(Object source, String name, Object value) {
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals(name)) {
                        return false;
                    }
                }
                return true;
            }
        };
        return JSON.toJSONString(obj, filter);
    }

    /**
     * 
     * 方法描述：只获取需要的属性
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月12日 时间：下午5:43:59
     * @param obj
     * @param params
     * @return
     * @version 1.0
     */
    public static String getJsonIncludeProperties(Object obj, final String... params) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(obj.getClass(), params);
        return JSON.toJSONString(obj, filter);
    }
}
