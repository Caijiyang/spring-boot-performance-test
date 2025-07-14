package org.felixcjy.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色表
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:06
 */
@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -806580264086075660L;

    /** 用户ID */
    @TableField("user_id")
    private String userId;

    /** 角色ID */
    @TableField("role_id")
    private String roleId;
}
