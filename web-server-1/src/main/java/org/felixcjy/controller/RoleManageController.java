package org.felixcjy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.felixcjy.domain.entity.SysRole;
import org.felixcjy.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:37
 */
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
@Tag(name = "系统设置-角色管理")
public class RoleManageController {
    private final SysRoleService sysRoleService;

    @GetMapping("/{id}")
    @Operation(summary = "通过 id 获取角色信息")
    public SysRole get(@PathVariable("id") String id) {
        SysRole role = sysRoleService.getById(id);
        if (role.getRoleId() != null && role.getRoleId().startsWith("EMPTY_")) {
            return null; // 统一处理为空的情况
        }
        return role;
    }

    @PutMapping
    @Operation(summary = "通过 id 更新角色信息")
    public String update(@RequestBody SysRole role) {
        sysRoleService.update(role);
        return "更新成功";
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "通过 id 删除角色信息")
    public String delete(@PathVariable("id") String id) {
        sysRoleService.delete(id);
        return "删除成功";
    }
}
