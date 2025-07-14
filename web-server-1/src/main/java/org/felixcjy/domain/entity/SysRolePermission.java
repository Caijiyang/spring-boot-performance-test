package org.felixcjy.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色-权限 关联表
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:14
 */
@Data
@TableName("sys_role_permission")
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 7100376351007206111L;

    /** 权限ID */
    @TableField("permission_id")
    private String permissionId;

    /** 角色ID */
    @TableField("role_id")
    private String roleId;
}
