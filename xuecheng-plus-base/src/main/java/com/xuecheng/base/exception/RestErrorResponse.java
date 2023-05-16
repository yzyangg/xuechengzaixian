package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author yzy
 * @version 1.0
 * @description TODO
 * @date 2023/5/14 19:17
 */

/**
 * 错误响应参数包装
 *
 * @author Lenovo
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}