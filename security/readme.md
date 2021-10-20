# Security 模块

预置基础的用户、用户组、角色功能

##

```sql
create table security_user
(
    id           bigint unsigned auto_increment,
    user_id      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment '用户ID',
    username     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment '用户名',
    nickname     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment '昵称',
    mobile       varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment '手机号',
    avatar_url   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '头像 URL',
    password     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment '密码',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index        user_id_ (user_id, is_deleted),
    index        username (username, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户基础信息';

create table security_group
(
    id              bigint unsigned auto_increment,
    group_id        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户组ID',
    parent_group_id varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '父用户组ID',
    group_name      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '名称',
    gmt_create      datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified    datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted      boolean  default false comment '是否删除',
    primary key (id),
    index           group_id (group_id, is_deleted),
    index           parent_group_id (parent_group_id, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户组';

create table security_role
(
    id           bigint unsigned auto_increment,
    role_id      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '角色ID',
    role_name    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '角色名称',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index        role_id (role_id, is_deleted),
    index        role_name (role_name, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '角色表';

```