package org.felixcjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.felixcjy.domain.dto.SysRolePermissionDTO;
import org.felixcjy.domain.entity.SysRolePermission;

import java.util.List;

/**
 * 角色权限数据处理接口
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:39
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {
    /** 全表查询角色权限关联表 */
    List<SysRolePermissionDTO> getRolePermissions();
}
