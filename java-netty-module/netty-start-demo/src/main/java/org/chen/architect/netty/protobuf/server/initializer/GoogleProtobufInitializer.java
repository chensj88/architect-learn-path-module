package org.chen.architect.netty.protobuf.server.initializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.chen.architect.netty.protobuf.server.handler.GoogleProtobufMultipartHandler;
import org.chen.architect.netty.protobuf.server.model.MyDataInfo;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-15 21:45
 */
public class GoogleProtobufInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        // protobuf 处理器
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 需要指定转换的参数类型  protobuf中指定的java_outer_classname名称，最外层类名称
        // TODO 存在一个问题，如果指定了message的类型后，那么就会导致其他的message类型无法处理
        // TODO 客户端与服务端只能通信指定类型的message
        // 如何动态处理不同类型的数据，该如何处理
        // Netty提供方案是 通过设置自定义协议，就是消息传输过程中使用不同的前缀，按照不同前缀来指定不同消息类型
        // 比如  AB 对应的是 DataInfo.Person
        //      BC 对应的是 DataInfo.Student
        // 这个ProtobufDecoder就需要自己实现处理
        // 第二种方案是基于已知类型来处理
        // 单一类型消息处理
        //pipeline.addLast(new ProtobufDecoder(DataInfo.Person.getDefaultInstance()));
        // 多种类型消息处理
        pipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        //单一类型消息处理
        //pipeline.addLast(new GoogleProtobufHandler());
        // 多种类型消息处理
        pipeline.addLast(new GoogleProtobufMultipartHandler());
    }
}
