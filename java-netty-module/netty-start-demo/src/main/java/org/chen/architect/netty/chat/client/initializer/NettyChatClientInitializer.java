package org.chen.architect.netty.chat.client.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.chen.architect.netty.chat.client.handler.NettyChatClientHandler;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-07 14:38
 */
public class NettyChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 解码器  根据指定分隔符分割获取ByteBuf获取参数
        pipeline.addLast("DelimiterBasedFrameDecoder",new DelimiterBasedFrameDecoder(4096,
                Delimiters.lineDelimiter()));
        // 解码器  字符串编码与解码 并且使用UTF-8编码
        pipeline.addLast("StringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("StringEncoder", new StringEncoder(CharsetUtil.UTF_8));


        pipeline.addLast("NettyChatClientHandler", new NettyChatClientHandler());
    }
}
