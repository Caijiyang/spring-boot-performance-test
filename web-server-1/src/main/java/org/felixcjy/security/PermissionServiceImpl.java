package org.felixcjy.security;

import lombok.AllArgsConstructor;
import org.felixcjy.domain.dto.SysRolePermissionDTO;
import org.felixcjy.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限处理业务逻辑接口实现类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:12
 */
@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final SysRolePermissionService sysRolePermissionService;

    @Override
    public Map<String, List<String>> getRolePermissions() {
        List<SysRolePermissionDTO> rolePermissions = sysRolePermissionService.getRolePermissions();
        Map<String, List<String>> rolePermissionMap = new HashMap<>();

        for (SysRolePermissionDTO sysRolePermissionDTO : rolePermissions) {
            String role = "ROLE_" + sysRolePermissionDTO.getRoleSign(); // Spring Security 角色必须加 "ROLE_"
            rolePermissionMap.computeIfAbsent(sysRolePermissionDTO.getUrlPattern(), k -> new ArrayList<>()).add(role);
        }
        return rolePermissionMap;
    }
}