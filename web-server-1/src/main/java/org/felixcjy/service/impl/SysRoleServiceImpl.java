package org.felixcjy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.felixcjy.domain.entity.SysRole;
import org.felixcjy.mapper.SysRoleMapper;
import org.felixcjy.service.SysRoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 角色数据处理接口实现类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:13
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    private final SysRoleMapper sysRoleMapper;

    @Override
    public IPage<SysRole> getRoleList(int pageNum, int pageSize) {
        Page<SysRole> sysRolePage = new Page<>(pageNum, pageSize);
        return sysRoleMapper.getRoleList(sysRolePage);
    }

    /**
     * 查询角色，优先从缓存中获取
     * 使用 @Cacheable(sync = true)，防止缓存击穿
     * 缓存空对象（假对象）来防止穿透
     */
    @Cacheable(value = "spring-boot-performance-test::role", key = "#roleId", sync = true)
    @Override
    public SysRole getById(String roleId) {
        SysRole role = sysRoleMapper.selectById(roleId);

        if (role == null) {
            // 返回一个标记为“空”的假对象，写入缓存，防止穿透
            SysRole emptyRole = new SysRole();
            emptyRole.setRoleId("EMPTY_" + roleId); // 你可以设定一个特殊标识
            return emptyRole;
        }
        return role;
    }

    /**
     * 更新角色，同时更新缓存
     */
    @CachePut(value = "spring-boot-performance-test::role", key = "#role.roleId")
    @Override
    public void update(SysRole role) {
        sysRoleMapper.updateById(role);
    }

    /**
     * 删除角色，同时清除缓存
     */
    @CacheEvict(value = "spring-boot-performance-test::role", key = "#roleId")
    @Override
    public void delete(String roleId) {
        sysRoleMapper.deleteById(roleId);
    }
}
