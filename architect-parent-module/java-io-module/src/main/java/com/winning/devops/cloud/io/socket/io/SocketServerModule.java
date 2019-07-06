package com.winning.devops.cloud.io.socket.io;

import com.winning.devops.cloud.io.utils.NioUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project architect-parent-module
 * @date 2019-07-04 20:30
 */
public class SocketServerModule {
    public static void main(String[] args){
        server();
    }

    private static void server() {
        ServerSocket serverSocket = null;
        InputStream in = null;
        OutputStream out = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            int receiveMessageSize = 0;
            byte[] recvBuf = new byte[1024];
            while (true) {
                System.out.println("server将一直等待连接的到来");
                clientSocket = serverSocket.accept();
                // 获取访问的连接
                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
                System.out.println("Handling client at " + clientAddress);
                // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
                in = clientSocket.getInputStream();
                while ((receiveMessageSize = in.read(recvBuf)) != -1) {
                    byte[] temp = new byte[receiveMessageSize];
                    System.arraycopy(recvBuf, 0, temp, 0, receiveMessageSize);
                    System.out.println("客户端信息："+new String(temp,"UTF-8"));
                }
                // 从socket中获取输出流
                out = clientSocket.getOutputStream();
                // 向输出流写入数据
                String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
                out.write(("Hello Client,I get the message. @ "+format).getBytes("UTF-8"));
                // 清空输出流
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            NioUtils.close(in, out, clientSocket,serverSocket);
        }
    }
}
