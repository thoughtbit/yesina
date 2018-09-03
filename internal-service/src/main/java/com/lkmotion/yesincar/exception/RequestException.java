package com.lkmotion.yesincar.exception;

/**
 * 接口请求异常
 *
 * @author ZhuBin
 * @date 2018/8/17
 */
public class RequestException extends RuntimeException {

    public RequestException() {
        super();
    }

    public RequestException(String message) {
        super(message);
    }
}
