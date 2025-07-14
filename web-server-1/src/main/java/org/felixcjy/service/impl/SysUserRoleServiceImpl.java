package org.felixcjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.felixcjy.domain.entity.SysUserRole;
import org.felixcjy.mapper.SysUserRoleMapper;
import org.felixcjy.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色数据处理接口实现类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:12
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
}