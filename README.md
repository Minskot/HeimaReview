# 黑马点评 hmdp

一个类「大众点评」的本地生活服务项目：商户浏览、探店笔记、点赞关注、优惠券秒杀、附近商铺等。
技术栈为 Spring Boot + MyBatis-Plus + MySQL + Redis，前端为 Nginx 托管的静态页面（Vue 2 + Element UI）。

本项目是跟随黑马程序员《Redis 入门到实战》课程做的个人实现版本，仓库记录我自己写代码的过程与踩坑记录。

## 技术栈

| 层次 | 选型 |
| --- | --- |
| 后端框架 | Spring Boot 2.7.4、Spring MVC（拦截器 + ThreadLocal 用户上下文） |
| 持久层 | MyBatis-Plus 3.5.2、MySQL 8 |
| 缓存 | Redis + Lettuce 连接池 |
| 工具 | Hutool 5.8.8、Lombok |
| 前端 | Nginx 1.18.0 静态资源、Vue 2、Element UI |

## 当前进度

已完成（实战篇第一章「短信登录」）：

- 手机号验证码登录：`POST /user/code` 发送验证码，`POST /user/login` 校验并登录
- 用户不存在时按手机号自动注册，昵称随机生成（`user_` + 随机串）
- 登录态保存在 Session，登出 `POST /user/logout` 使 Session 失效，`GET /user/me` 返回当前用户
- `LoginInterceptor` 统一拦截登录校验，`UserHolder` 基于 ThreadLocal 传递当前用户，`MvcConfig` 注册拦截器并放行登录、商铺列表、上传等公开接口
- 探店笔记图片上传：落盘到 Nginx 静态目录 `html/hmdp/imgs/`，与 `SystemConstants.IMAGE_UPLOAD_DIR` 保持一致

未实现（课程后续章节）：

- 商户信息缓存、缓存穿透 / 缓存雪崩
- 达人探店、点赞（SortedSet）、好友关注与共同关注
- 优惠券秒杀（乐观锁、Lua 脚本、消息队列异步下单）
- 附近商铺（GEO）、用户签到（BitMap）、UV 统计（HyperLogLog）

## 快速开始

### 1. 环境要求

- JDK 21（注意：Spring Boot 2.7.4 无法在 JDK 25 下编译，`pom.xml` 已把 Lombok 锁定到 1.18.46 以适配 JDK 21/25）
- MySQL 8、Redis 6/7、Nginx 1.18.0（前端资源已在 `src/main/resources/nginx-1.18.0` 内）

### 2. 初始化数据库

```sql
source D:/Programmings/Java/HeimaReview/hmdp/hmdp.sql;
```

或：

```powershell
mysql -uroot -p1234 -e "CREATE DATABASE IF NOT EXISTS hmdp DEFAULT CHARSET utf8mb4;"
mysql -uroot -p1234 hmdp < .\hmdp.sql
```

### 3. 修改配置

`src/main/resources/application.yaml` 里的数据库账号密码、Redis 地址按本机修改（仓库内是本地开发用的默认值）。

### 4. 启动

```powershell
# 后端：端口 8081
# 运行主类 com.hmdp.HmDianPingApplication（或在项目根目录 mvn spring-boot:run）

# 前端：进入 nginx 目录启动，端口 8080
cd .\src\main\resources\nginx-1.18.0
.\nginx.exe
```

浏览器访问 <http://127.0.0.1:8080>，测试账号可用任意合法手机号，验证码打印在后端日志里（`发送短信验证码成功，验证码：xxxxxx`）。

## 目录结构

```text
hmdp/
├── pom.xml
├── hmdp.sql                      # 建表 + 初始数据
└── src/main/
    ├── java/com/hmdp/
    │   ├── config/               # MvcConfig（拦截器）、MybatisConfig、全局异常处理
    │   ├── controller/           # 用户、商铺、博客、优惠券等接口
    │   ├── dto/ / entity/        # 传输对象与数据库实体
    │   ├── mapper/               # MyBatis-Plus Mapper
    │   ├── service/              # 业务接口与实现
    │   └── utils/                # 拦截器、UserHolder、常量、正则/验证码工具
    └── resources/
        ├── application.yaml
        └── nginx-1.18.0/         # 前端静态页面与 Nginx 配置
```

## 说明

- 仓库中的数据库密码、Redis 地址均为本机开发配置，请勿依赖；生产环境请改用环境变量或配置中心。
- `starting.md` 记录了本机启动过程的排查结论（Redis/MySQL/Nginx 正常，JDK 需固定在 21）。
- 原始脚手架与前端资源来自课程作者仓库 [cs001020/hmdp](https://github.com/cs001020/hmdp)，课程视频：[黑马程序员 Redis 入门到实战](https://www.bilibili.com/video/BV1cr4y1671t)。
