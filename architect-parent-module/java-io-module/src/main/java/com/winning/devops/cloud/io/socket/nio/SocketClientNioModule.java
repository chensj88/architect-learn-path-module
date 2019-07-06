package com.winning.devops.cloud.io.socket.nio;


import com.winning.devops.cloud.io.utils.NioUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author chensj
 * @version v1.0
 * @classDesc java nio socket client
 * @project architect-parent-module
 * @date 2019-07-04 20:32
 */
public class SocketClientNioModule {
    public static void main(String[] args){
        client();
    }

    public static void client() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try {
            // 打开SocketChannel
            socketChannel = SocketChannel.open();
            // 配置实现非阻塞式的信道
            socketChannel.configureBlocking(false);
            // 连接服务端
            socketChannel.connect(new InetSocketAddress("192.168.31.59", 8080));
            if (socketChannel.finishConnect()) {
                int i = 0;
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    // 读取数据
                    String info = "I'm " + i++ + "-th information from client";
                    // 清空缓冲区
                    buffer.clear();
                    // 数据写入缓存区
                    buffer.put(info.getBytes());
                    // 缓存区状态变化，指定需要处理的数据的position和limit
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        System.out.println(buffer);
                        // 数据从缓存区写入channel中
                        socketChannel.write(buffer);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            NioUtils.close(socketChannel);
        }
    }


}
