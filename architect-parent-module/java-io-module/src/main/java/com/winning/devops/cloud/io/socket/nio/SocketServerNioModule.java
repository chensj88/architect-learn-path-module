package com.winning.devops.cloud.io.socket.nio;

import com.winning.devops.cloud.io.utils.Constants;
import com.winning.devops.cloud.io.utils.NioUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

/**
 * @author chensj
 * @version v1.0
 * @classDesc TCP服务端代码改写成NIO的方式
 * @project architect-parent-module
 * @date 2019-07-04 20:40
 */
public class SocketServerNioModule{

    public static void main(String[] args){
        System.out.println("[main] @ "+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SSS"));
        selector();
    }
    public static void selector() {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try{
            // Selector的创建
            selector = Selector.open();
            // 打开ServerSocketChannel
            ssc= ServerSocketChannel.open();
            // channel 指定监听端口
            ssc.socket().bind(new InetSocketAddress(Constants.PORT));
            //  设置成非阻塞模式
            ssc.configureBlocking(false);
            // 注册Selector
            // 第一个参数表示selector对象
            // 第二个参数表示这个Selector监听Channel的事件类型
            // 向Selector注册Channel时，register()方法会返回一个SelectionKey对象
            // 这个对象包含：
            //      interest集合：监听事件类型集合
            //      ready集合: 通道已经准备就绪的操作的集合
            //      Channel: Channel
            //      Selector: selector
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while(true){

                System.out.println(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SSS"));
                // 通过Selector选择通道,返回值(int)表示有多少通道已经就绪
                // 参数表示channel正常后或多或少会阻塞多少毫秒
                if(selector.select(Constants.TIME_OUT) == 0){
                    System.out.println("==");
                    continue;
                }
                // 调用了select()方法，并且返回值表明有一个或更多个通道就绪了，
                // 然后可以通过调用selector的selectedKeys()方法
                // 获取当前已经就绪的selectorKeys(selector key set,已选择键集)
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while(iter.hasNext()){
                    SelectionKey key = iter.next();
                    // 检测channel中什么事件或操作已经就绪
                    // 通过判断SelectionKey的ready集合类型选择不同处理方式完成change的处理

                    // SocketServerChannel就绪
                    System.out.println(ReflectionToStringBuilder.toString(key, ToStringStyle.MULTI_LINE_STYLE));
                    if(key.isAcceptable()){
                        handleAccept(key);
                    }

                    //  有数据可读的通道
                    if(key.isReadable()){
                        handleRead(key);
                    }
                    // 等待写数据的通道
                    if(key.isWritable() && key.isValid()){
                        handleWrite(key);
                    }
                    // 连接就绪
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    // 移除当前的selectorKey，Selector不会自己从已选择键集中移除SelectionKey实例
                    // 必须在处理完通道时自己移除
                    iter.remove();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            NioUtils.close(selector, ssc);
        }
    }



    /**
     * 处理连接
     * @param key
     * @throws IOException
     */
    public static void handleAccept(SelectionKey key) throws IOException{
        // channel()方法返回的通道需要转型成你要处理的channel类型
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        // 监听新进来的连接
        SocketChannel sc = ssChannel.accept();
        // 设置成非阻塞模式
        sc.configureBlocking(false);
        // 连接成功后 转换当前selector开始监听read事件 接收数据
        // TODO 这里应该重新建立一个selector 用于专门处理read事件
        // 注册selector，指定监听事件，并分配新的字节缓冲区
        // ByteBuffer.allocateDirect(BUF_SIZE)分配新的直接字节缓冲区， BUF_SIZE表示新缓冲区的容量
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(Constants.BUFFER_SIZE));
    }

    /**
     * 读数据 处理
     * @param key
     * @throws IOException
     */
    public static void handleRead(SelectionKey key) throws IOException{
        SocketChannel sc = (SocketChannel)key.channel();
        // 获取缓冲区
        ByteBuffer buf = (ByteBuffer)key.attachment();
        // 读取数据
        long bytesRead = sc.read(buf);
        while(bytesRead>0){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if(bytesRead == -1){
            sc.close();
        }
    }

    /**
     * 写数据 处理
     * @param key
     * @throws IOException
     */
    public static void handleWrite(SelectionKey key) throws IOException{
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }



}
