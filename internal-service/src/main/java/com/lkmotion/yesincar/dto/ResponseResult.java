package com.lkmotion.yesincar.dto;

import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 通用返回值处理类
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unchecked")
public class ResponseResult<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    /**
     * 返回成功数据（status：200）
     *
     * @param data 数据内容
     * @param <T>  数据类型
     * @return ResponseResult实例
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(BusinessInterfaceStatus.SUCCESS.getCode()).setMessage(BusinessInterfaceStatus.SUCCESS.getValue()).setData(data);
    }

    /**
     * 返回成功数据（status：200）
     *
     * @param data 数据内容
     * @return ResponseResult实例
     */
    public static ResponseResult success(String data) {
        return new ResponseResult().setCode(BusinessInterfaceStatus.SUCCESS.getCode()).setMessage(BusinessInterfaceStatus.SUCCESS.getValue()).setData(data);
    }

    /**
     * 返回错误数据（status：500）
     *
     * @param data 错误内容
     * @param <T>  数据类型
     * @return ResponseResult实例
     */
    public static <T> ResponseResult fail(T data) {
        return new ResponseResult().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name()).setData(data);
    }

    /**
     * 返回错误数据（status：500）
     *
     * @param data 错误内容
     * @return ResponseResult实例
     */
    public static ResponseResult fail(String data) {
        return new ResponseResult().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name()).setData(data);
    }

    /**
     * 自定义返回错误数据
     *
     * @param code    错误代码
     * @param message 错误消息
     * @return ResponseResult实例
     */
    public static ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * @param code    错误代码
     * @param message 错误消息
     * @param data    错误内容
     * @return ResponseResult实例
     */
    public static ResponseResult fail(int code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }
}