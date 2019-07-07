package org.chen.architect.netty.health.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.chen.architect.netty.base.Constants;

import java.util.Date;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:31
 */
public class HealthCheckServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("[%s @ %s ] : 连接了 \n",
                ctx.channel().remoteAddress(),
                DateFormatUtils.format(new Date(), Constants.DEFAULT_DATETIME_PATTERN)));
    }

    /**
     * 事件触发 回调函数
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断空闲检测事件
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;

            String message = null;

            switch (event.state()){
                case READER_IDLE:
                    message = "读空闲";
                    break;
                case WRITER_IDLE:
                    message = "写空闲";
                    break;
                case ALL_IDLE:
                    message = "读写空闲";
                    break;
                default:
                    message = "未知操作";
            }
            String formatMessage  = String.format("[%s 超时事件 @ %s 事件类型：%s]"
                    , ctx.channel().remoteAddress(), DateFormatUtils.format(new Date(), Constants.DEFAULT_DATETIME_PATTERN)
                    , message);
            System.out.println(formatMessage);

            // 关闭通道
            ctx.channel().close();
        }
    }
}
