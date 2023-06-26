package com.timeless.enums;

import lombok.Data;

/**
 * @author timeless
 * @create 2022-12-05 14:56
 */
public enum AppHttpCodeEnum {

    SUCCESS(1000, "成功"),
    SYSTEM_ERROR(1001, "系统错误"),


    // 用户相关
    REGISTER_FAIL(2001, "注册失败"),
    USERNAME_HAS_BEEN_REGISTERED(2002, "用户名已被注册"),
    USER_NOT_EXIST(2003, "用户不存在"),
    PASSWORD_ERROR(2004, "密码错误"),
    APPLY_FAIL(2005, "申请失败"),



    //校验相关
    PARAMS_ERROR(3001, "输入参数错误");

    final int code;
    final String msg;

    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
