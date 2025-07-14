package org.felixcjy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.felixcjy.domain.entity.SysRole;

/**
 * 角色数据处理接口
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:13
 */
public interface SysRoleService extends IService<SysRole> {
    /** 数据层简单分页查询角色列表 */
    IPage<SysRole> getRoleList(int pageNum, int pageSize);

    /** 根据 ID 获取角色信息 */
    SysRole getById(String roleId);

    /** 更新角色信息 */
    void update(SysRole role);

    /** 通过角色 ID 删除角色*/
    void delete(String roleId);
}
