我已经帮你检查并尝试启动，当前结果如下：

- Redis：正常，`PING` 返回 `PONG`
- MySQL：`3306` 端口正常
- Nginx 前端：正常，`8080` 可以访问
- 后端：启动失败，错误是：

```
Unable to establish loopback connection
java.net.SocketException: Invalid argument: connect
```

另外，项目使用的是 Spring Boot 2.7.4，不能直接使用你当前 Maven 默认的 JDK 25。

**你之后每次启动项目按以下步骤操作：**

1. 启动 Redis

```
D:\CodingPrograms\Redis\redis-server.exe
```

保持窗口不要关闭。

1. 确认 MySQL 已启动，并确认存在 `hmdp` 数据库。

如果还没有导入数据，执行：

```
source D:/Programmings/Java/HeimaReview/hmdp/hmdp.sql;
```

1. 关闭旧的 Java 项目进程，避免 `8081` 端口占用：

```
Get-NetTCPConnection -LocalPort 8081 -ErrorAction SilentlyContinue
```

如果有进程，执行：

```
Stop-Process -Id 进程号 -Force
```

1. 在 IntelliJ IDEA 中把项目 JDK 设置为：

```
D:\CodingPrograms\.jdks\jdk21
```

不要使用 JDK 25。

1. 运行主类：

```
com.hmdp.HmDianPingApplication
```

1. 启动 Nginx：

```
cd D:\Programmings\Java\HeimaReview\hmdp\src\main\resources\nginx-1.18.0
.\nginx.exe
```

如果 Nginx 已经启动，不需要重复启动。

1. 浏览器访问：

```
http://127.0.0.1:8080
```

**当前页面错误的两个原因：**

1. 后端目前没有稳定启动，前端调用 `/api` 时会失败。
2. 你当前是 `init` 分支，登录、验证码、登出、秒杀等功能本身还没有实现，会返回“功能未完成”。

我已经停止了本次启动过程中产生的残留 Java 进程，当前没有继续占用 `8081` 端口。