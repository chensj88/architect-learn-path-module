package com.winning.devops.cloud.io.utils;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author chensj
 * @version v1.0
 * @classDesc NIO 工具类
 * @project architect-parent-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-04 23:33
 */
public class NioUtils {
    /**
     * 关闭所有实现closeable接口的类
     * @param closeableArray 实现closeable接口的类结合
     */
    public static void close(Closeable... closeableArray) {
        try {
            if(closeableArray.length >= 1) {
                for (Closeable closeable : closeableArray) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void close(SocketChannel socketChannel) {
        try {
            if (socketChannel != null) {
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
