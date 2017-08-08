package com.shandian.util;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * bean工具类
 * 
 * @author Gyk
 * 
 */
public class BeanUtils {
    /**
     * 将obj类转为Map<String,Object>
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!StringUtils.equals(name, "class")) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 将obj类转为Map<String,String>
     * 
     * @param obj
     * @return
     */
    public static Map<String, String> beanToMapForString(Object obj) {
        Map<String, Object> tempMap = BeanUtils.beanToMap(obj);
        Map<String, String> resultMap = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
            try {
                resultMap.put(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : "");
            } catch (Exception e) {
                resultMap.put(entry.getKey(), "");
            }
        }
        return resultMap;
    }

    /**
     * 从源对象复制属性到目标对象，可封装各种类型转换器（Date,Bigdecimal等）
     * 
     * @param dest
     * @param orig
     */
    public static void copyBeanPorperties(Object dest, Object orig) {
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        ConvertUtils.register(new DateConverter(), java.util.Date.class);
        ConvertUtils.register(new BigIntegerConverter(), java.math.BigInteger.class);
        ConvertUtils.register(new TimestampConverter(), java.sql.Timestamp.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        try {
            beanUtilsBean.copyProperties(dest, orig);
        } catch (Exception e) {}
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
