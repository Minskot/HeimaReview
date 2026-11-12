package com.hmdp.utils;

public class SystemConstants {
    // 探店笔记配图的上传目录，必须与 nginx 的静态资源目录对齐：
    // nginx.conf 的 root 是 html/hmdp，前端把上传返回的 "/blogs/x/y/xxx.jpg"
    // 拼成 "/imgs/blogs/x/y/xxx.jpg" 访问，故此处须指向 <nginx>/html/hmdp/imgs/
    public static final String IMAGE_UPLOAD_DIR = "D:\\Programmings\\Java\\HeimaReview\\hmdp\\src\\main\\resources\\nginx-1.18.0\\html\\hmdp\\imgs\\";
    public static final String USER_NICK_NAME_PREFIX = "user_";
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int MAX_PAGE_SIZE = 10;
}
