package org.felixcjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.felixcjy.domain.dto.SysRolePermissionDTO;
import org.felixcjy.domain.entity.SysRolePermission;
import org.felixcjy.mapper.SysRolePermissionMapper;
import org.felixcjy.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色权限数据处理接口实现类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:40
 */
@Service
@AllArgsConstructor
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {
    private final SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRolePermissionDTO> getRolePermissions() {
        return sysRolePermissionMapper.getRolePermissions();
    }
}
