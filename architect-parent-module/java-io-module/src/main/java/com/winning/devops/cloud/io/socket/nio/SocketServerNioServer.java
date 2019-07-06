package com.winning.devops.cloud.io.socket.nio;

import com.winning.devops.cloud.io.utils.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author chensj
 * @version V1.0
 * @classDesc java nio socket server
 *            注册两个selector 分别处理
 *              serverSelector 连接
 *              clientSelector 数据读处理
 *            这个是@{@link  SocketServerNioModule}升级版本
 * @email chensj@winning.com.cn
 * @date 2019-07-05 9:59
 * NIO模型中通常会有两个线程，每个线程绑定一个轮询器selector，在
 * 我们这个例子中serverSelector负责轮询是否有新的连接，clientSelector负责轮询连接是否有数据可读
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class SocketServerNioServer {

    public static void main(String[] args) throws IOException {
        // socket server 使用的selector
        Selector serverSelector = Selector.open();
        // socket client 使用的selector
        Selector clientSelector = Selector.open();
        // 创建serverSelector轮询线程
        // 负责轮询是否有新的连接 SelectionKey.OP_ACCEPT
        new Thread(() -> {
            try {
                ServerSocketChannel listenChannel = ServerSocketChannel.open();
                // 指定监听端口
                listenChannel.socket().bind(new InetSocketAddress(Constants.PORT));
                // 设置为非阻塞
                listenChannel.configureBlocking(false);
                // 注册selector到channel，并指定监听事件
                listenChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
                while (true) {
                    // 监测是否有新的连接，这里的1指的是阻塞的时间为3000ms
                    if (serverSelector.select(Constants.TIME_OUT) == 0) {
                        System.out.println("连接还没有就绪，请稍候.....");
                        continue;
                    }
                    // 循环SelectionKey
                    Iterator<SelectionKey> iterator = serverSelector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 判断是否是新来的连接
                        if (key.isAcceptable()) {
                            try {
                                // 新来的连接，直接注册到clientSelector来处理
                                SocketChannel clientSocketChannel = (SocketChannel) key.channel();
                                clientSocketChannel.configureBlocking(false);
                                clientSocketChannel.register(clientSelector, SelectionKey.OP_READ);
                            } finally {
                                // 移除当前的key
                                iterator.remove();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // 创建clientSelector轮询线程
        // 轮询连接是否有数据可读 SelectionKey.OP_READ
        new Thread(() -> {
            try {
                while (true) {
                    // 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为3000ms
                    if (clientSelector.select(Constants.TIME_OUT) == 0) {
                        System.out.println("还没有需要处理的SelectionKey.OP_READ事件数据，继续等待.....");
                        continue;
                    }

                    Iterator<SelectionKey> iterator = clientSelector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 判断是否是数据可读
                        if (key.isReadable()) {
                            try {
                                // 数据可读连接
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                // 设置缓冲区
                                ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                                // 读取数据 按照以块为单位批量读取
                                clientChannel.read(buffer);
                                // 缓存区状态变化，指定需要处理的数据的position和limit
                                buffer.flip();
                                // 输出结果
                                System.out.println(
                                        Charset.defaultCharset().newDecoder().decode(buffer).toString()
                                );
                            } finally {
                                // 移除当前的key
                                iterator.remove();
                                // 指定当前这个key 继续关注的事件类型
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
