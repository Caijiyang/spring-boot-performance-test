package org.felixcjy.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 15:01
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @TableId("role_id")
    private String roleId;

    /** 角色标识 */
    @TableField("role_sign")
    private String roleSign;

    /** 角色名称 */
    @TableField("role_name")
    private String roleName;

    /** 显示顺序 */
    @TableField("role_sort")
    private int roleSort;

    /** 备注 */
    @TableField("remark")
    private String remark;

    /** 角色状态 0:正常 1:停用*/
    @TableField("status")
    private char status;

    /** 删除标识 */
    @TableField("del_flag")
    private char delFlag;

    /** 创建者 */
    @TableField("create_user_id")
    private String createdUserId;

    /** 创建时间 */
    @TableField("create_date_time")
    private LocalDateTime createDateTime;

    /** 更新者 */
    @TableField("update_user_id")
    private String updateUserId;

    /** 更新时间 */
    @TableField("update_date_time")
    private LocalDateTime updateDateTime;
}
