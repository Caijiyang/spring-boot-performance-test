package org.felixcjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.felixcjy.domain.dto.SysUserDTO;
import org.felixcjy.domain.entity.SysUser;
import org.felixcjy.domain.entity.SysUserRole;
import org.felixcjy.mapper.SysUserMapper;
import org.felixcjy.service.SysUserRoleService;
import org.felixcjy.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 用户数据处理接口实现类
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 14:07
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private final PasswordEncoder passwordEncoder;
    private final SysUserMapper sysUserMapper;
    private final SysUserRoleService sysUserRoleService;

    @Override
    public SysUserDTO getUserByUserAccount(String userAccount) {
        return sysUserMapper.getUserByUserAccount(userAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        sysUser.setUserId(UUID.randomUUID().toString().replace("-", ""));
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setStatus("0");
        sysUser.setCreatedUserId("system");
        sysUser.setCreateDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser);
        if (CollectionUtils.isNotEmpty(sysUserDTO.getRoleIds())) {
            List<SysUserRole> sysUserRoles = new ArrayList<>();
            for (String roleId : sysUserDTO.getRoleIds()) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getUserId());
                sysUserRole.setRoleId(roleId);
                sysUserRoles.add(sysUserRole);
            }
            sysUserRoleService.saveBatch(sysUserRoles);
        } else {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            // 1 为普通角色 ID
            sysUserRole.setRoleId("1");
            sysUserRoleService.save(sysUserRole);
        }
    }
}
