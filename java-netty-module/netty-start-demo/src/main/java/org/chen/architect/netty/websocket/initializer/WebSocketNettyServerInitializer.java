package org.chen.architect.netty.websocket.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.chen.architect.netty.websocket.handler.TextWebSocketNettyServerHandler;


/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-10 21:22
 */
public class WebSocketNettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        // http 处理器
        pipeline.addLast(new HttpServerCodec());
        // chunk 写处理器
        pipeline.addLast(new ChunkedWriteHandler());
        // netty 中将数据分块来处理 ，在这个处理器会合并，当超过最大maxContentLength，还是会调用另一个方法 handleOversizedMessage
        pipeline.addLast(new HttpObjectAggregator(8192));
        // WebSocket请求的根路径  ws://localhost:8080/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义处理器
        pipeline.addLast(new TextWebSocketNettyServerHandler());
    }
}
