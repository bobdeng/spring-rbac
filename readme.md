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
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.bobdeng</groupId>
    <artifactId>spring-rbac</artifactId>
    <version>Tag</version>
</dependency>
```
- gradle
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
	        implementation 'com.github.bobdeng:spring-rbac:Tag'
	}
```
## 配置应用
- application.properties
```properties
spring.jpa.mapping-resources=/rbac/orm.xml
jwt.token=123456
jwt.expire=8640000000
jwt.prefix=Token:
```
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
## 第三方用户登录
- 调用ThirdLoginService，得到UserToken，然后保存Token到Cookie

