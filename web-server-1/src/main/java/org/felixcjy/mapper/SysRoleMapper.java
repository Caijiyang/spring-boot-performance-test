package org.felixcjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.felixcjy.domain.entity.SysRole;

/**
 * 角色表 Mapper
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:03
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    IPage<SysRole> getRoleList(@Param("page") Page<SysRole> sysRolePage);
}
