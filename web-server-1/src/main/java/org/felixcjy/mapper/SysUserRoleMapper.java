package org.felixcjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.felixcjy.domain.entity.SysUserRole;

/**
 * 用户角色表 Mapper
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:08
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
