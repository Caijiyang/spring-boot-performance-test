package org.felixcjy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.felixcjy.domain.common.WebResult;
import org.felixcjy.domain.dto.SysUserDTO;
import org.felixcjy.domain.enums.ErrorType;
import org.felixcjy.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理 Controller
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 14:34
 */
@Slf4j
@RestController
@RequestMapping("/system/user")
@AllArgsConstructor
@Tag(name = "系统管理-用户管理")
public class UserManageController {
    private final SysUserService sysUserService;

    @GetMapping("/addUser")
    @Operation(summary = "新增用户", description = "模拟新增用户的接口，不涉及角色和当前登录用户")
    public WebResult<Object> addUser(@Valid @RequestBody SysUserDTO sysUserDTO) {
        try {
            sysUserService.addUser(sysUserDTO);
            return WebResult.success().withMessage("addUser success.");
        } catch (Exception e) {
            log.error("UserManageController addUser error,{}", e.getMessage(), e);
            return WebResult.fail(ErrorType.INTERNAL_SERVER_ERROR);
        }
    }
}
