package org.felixcjy.domain.common;

import lombok.Data;
import org.felixcjy.domain.enums.ErrorType;

import java.io.Serializable;

/**
 * HTTP 响应返回请求结果
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 10:07
 */
@Data
public class WebResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;       // 状态码（自定义或 HTTP 状态码）
    private String message; // 提示消息
    private T data;         // 业务数据
    private long timestamp; // 时间戳（自动生成）

    private WebResult() {
        this.timestamp = System.currentTimeMillis();
    }

    /** 成功响应（无数据） */
    public static <T> WebResult<T> success() {
        return success(null);
    }

    /** 成功响应 */
    public static <T> WebResult<T> success(T data) {
        WebResult<T> result = new WebResult<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /** 失败响应（自定义状态码和消息） */
    public static <T> WebResult<T> fail(int code, String message) {
        WebResult<T> result = new WebResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /** 失败响应（预定义错误类型，如业务异常） */
    public static <T> WebResult<T> fail(ErrorType errorType) {
        return fail(errorType.getCode(), errorType.getMessage());
    }

    /** 链式设置自定义消息 */
    public WebResult<T> withMessage(String message) {
        this.message = message;
        return this;
    }
}