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

        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast(new ChunkedWriteHandler());
        // netty 中将数据分块来处理 ，在这个处理器会合并，当超过最大size，还是会调用另一个方法
        pipeline.addLast(new HttpObjectAggregator(8192));

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new TextWebSocketNettyServerHandler());
    }
}
