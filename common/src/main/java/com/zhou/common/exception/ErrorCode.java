package com.zhou.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(200, true, "ok"),
    PARAMS_ERROR(400, false, "请求参数错误"),
    NOT_LOGIN_ERROR(401, false, "未登录"),
    NO_AUTH_ERROR(40101, false, "无权限"),
    NOT_FOUND_ERROR(404, false, "请求数据不存在"),
    FORBIDDEN_ERROR(403, false, "禁止访问"),
    SYSTEM_ERROR(500, false, "系统内部异常"),
    OPERATION_ERROR(501,false, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 是否成功
     */
    private final Boolean success;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

}

