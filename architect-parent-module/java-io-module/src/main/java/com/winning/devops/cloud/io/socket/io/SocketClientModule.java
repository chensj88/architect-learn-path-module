package com.winning.devops.cloud.io.socket.io;

import com.winning.devops.cloud.io.utils.NioUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author chensj
 * @version v1.0
 * @classDesc java io socket
 * @project architect-parent-module
 * @date 2019-07-04 22:10
 */
public class SocketClientModule {
    private static final int PORT = 8080;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        int i = 0;
        while (true){
            i++;
            client();
            System.out.println(i);
        }

    }

    private static void client() {
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            // 休息 2s
            TimeUnit.SECONDS.sleep(2);

            socket = new Socket(InetAddress.getLocalHost(), PORT);
            // 建立连接后获得输出流
            out = socket.getOutputStream();
            String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
            String message = "你好 SocketServer , @ " + format;
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.flush();
            // 通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
            socket.shutdownOutput();
            // 从socket中获取输入流
            in = socket.getInputStream();
            // 接收数据缓冲区
            byte[] bytes = new byte[BUFFER_SIZE];
            // 接收数据大小
            int receiveSize = 0;
            while ((receiveSize = in.read(bytes)) != -1) {
                byte[] temp = new byte[receiveSize];
                System.arraycopy(bytes, 0, temp, 0, receiveSize);
                System.out.println("服务端信息：" + new String(temp, "UTF-8"));
                // 判断是否最后一次，如果是则接收读取操作
                if(receiveSize < BUFFER_SIZE){
                    break;
                }
            }
            System.out.println("socket end");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            NioUtils.close(in, out, socket);
        }
    }


}
