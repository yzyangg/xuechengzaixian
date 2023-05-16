package com.xuecheng.base.exception;


/**
 * The enum Common error.
 * 通用错误枚举
 *
 * @author Lenovo
 */
public enum CommonError {

    /**
     * Unknown error common error.
     */
    UNKNOWN_ERROR("执行过程异常，请重试。"),
    /**
     * Params error common error.
     */
    PARAMS_ERROR("非法参数"),
    /**
     * Object null common error.
     */
    OBJECT_NULL("对象为空"),
    /**
     * Query null common error.
     */
    QUERY_NULL("查询结果为空"),
    /**
     * Request null common error.
     */
    REQUEST_NULL("请求参数为空");

    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    private CommonError( String errMessage) {
        this.errMessage = errMessage;
    }

}