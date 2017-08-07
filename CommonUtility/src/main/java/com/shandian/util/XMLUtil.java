package com.shandian.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 * 
 * @author miklchen
 * 
 */
public class XMLUtil {
    /**
     * 
     * 
     * 解析微信发来的请求（XML） 并解密
     * 
     * @param request
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXmlWithString(String string) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        if (string == null || string.trim().equals("")) {
            return map;
        }
        InputStream inputStream = new ByteArrayInputStream(string.getBytes("UTF-8"));
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            for (Iterator<Element> iterInner = e.elementIterator(); iterInner.hasNext();) {
                Element elementInner = iterInner.next();
                map.put(elementInner.getName(), elementInner.getText());
            }
            map.put(e.getName(), e.getText());
        }
        return map;
    }
}
