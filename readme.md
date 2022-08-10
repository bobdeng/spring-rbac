# 提供了基本的应用框架，包括
- 租户 一个多租户的系统
- 角色 系统角色管理
- 用户 一个租户有多个用户
- 账户/密码 账户密码登录
- 权限。通过AOP控制权限访问
- 组织。多层的组织框架

# 使用方法
## 导入库
- Maven
```xml
<dependency>
            <groupId>cn.bobdeng.rbac</groupId>
            <artifactId>rbac-server</artifactId>
            <version>版本</version>
 </dependency>
```
## 配置应用
- Application配置
```java
  @ComponentScan({"应用包", "cn.bobdeng"})
  @EnableJpaRepositories({"应用包", "cn.bobdeng"})
  @EntityScan({"应用包", "cn.bobdeng"})
```
- 实现管理员密码发送/存储
```java
cn.bobdeng.rbac.domain.AdminPassword.Notifier
cn.bobdeng.rbac.domain.AdminPassword.Store
```
- 扩展应用功能
实现接口：ExternalFunctionReader，并标记为primary
# 使用
## 管理员、用户登录
进入 /admin/
## 用户登录跳转
从用户页面，跳转到 /admin/user/login

