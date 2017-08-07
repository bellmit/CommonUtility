package com.shandian.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * 神州鹰掌通家园项目组 Title: 用于金钱的四则运行 Description: 提供较为精确的double数值的加减乘除 Copyright: Copyright (c) 2014 厦门神州鹰软件科技有限公司
 * 
 * @author 胡耀忠 创建时间:2014-5-15下午04:48:18
 * 
 */
public final class MoneyUtil {
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算
     * 
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * 
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * 
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入
     * 
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * 
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * 
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 
     * 方法描述：将钱格式化为10.00样子的字符串
     * 
     * @author: 胡耀忠 hyz
     * @date： 日期：2015-2-11 时间：下午03:11:07
     * @param money
     * @return
     * @version 1.0
     */
    public static String moneyStr(double money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(money);
    }

    /**
     * 
     * 方法描述：double数据比较
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月30日 时间：下午3:56:57
     * @param v1
     * @param v2
     * @return
     * @version 1.0
     */
    public static Integer compare(double v1, double v2) {
        BigDecimal data1 = new BigDecimal(String.valueOf(v1));
        BigDecimal data2 = new BigDecimal(String.valueOf(v2));
        return data1.compareTo(data2);
    }
}
