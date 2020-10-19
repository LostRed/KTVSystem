package com.lostred.ktv.test;

import com.lostred.ktv.util.MD5Util;

import java.security.NoSuchAlgorithmException;

public class Password {
    public static void main(String[] args) {
        String str = "123456";
        try {
            String newString = MD5Util.EncoderByMd5(str);
            System.out.println(newString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
