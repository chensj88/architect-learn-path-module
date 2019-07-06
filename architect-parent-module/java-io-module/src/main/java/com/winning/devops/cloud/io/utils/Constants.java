package com.winning.devops.cloud.io.utils;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-05 10:53
 */
public class Constants {

    private Constants() {
    }

    /**
     * 端口
     * 用于端口监听
     */
    public static final Integer PORT = 8080;
    /**
     * 过期时间
     * 用于selector 阻塞
     */
    public static final Long TIME_OUT = 3000L;
    /**
     * 缓冲区大小
     * 用于ByteBuffer分配
     */
    public static final Integer BUFFER_SIZE = 1024;
}
