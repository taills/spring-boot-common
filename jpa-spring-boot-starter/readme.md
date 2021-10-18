# MF Service Common JPA

JPA 集成组件，配合代码生成脚本，自动化生成数据库访问层，非常便于使用与扩展。

## 使用说明

建表时要求必带如下`4`个字段

- id `bigint unsigned auto_increment`
- gmt_create `datetime default CURRENT_TIMESTAMP comment '创建时间'`
- gmt_modify `datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',`
- is_deleted `boolean  default false comment '软删除标记'`

### User 表示例

```sql
create table user
(
    id           bigint unsigned auto_increment,
    user_id      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户ID',
    username     varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '用户名',
    nickname     varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '昵称',
    mobile       varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '手机号',
    avatar_url   varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '头像 URL',
    address_id   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null comment '默认地址ID',
    gmt_create   datetime default CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   boolean  default false comment '是否删除',
    primary key (id),
    index user_id_ (user_id, is_deleted),
    index username (username, is_deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    comment '用户基础表';
```
### 代码生成脚本

生成代码时选择包路径为 `com.gxmafeng.jpa` 

成功会生成`3` 个文件夹 `entity` `repository` `service`

### Controller 直接使用

```java
    //注入 UserService
    @Autowired
    UserService userService;

    @PostMapping("/user/save")
    public User save(@RequestBody User user){
        // 调用 save 方法保存
        return userService.save(user);
    }
```

### 新增 deleteByUserId 方法
由于 UserId 为混乱的字符串,默认生成的代码里没有已该字段为条件删除表记录的，所以需要新增。

在 `UserRepository` 中新增以下内容，idea 中有插件的话，会有代码提示，包括字段名、排序等

```java
    @Modifying
    @Transactional
    Integer deleteAllByUserId(String userId);
```

在 `UserService` 中新增以下内容

```java
	public Integer deleteAllByUserId(String userId){
		return rep.deleteAllByUserId(userId);
	}
```

`Controller` 中的内容为

```java
    @DeleteMapping("/user/{userId}")
    public Integer delete(@PathVariable String userId) {
        return userService.deleteAllByUserId(userId);
    }
```
