create table if not exists security_user
(
    id           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'ID',
    username     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户名',
    nickname     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '昵称',
    password     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '密码',
    totp_secret  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci null comment '2FA 密钥',
    expired_at   datetime default '2099-12-31 00:00:00' comment '过期时间',
    is_locked    boolean  default false comment '是否锁定',
    is_enabled   boolean  default false comment '是否启用',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    unique pk_username (username),
    index idx_nickname (nickname, is_deleted),
    index idx_username (username, is_deleted),
    index idx_gmt_create (gmt_create)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户基础信息';

create table if not exists security_group
(
    id           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'ID',
    parent_id    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '父用户组ID',
    group_name   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '名称',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    unique unique_group_name (group_name),
    index pk_id (id, is_deleted),
    index idx_parent_id (parent_id, is_deleted),
    index idx_group_name (group_name, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户组';


create table if not exists security_role
(
    id           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'ID',
    role_name    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '角色名称',
    description  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '描述',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    unique pk_role_name (role_name),
    index idx_role_name (role_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '角色';

create table if not exists security_user_role
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


create table if not exists security_user_group
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



create table if not exists security_group_role
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

create table if not exists security_ip_acl
(
    id varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment 'ID',
    acl_type       enum ('ALLOW','DENY')                                         null default 'ALLOW' comment '类型。 ALLOW/DENY',
    ip_range_begin varbinary(16)                                                 not null comment 'IP范围开始的地址',
    ip_range_end   varbinary(16)                                                 not null comment 'IP范围结束的地址',
    description    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '描述',
    gmt_create     datetime                                                           default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified   datetime                                                           default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted     boolean                                                            default false comment '是否删除',
    primary key (id),
    index idx_acl_type (acl_type),
    index idx_ip_range_begin (ip_range_begin),
    index idx_ip_range_end (ip_range_end),
    index idx_is_deleted (is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment 'IP 访问控制列表';

