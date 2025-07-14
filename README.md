# spring-boot-performance-test

此项目用于 Spring boot 开发测试，这是个试验场，也是一份整合记录。

如需使用，请修改配置文件中的 数据库、Redis、Minio 等组件的地址。

管理员：账号/密码
felix / qwer1234

普通用户：账号/密码
user1 / qwer1234

## 项目的目的

1. 各种组件的使用方式
2. 各种场景下的开发模式

## 目前已包含的组件

数据库相关

- `MySQL 8.5`
- `MyBatis Plus`
- `Druid` 数据库连接池
- `Redis`

整合的组件

- 常用的 `Apache Commons` 包
- `Spring Boot Validation` 参数校验
- `Spring Cache` 配合 `Redis` 使用
- `Actuator` 监控
- `Logback` 日志
- `spring-boot-starter-test` 测试包

权限

- Spring Security
  - 已经动态整合角色、权限的管理！

## 计划整合的内容

消息队列

-  Kafka
-  Rabbit MQ
-  Rocket MQ

定时任务

-  Quartz
-  Xxl-job

监控

-  Actuator
-  Spring Boot Admin
-  Arthas
    - 在启动模块的配置文件中标注即可。

权限

- jwt