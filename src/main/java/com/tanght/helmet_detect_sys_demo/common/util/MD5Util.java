package com.tanght.helmet_detect_sys_demo.common.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @Title: MD5Util
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 21:51
 * @description:
 */


public class MD5Util {

    /**
     * 生成字符串的MD5哈希值
     *
     * @param input 输入的字符串
     * @return MD5哈希值
     */
    public static String getMD5(String input) {
        try {
            // 获取MD5 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算MD5哈希值
            md.update(input.getBytes());
            byte[] digest = md.digest();
            // 将字节转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
