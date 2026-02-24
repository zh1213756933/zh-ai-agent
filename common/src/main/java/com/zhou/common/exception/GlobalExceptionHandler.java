package com.zhou.common.exception;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.zhou.common.result.BaseResponse;
import com.zhou.common.result.GraceJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse<?> returnMyException(Exception e) {
        log.error("RuntimeException", e);
        return GraceJSONResult.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public GraceJSONResult returnMethodArgumentNotValid(MethodArgumentNotValidException e) {
//        BindingResult result = e.getBindingResult();
//        Map<String, String> map = getErrors(result);
//        return GraceJSONResult.errorMap(map);
//    }


    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError fieldError : errorList) {
            // 错误所对应的属性字段名
            String field = fieldError.getField();
            // 错误的信息
            String msg = fieldError.getDefaultMessage();
            map.put(field, msg);
        }
        return map;
    }

    /**
     * 业务流转异常，配合断言类使用
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> returnBusinessException(BusinessException e) {
        log.info("异常信息:{}", e.getMessage());
        if (e.getErrorCode() != null){
            return GraceJSONResult.error(e.getErrorCode());
        }else {
            return GraceJSONResult.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * BindException
     * 参数绑定异常 (类型不匹配、格式错误)
     */
//    @ExceptionHandler(BindException.class)
//    public GraceJSONResult<?> handler(BindException e) {
//        BindingResult result = e.getBindingResult();
//        Map<String, String> map = getErrors(result);
//        return GraceJSONResult.errorMap(map);
//    }

    /**
     * MissingServletRequestParameterException
     * 通过 @RequestParam 方法参数绑定异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse<?> handle(MissingServletRequestParameterException e) {
        String message = StrUtil.format("参数『{}』不能为空", e.getParameterName());
        return GraceJSONResult.error(ErrorCode.PARAMS_ERROR, message);
    }

    /**
     * HttpMessageNotReadableException
     * SpringBoot 解析 JSON 请求体异常 常见前端不传递默认""映射到Enum上，以及时间格式映射错误--需要传时间戳
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<?> handle(HttpMessageNotReadableException e) {
        Throwable cause = e.getMostSpecificCause();
        if (cause instanceof MismatchedInputException) {
            MismatchedInputException mismatchedEx = (MismatchedInputException) cause;
            String fieldName = mismatchedEx.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .orElse("未知字段");

            // 格式化错误信息
            String msg = "参数『{}』类型错误，该参数类型应为『{}』";
            if (mismatchedEx.getTargetType().isEnum()) {
                Object[] enumConstants = mismatchedEx.getTargetType().getEnumConstants();
                String enumValues = StrUtil.join("，", enumConstants);
                msg = StrUtil.format(msg + "，可选值为『{}』", fieldName, "枚举", enumValues);
            } else if (Date.class.isAssignableFrom(mismatchedEx.getTargetType())) {
                msg = StrUtil.format(msg + "，请传入时间戳", fieldName, "日期");
            } else {
                msg = StrUtil.format(msg, fieldName, mismatchedEx.getTargetType().getSimpleName());
            }
            if (cause instanceof InvalidFormatException) {
                InvalidFormatException invalidEx = (InvalidFormatException) cause;
                msg += StrUtil.format("，此次请求参数类型为『{}』，值为『{}』", invalidEx.getValue().getClass().getSimpleName(), invalidEx.getValue());
            }
            return GraceJSONResult.error(ErrorCode.PARAMS_ERROR, msg);
        }

        // 默认错误处理
        return GraceJSONResult.error(ErrorCode.PARAMS_ERROR, "JSON解析错误: " + e.getMessage());
    }

}
