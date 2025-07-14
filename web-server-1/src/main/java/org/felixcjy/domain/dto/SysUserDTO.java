package org.felixcjy.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 用户数据传输类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:45
 */
@Data
public class SysUserDTO {
    /** 用户ID */
    private String userId;

    /** 账号 */
    @NotNull(message = "账号不能为空")
    @Size(min = 5, max = 20, message = "账号长度必须在5到20之间")
    private String userAccount;

    /** 姓名 */
    @NotNull(message = "姓名不能为空")
    @Size(min = 2, max = 50, message = "姓名长度必须在2到50之间")
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 性别 */
    private String sex;

    /** 密码 */
    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6到20之间")
    private String password;

    /** 电话 */
    private String phoneNumber;

    /** 邮箱 */
    @Email(message = "邮箱格式不正确")
    private String email;

    /** 用户类型 */
    private String userType;

    /** 用户头像 */
    private String avatar;

    /** 备注 */
    private String remark;

    /** 账号状态（0 正常, 1 停用） */
    private String status;

    /** 角色列表 */
    private Set<SysRoleDTO> roles;

    /** 角色ID列表，新增角色时使用 */
    private Set<String> roleIds;
}
