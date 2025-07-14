package org.felixcjy.security;

import lombok.AllArgsConstructor;
import org.felixcjy.domain.dto.SysUserDTO;
import org.felixcjy.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:35
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    private final SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDTO sysUserDTO = sysUserService.getUserByUserAccount(username);
        if (sysUserDTO == null) {
            logger.error("loadUserByUsername can not find user: {}", username);
            throw new UsernameNotFoundException("用户不存在");
        }

        // 注意，这种方式的角色值必须有 ROLE_ 前缀！
        List<SimpleGrantedAuthority> roles = sysUserDTO.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleSign()))
                .collect(Collectors.toList());
        return User.withUsername(sysUserDTO.getUserAccount())
                .password(sysUserDTO.getPassword())
                .authorities(roles.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .build();
        /*
        // 简单管理角色可以这样
        return User.builder()
                .username(sysUserDTO.getUserAccount())
                .password(sysUserDTO.getPassword())  // 使用数据库存储的密码，Spring Security 会自动比较加密后的密码
                .roles("ADMIN")  // 这里假设所有用户都有 "USER" 角色
                .build();
        */
    }
}
