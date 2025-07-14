package org.felixcjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.felixcjy.domain.entity.SysPermission;

/**
 * 权限表 Mapper
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:07
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
}
