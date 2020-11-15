package com.hualxx.crowd.util;


import com.hualxx.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author hual
 * @create 2020-11-12 2:36
 */


public class Crowdutil {

    /**
     * 对明文字符串进行MD5加密
     * @param source 传入的明文字符串
     * @return 加密结果
     */
    public static String md5(String source){

        //1.判断source是否有效
        if(source == null || source.length() == 0){

            //2.如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        String algorithm = "md5";

        try {
            //3.获取MessageDigest对象
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //4.获取字符串对应的字节数组
            byte[] input = source.getBytes();

            //5.执行加密
            byte[] output = messageDigest.digest(input);

            //6.创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);

            //7.按照16进制将bigInteger的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix);

            return encoded;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断当前请求是否为Ajax请求
     *
     * @param request 请求对象
     * @return true：当前请求时Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {

        //1.获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String XRequestHeader = request.getHeader("X-Requested-With");

        //2.判断
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (XRequestHeader != null && XRequestHeader.equals("XMLHttpRequest"));
    }
}
