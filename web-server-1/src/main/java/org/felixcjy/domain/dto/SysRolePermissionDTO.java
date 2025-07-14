package org.felixcjy.domain.dto;

import lombok.Data;

/**
 * 权限角色数据传输类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:20
 */
@Data
public class SysRolePermissionDTO {
    /** 权限ID */
    private String permissionId;

    /** 权限名称 */
    private String permissionName;

    /** URL 模式 */
    private String urlPattern;

    /** 角色ID */
    private String roleId;

    /** 角色标识 */
    private String roleSign;

    /** 角色名称 */
    private String roleName;
}
