package org.felixcjy.domain.dto;

import lombok.Data;

/**
 * 角色数据，仅包含必要的常用信息
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:46
 */
@Data
public class SysRoleDTO {
    /** 角色标识 */
    private String roleSign;

    /** 角色名称 */
    private String roleName;

    /** 显示顺序 */
    private int roleSort;

    /** 备注 */
    private String remark;

    /** 角色状态 0:正常 1:停用 */
    private char status;
}
