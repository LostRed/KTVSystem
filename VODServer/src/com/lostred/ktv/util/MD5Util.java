package com.lostred.ktv.util;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5码工具
 */
public class MD5Util {
    /**
     * 获取文件的md5码
     *
     * @param file 文件
     * @return md5码字符串
     */
    public static String getMD5(File file) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 利用MD5进行加密
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 判断用户密码是否正确
     *
     * @param newPassword 用户输入的密码
     * @param oldPassword 正确密码
     */
    public static boolean checkPassword(String newPassword, String oldPassword) throws NoSuchAlgorithmException {
        return EncoderByMd5(newPassword).equals(oldPassword);
    }
}
