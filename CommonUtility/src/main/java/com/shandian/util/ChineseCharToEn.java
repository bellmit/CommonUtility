package com.shandian.util;

import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public final class ChineseCharToEn {
    private final static int[] li_SecPosValue = {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
    private final static String[] lc_FirstLetter = {"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x", "y", "z"};

    /**
     * 取得给定汉字串的首字母串,即声母串
     * 
     * @param str 给定汉字串
     * @return 声母串
     */
    public static String getAllFirstLetter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        String _str = "";
        for (int i = 0; i < str.length(); i++) {
            _str = _str + getFirstLetter(str.substring(i, i + 1));
        }
        return _str;
    }

    /**
     * 取得给定汉字的首字母,即声母
     * 
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GB2312", "ISO8859-1");
        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = chinese.charAt(0); // 汉字区码
            int li_PositionCode = chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= li_SecPosValue[i] && li_SecPosCode < li_SecPosValue[i + 1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else // 非汉字字符,如图形符号或ASCII码
            {
                chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }
        return chinese;
    }

    /**
     * 字符串编码转换
     * 
     * @param str 要转换编码的字符串
     * @param charsetName 原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName, String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println("获取拼音首字母：" + ChineseCharToEn.getAllFirstLetter("北京联席办"));
        // String Chines = "中华人民共和国";
        // System.out.println(Chines.replaceAll("\\pP", ""));
        // Pinyin Pinyin = new Pinyin();
        // System.out.println(Pinyin.Converter(Chines));
        // Collator 类是用来执行区分语言环境的 String 比较的，这里选择使用CHINA
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        String[] arrStrings = {"啥打法", "校车1214", "校车121402", "神州鹰专车Szy01", "阳光幼儿园校车"};
        // 使根据指定比较器产生的顺序对指定对象数组进行排序。
        Arrays.sort(arrStrings, comparator);
        for (int i = 0; i < arrStrings.length; i++) {
            System.out.println(arrStrings[i]);
        }
    }

    /**
     * 过滤相同的拼音
     * 
     * @param Chines
     */
    public void FiterSpell(String[] Chines) {
        if (Chines == null) {
            return;
        }
        HashMap<String, Integer> CHMap = new HashMap<String, Integer>();
        int rsize = Chines.length;
        for (int i = 0; i < rsize; i++) {
            if (Chines[i] == null) {
                continue;
            }
            if (CHMap.get(Chines[i]) == null) {
                CHMap.put(Chines[i], 1);
            } else {
                Chines[i] = null;
            }
        }
    }

    /**
     * 转化器。
     * 
     * @param Chines 需要转化的汉字
     * @return 转化之后的拼音，用逗号隔开
     */
    public String Converter(String Chines) {
        String PinYinName = "";
        char[] NameChar = Chines.toCharArray();
        HanyuPinyinOutputFormat DefaultFormat = new HanyuPinyinOutputFormat();
        DefaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        DefaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < NameChar.length; i++) {
            if (NameChar[i] > 128) {
                if (i > 0 && (NameChar[i - 1] <= 128)) {
                    PinYinName += ",";
                }
                try {
                    String[] TemPy = PinyinHelper.toHanyuPinyinStringArray(NameChar[i], DefaultFormat);
                    FiterSpell(TemPy);
                    if (TemPy != null && TemPy.length > 0) {
                        for (int j = 0; j < TemPy.length; j++) {
                            if (TemPy[j] != null) {
                                PinYinName += TemPy[j] + " ";
                            }
                        }
                        PinYinName = PinYinName.trim() + ",";
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                switch (NameChar[i]) {
                    case '1':
                        PinYinName += "yi,";
                        break;
                    case '2':
                        PinYinName += "er,";
                        break;
                    case '3':
                        PinYinName += "san,";
                        break;
                    case '4':
                        PinYinName += "si,";
                        break;
                    case '5':
                        PinYinName += "wu,";
                        break;
                    case '6':
                        PinYinName += "liu,";
                        break;
                    case '7':
                        PinYinName += "qi,";
                        break;
                    case '8':
                        PinYinName += "ba,";
                        break;
                    case '9':
                        PinYinName += "jiu,,";
                        break;
                    default:
                        PinYinName += NameChar[i];
                }
            }
        }
        if (PinYinName.length() > 0 && PinYinName.substring(PinYinName.length() - 1, PinYinName.length()).equals(",")) {
            return PinYinName.substring(0, PinYinName.length() - 1).toLowerCase();
        } else {
            return PinYinName.toLowerCase();
        }
    }
}
