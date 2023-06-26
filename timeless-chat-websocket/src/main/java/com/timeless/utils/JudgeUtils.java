package com.timeless.utils;

/**
 * 用于各种判断
 *
 * @author timeless
 * @create 2023-06-22 23:55
 */
public class JudgeUtils {

    /**
     * @Description: 使用正则表达式匹配只包含0-9和小写a-z的字符串，且是5-10位
     * @Date: 2023/6/22 23:58
     * @Author: timeless
     */
    public static boolean usernameIsValid(String str) {
        String pattern = "^[0-9a-z]{5,10}$";
        return str.matches(pattern);
    }

}
