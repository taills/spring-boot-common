# API Extension

## Controller 示例
```java
@ApiResponseBody
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ApiResult getUserById(@PathVariable Long userId) {
        if (userId < 1) {
            throw new BaseException(ApiResult.failure(10000, "用户ID不能小于1"));
        }
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()){
            return ApiResult.success(user.get());
        }else{
            throw new RuntimeException("用户不存在");
        }
    }
}

```

## 统一的输出格式
在 `Controller` 类上增加注解 `@ApiResponseBody` 即可

更进一步的话，把方法的返回值类型定义为 `ApiResult`

```json
{
  "code": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "userId": "2",
    "username": "string",
    "nickname": "string",
    "mobile": "string",
    "avatarUrl": "string",
    "addressId": "string"
  }
}
```

## 统一的异常捕获

抛出业务异常
```java
throw new BaseException(ApiResult.failure(10000, "用户ID不能小于1"));
```
抛出运行时异常
```java
throw new RuntimeException("用户不存在");
```