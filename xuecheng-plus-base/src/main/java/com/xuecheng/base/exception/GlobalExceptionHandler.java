package com.xuecheng.base.exception;

/**
 * @author yzy
 * @version 1.0
 * @description TODO
 * @date 2023/5/14 20:28
 */

import lombok.extern.slf4j.Slf4j;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.StringJoiner;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(XueChengPlusException e) {
        log.error("【系统异常】{}", e.getErrMessage(), e);
        return new RestErrorResponse(e.getErrMessage());

    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e) {

        log.error("【系统异常】{}", e.getMessage(), e);

        return new RestErrorResponse(CommonError.UNKNOWN_ERROR.getErrMessage());

    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            log.error("【参数校验异常】{}", fieldError.getDefaultMessage());
        });
        StringJoiner stringJoiner = new StringJoiner(",");
        bindingResult.getFieldErrors().forEach(fieldError -> {
            stringJoiner.add(fieldError.getDefaultMessage());
        });
        String s = stringJoiner.toString();

        return new RestErrorResponse(s);

    }
}