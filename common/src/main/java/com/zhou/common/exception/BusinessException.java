package com.zhou.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    //调用是否成功
    private final Boolean success;

    private ErrorCode errorCode;


    public BusinessException(String message){
         super(message);
         this.code = 50000;
         this.success = false;
    }

    public BusinessException(Integer code,Boolean success,String message){
        super(message);
        this.code = code;
        this.success = success;
    }

    public BusinessException(ErrorCode errorCode){
        super("异常状态码为：" + errorCode.getCode()
                + "；具体异常信息为：" + errorCode.getMessage());
        this.code = errorCode.getCode();
        this.success = errorCode.getSuccess();
        this.errorCode = errorCode;
    }

}
