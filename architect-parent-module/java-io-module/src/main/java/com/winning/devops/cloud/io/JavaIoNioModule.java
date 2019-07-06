package com.winning.devops.cloud.io;

import com.winning.devops.cloud.io.utils.NioUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chensj
 * @version v1.0
 * @classDesc java io nio 对比
 * @project architect-parent-module
 * @date 2019-07-04 19:59
 */
public class JavaIoNioModule {

    public static void main(String[] args) {
        normalIo();
        newIo();
    }


    /**
     * java nio
     * 通过RandomAccessFile进行操作，当然也可以通过FileInputStream.getChannel()进行操作
     * Buffer顾名思义：缓冲区，实际上是一个容器，一个连续数组
     * Channel提供从文件、网络读取数据的渠道，但是读写的数据都必须经过Buffer
     */
    public static void newIo() {

        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile("java-io-module/src/main/resources/normal-io.txt", "rw");
            // 获取channel
            FileChannel fileChannel = randomAccessFile.getChannel();
            // 分配空间
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 写入数据到Buffer
            int bytesRead = fileChannel.read(buffer);
            // 读取数量
            System.out.println(bytesRead);
            while (bytesRead != -1) {
                // 调用flip()方法 传输数据，与 compact结合使用
                buffer.flip();
                // 判断当前位置与限制之间是否存在元素
                while (buffer.hasRemaining()) {
                    // 使用get()方法从Buffer中读取数据
                    System.out.print((char) buffer.get());
                }
                // 压缩缓冲区,将所有未读的数据拷贝到Buffer起始处
                buffer.compact();
                // 继续读取
                bytesRead = fileChannel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java IO 流读取文件
     */
    public static void normalIo() {
        InputStream is = null;

        try {
            is = new BufferedInputStream(new FileInputStream("java-io-module/src/main/resources/normal-io.txt"));
            byte[] buf = new byte[1024];
            int bytesRead = is.read(buf);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    System.out.print((char) buf[i]);
                }
                bytesRead = is.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            NioUtils.close(is);
        }
    }



}
