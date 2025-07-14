package org.felixcjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.felixcjy.domain.dto.SysRolePermissionDTO;
import org.felixcjy.domain.entity.SysRolePermission;

import java.util.List;

/**
 * 角色权限关联表 Mapper
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:20
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
    /** 全表查询角色权限关联表 */
    List<SysRolePermissionDTO> getRolePermissions();
}
