package org.felixcjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.felixcjy.domain.entity.SysPermission;
import org.felixcjy.mapper.SysPermissionMapper;
import org.felixcjy.service.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 * 权限数据处理接口实现类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:14
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
}

