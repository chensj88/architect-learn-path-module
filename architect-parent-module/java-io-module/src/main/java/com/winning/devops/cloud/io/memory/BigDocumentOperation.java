package com.winning.devops.cloud.io.memory;


import com.winning.devops.cloud.io.utils.NioUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 大文件处理
 * @project architect-parent-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-04 23:10
 */
public class BigDocumentOperation {
    public static void main(String[] args) {
        readByByteBuffer();
        System.out.println("===============");
        readByMappedByteBuffer();
    }

    /**
     * ByteBuffer读取文件
     */
    public static void readByByteBuffer() {
        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        try {
            randomAccessFile = new RandomAccessFile("java-io-module/src/main/resources/test.pptx", "rw");
            fileChannel = randomAccessFile.getChannel();
            long startTS = System.currentTimeMillis();
            ByteBuffer buffer = ByteBuffer.allocate((int) randomAccessFile.length());
            buffer.clear();
            fileChannel.read(buffer);
            long endTS = System.currentTimeMillis();
            System.out.println("Read time: " + (endTS - startTS) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            NioUtils.close(randomAccessFile, fileChannel);
        }
    }

    /**
     * MappedByteBuffer读取文件
     */
    public static void readByMappedByteBuffer() {
        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        try {
            randomAccessFile = new RandomAccessFile("java-io-module/src/main/resources/test.pptx", "rw");
            fileChannel = randomAccessFile.getChannel();
            long startTS = System.currentTimeMillis();
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, randomAccessFile.length());
            long endTS = System.currentTimeMillis();
            System.out.println("Read time: " + (endTS - startTS) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            NioUtils.close(randomAccessFile, fileChannel);
        }
    }
}
