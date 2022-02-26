package com.ywz.internal_common.dto;


import java.io.Serializable;


import com.ywz.internal_common.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通用返回值处理类，继承序列化接口
 *
 */
@Data
/**
 * 设置为true时，setter方法返回当前对象
 **/
@Accessors(chain = true)
@SuppressWarnings("all")
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = -2073390651767969040L;

    /**
    * 返回的结果统一为
    * {
    *   "code":1000,
    *   "message":"success",
    *   "data":T（可以是生成的验证码的response，可以是返回的其他数据的response）
    * }
    * */
    private int code;
    private String message;
    private T data;

    /**
     * 返回成功数据（status：1）
     *
     * @param data 数据内容
     * @param <T>  数据类型
     * @return ResponseResult实例
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    /**
     * 返回错误数据（status：500）
     *
     * @param data 错误内容
     * @param <T>  数据类型
     * @return ResponseResult实例
     */
    public static <T> ResponseResult fail(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.INTERNAL_SERVER_EXCEPTION.getCode()).setMessage(CommonStatusEnum.INTERNAL_SERVER_EXCEPTION.getValue()).setData(data);
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
