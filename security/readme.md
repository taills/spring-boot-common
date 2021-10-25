# Security 模块

预置基础的用户、用户组、角色功能

##

```sql
create table security_user
(
    id           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment 'ID',
    username     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment '用户名',
    nickname     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '昵称',
    mobile       varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   comment '手机号',
    avatar_url   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  comment '头像 URL',
    password     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment '密码',
    expired_at   datetime default '2099-12-31 00:00:00' comment '过期时间',
    is_locked    boolean  default false comment '是否锁定',
    is_enabled   boolean  default false comment '是否启用',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index pk_id (id, is_deleted),
    index idx_nickname (nickname, is_deleted),
    unique idx_mobile (mobile, is_deleted),
    unique idx_username (username, is_deleted),
    index idx_gmt_create (gmt_create)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户基础信息';

create table security_group
(
    id           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'ID',
    parent_id    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '父用户组ID',
    group_name   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '名称',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index pk_id (id, is_deleted),
    index idx_parent_id (parent_id, is_deleted),
    index idx_group_name (group_name, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户组';


create table security_role
(
    id           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'ID',
    role_name    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '角色名称',
    description  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '描述',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index pk_id (id, is_deleted),
    index idx_role_name (role_name, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '角色';

create table security_api_log
(
    id             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   not null comment 'ID',
    url            varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'URL',
    http_method    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   not null comment 'HTTP Method',
    class_method   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null comment 'Java Class Method',
    ip             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   not null comment 'ip',
    request_args   varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'request args',
    response_args  varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'response args',
    time_consuming int unsigned default 0 comment 'time-consuming(ms)',
    user_id        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   not null comment '用户ID',
    gmt_create     datetime     default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified   datetime     default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted     boolean      default false comment '是否删除',
    primary key (id),
    index user_id_ (user_id, is_deleted),
    index http_method_ (http_method, is_deleted),
    index ip_ (ip, is_deleted),
    index class_method_ (class_method, is_deleted),
    index gmt_create_ (gmt_create, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment 'API日志';



create table security_user_role
(
    id           int unsigned auto_increment comment 'ID',
    user_id      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户ID',
    role_id      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '角色ID',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index idx_role_id (role_id),
    index idx_user_id (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户-角色 [中间表]';


create table security_user_group
(
    id           int unsigned auto_increment comment 'ID',
    user_id      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户ID',
    group_id     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户组ID',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index idx_group_id (group_id),
    index idx_user_id (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户-用户组 [中间表]';



create table security_group_role
(
    id           int unsigned auto_increment comment 'ID',
    group_id     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户组ID',
    role_id      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '角色ID',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index group_id_ (group_id),
    index role_id_ (role_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户组-角色 [中间表]';


```