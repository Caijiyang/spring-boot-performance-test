package org.felixcjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.felixcjy.domain.dto.SysUserDTO;
import org.felixcjy.domain.entity.SysUser;

/**
 * 用户数据处理接口
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:44
 */
public interface SysUserService extends IService<SysUser> {
    /** 通过用户的账户查询用户信息 */
    SysUserDTO getUserByUserAccount(String userAccount);

    /** 添加用户 */
    void addUser(SysUserDTO sysUserDTO);
}
