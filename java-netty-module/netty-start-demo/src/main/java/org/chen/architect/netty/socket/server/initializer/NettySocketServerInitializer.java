package org.chen.architect.netty.socket.server.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.chen.architect.netty.socket.server.handler.NettySocketServerHandler;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:27
 */
public class NettySocketServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 客户端与服务端一旦连接成功，这个方法就会被调用
     * 先做handler add，channel register
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        // 一种解码器，它通过消息中长度字段的值动态地分割接收的ByteBuf
        // 当您解码具有整数标头字段的二进制消息时，它特别有用，该字段表示消息正文的长度或整个消息。
        pipeline.addLast("lengthFieldBasedFrameDecoder",
                new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                0, 4, 0, 4));
        // 一种预编码消息长度的编码器。长度值作为二进制形式预先添加 就是在之前添加
        pipeline.addLast("lengthFieldPrepender",new LengthFieldPrepender(4));
        // 字符串编码与解码 并且使用UTF-8编码
        pipeline.addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8));

        // 自定义handler
        pipeline.addLast("nettySocketServerHandler",new NettySocketServerHandler());


    }
}
