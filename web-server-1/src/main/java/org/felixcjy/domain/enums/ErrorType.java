package org.felixcjy.domain.enums;

import lombok.Getter;

/**
 * 统一错误类型枚举
 * 用于预定义系统错误码和提示消息
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 10:09
 */
@Getter
public enum ErrorType {
    // 通用错误（1000-1999）
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数非法"),
    UNAUTHORIZED(401, "身份未认证"),
    FORBIDDEN(403, "无访问权限"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    // 业务自定义错误（2000-2999）
    USER_NOT_FOUND(2001, "用户不存在"),
    USER_NAME_DUPLICATE(2002, "用户名已存在"),
    ORDER_STATUS_INVALID(2101, "订单状态非法"),

    // 第三方服务错误（3000-3999）
    PAYMENT_FAILED(3001, "支付服务调用失败"),
    SMS_SEND_FAILED(3002, "短信发送失败");

    /** 错误码 */
    private final int code;

    /** 错误消息 */
    private final String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /** 根据错误码查找对应的 ErrorType */
    public static ErrorType of(int code) {
        for (ErrorType errorType : ErrorType.values()) {
            if (errorType.getCode() == code) {
                return errorType;
            }
        }
        return INTERNAL_SERVER_ERROR; // 默认返回未知错误
    }
}
