-- ----------------------------
-- 1、用户表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
                          user_id              varchar(64)  not null     comment '用户ID',
                          user_account         varchar(32)  not null     comment '用户账号',
                          user_name            varchar(332) not null     comment '姓名',
                          nick_name            varchar(332) not null     comment '昵称',
                          sex                  char(1)      default ''   comment '用户性别',
                          password             varchar(255) default ''   comment '密码',
                          phone_number         varchar(11)  default ''   comment '手机号码',
                          email                varchar(50)  default ''   comment '用户邮箱',
                          user_type            varchar(2)   default ''   comment '用户类型 ',
                          avatar               varchar(100) default ''   comment '头像地址',
                          remark               varchar(500) default null comment '备注',
                          status               char(1)      default '0'  comment '帐号状态（0:正常 1:停用）',
                          del_flag             char(1)      default '0'  comment '删除标志（0:正常 1:删除）',
                          create_user_id       varchar(64)  default ''   comment '创建者',
                          create_date_time     datetime                  comment '创建时间',
                          update_user_id       varchar(64)  default ''   comment '更新者',
                          update_date_time     datetime                  comment '更新时间',
                          last_login_date_time datetime                  comment '最后登录时间',
                          primary key (user_id)
) engine = innodb comment = '用户信息表';

-- ----------------------------
-- 2、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
                          role_id          varchar(64)  not null     comment '角色ID',
                          role_sign        varchar(64)  not null     comment '角色标识',
                          role_name        varchar(30)  not null     comment '角色名称',
                          role_sort        int(4)       not null     comment '显示顺序',
                          remark           varchar(500) default null comment '备注',
                          status           char(1)      default '0'  comment '角色状态（0:正常 1:停用）',
                          del_flag         char(1)      default '0'  comment '删除标志（0:正常 1:删除）',
                          create_user_id   varchar(64)  default ''   comment '创建者',
                          create_date_time datetime                  comment '创建时间',
                          update_user_id   varchar(64)  default ''   comment '更新者',
                          update_date_time datetime                  comment '更新时间',
                          primary key (role_id)
) engine = innodb comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values ('0', 'ADMIN', '超级管理员', 1, '', 0, 0, 'admin', sysdate(), null, null);
insert into sys_role values ('1', 'COMMON', '普通角色', 2, '', 0, 0, 'admin', sysdate(), null, null);

-- ----------------------------
-- 3、用户和角色关联表  用户 1-N 角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
                               user_id varchar(64) not null comment '用户ID',
                               role_id varchar(64) not null comment '角色ID',
                               primary key(user_id, role_id)
) engine = innodb comment = '用户和角色关联表';

-- ----------------------------
-- 4、权限表
-- ----------------------------
drop table if exists sys_permission;
create table sys_permission (
                                permission_id   varchar(64) not null comment '权限ID',
                                permission_name varchar(64) not null comment '权限名',
                                url_pattern     varchar(64) not null comment '权限路径',
                                primary key(permission_id)
) engine = innodb comment = '权限表';

-- ----------------------------
-- 初始化-权限表数据
-- ----------------------------
insert into sys_permission values ('0', '管理员可访问', '/admin/**');
insert into sys_permission values ('1', '管理员和普通用户均可访问', '/common/**');

-- ----------------------------
-- 5、角色权限关联表 角色 N - N 权限
-- ----------------------------
drop table if exists sys_role_permission;
create table sys_role_permission (
                                     permission_id   varchar(64) not null comment '权限ID',
                                     role_id varchar(64) not null comment '角色ID',
                                     primary key(permission_id, role_id)
) engine = innodb comment = '角色权限关联表';

-- ----------------------------
-- 初始化-角色权限关联表数据
-- ----------------------------
insert into sys_role_permission values ('0', '0');
insert into sys_role_permission values ('1', '0');
insert into sys_role_permission values ('1', '1');