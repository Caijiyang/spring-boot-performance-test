package org.felixcjy.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:45
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 5434720000412710457L;

    /** 用户ID */
    @TableId("user_id")
    private String userId;

    /** 账号 */
    @TableField("user_account")
    private String userAccount;

    /** 姓名 */
    @TableField("user_name")
    private String userName;

    /** 昵称 */
    @TableField("nick_name")
    private String nickName;

    /** 性别 0:男 1:女 2:保密 3:未知 */
    @TableField("sex")
    private String sex;

    /** 密码 */
    @TableField("password")
    private String password;

    /** 电话 */
    @TableField("phone_number")
    private String phoneNumber;

    /** 邮箱 */
    @TableField("email")
    private String email;

    /** 用户类型 0:普通用户 */
    @TableField("user_type")
    private String userType;

    /** 用户头像 */
    @TableField("avatar")
    private String avatar;

    /** 备注 */
    @TableField("remark")
    private String remark;

    /** 账号状态（0 正常, 1 停用） */
    @TableField("status")
    private String status;

    /** 删除标识（0 正常, 1 删除） */
    @TableField("del_flag")
    private String delFlag;

    /** 创建者 */
    @TableField("create_user_id")
    private String createdUserId;

    /** 创建时间 */
    @TableField("create_date_time")
    private LocalDateTime createDateTime;

    /** 更新者 */
    @TableField("update_user_id")
    private String updateUserId;

    /** 更新时间 */
    @TableField("update_date_time")
    private LocalDateTime updateDateTime;

    /** 最后登录日期 */
    @TableField("last_login_date_time")
    private LocalDateTime lastLoginDateTime;
}
