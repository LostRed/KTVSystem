package com.lostred.ktv.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取汉字拼音首字母工具
 */
public class InitialUtil {
    /**
     * 将字符串转成拼音，取首字母或全拼
     *
     * @param str 字符串
     * @return 拼音首字母
     */
    public static String convertToInitial(String str) {
        String regExp = "^[\u4E00-\u9FFF]+$";
        StringBuilder sb = new StringBuilder();
        if (str == null || "".equals(str.trim())) {
            return "";
        }
        for (int i = 0; i < str.length(); i++) {
            char unit = str.charAt(i);
            //是汉字，则转拼音
            if (match(String.valueOf(unit), regExp)) {
                String pinYin = convertSingleCharacterToPinyin(unit).toUpperCase();
                sb.append(pinYin.charAt(0));
            } else {
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    /**
     * 将单个汉字转成拼音
     *
     * @param character 汉字字符
     * @return 拼音
     */
    private static String convertSingleCharacterToPinyin(char character) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        StringBuilder sb = new StringBuilder();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(character, outputFormat);
            sb.append(res[0]);//对于多音字，只用第一个拼音
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    /***
     * 根据字符和正则表达式进行匹配
     *
     * @param str 源字符串
     * @param regex 正则表达式
     *
     * @return true：匹配成功  false：匹配失败
     */
    private static boolean match(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
