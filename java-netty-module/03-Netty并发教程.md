# Netty 并发教程

## 1、Netty 简介

> Netty is *an asynchronous event-driven network application framework* 
> for rapid development of maintainable high performance protocol servers & clients.
>
> Netty是一个异步基于事件驱动的网络应用框架，用于快速开发可维护的高性能的协议服务器和客户端
>
> Netty is a NIO client server framework which enables quick and easy development of network applications such as protocol servers and clients. It greatly simplifies and streamlines network programming such as TCP and UDP socket server.
>
> Netty是一个NIO C/S 框架(服务端和客户端均可以使用)，可以快速轻松地开发网络应用程序，比如协议服务器和客户端。它极大地简化和流线化了网络编程，比如TCP和UDP socket server。
>
> 'Quick and easy' doesn't mean that a resulting application will suffer from a maintainability or a performance issue. Netty has been designed carefully with the experiences earned from the implementation of a lot of protocols such as FTP, SMTP, HTTP, and various binary and text-based legacy protocols. As a result, Netty has succeeded to find a way to achieve ease of development, performance, stability, and flexibility without a compromise.
>
> “快速简便”并不意味着最终的应用程序将会遭受到可维护性或性能问题的影响。Netty是经过精心设计，设计包含了具有丰富的协议，比如FTP，SMTP，HTTP以及各种二进制和基于文本的传统协议。因此，Netty成功地找到了一种在不妥协的情况下实现易于开发，性能，稳定性和灵活性的方法。

### 特性 Features

#### 设计 Design

* 为多种多样的传输类型提供了统一的API--阻塞或者非阻塞的socket 
* 基于灵活且可扩展的事件模型，可以清晰地分离关注点(separation of concerns)
* 提供了高度可定制化的线程模型-单线程，一个或多个线程池，如SEDA
  * SEDA Staged Event Driven Architecture
  * 阶段事件驱动架构
  * 一个请求的过程分成不同的Staged，每个不同的Staged根据情况来使用不同数量的线程来完成处理，阶段之间是异步进行通信
* 真正的无连接数据报套接字支持（自3.1起）

#### 易于使用 Ease of use

* 详细记录的Javadoc，用户指南和示例
* 没有其他依赖项，JDK 5（Netty 3.x）或6（Netty 4.x）就足够了
  * 注意：某些组件（如HTTP / 2）可能有更多要求。 有关更多信息，请参阅 [“要求”页面](https://netty.io/wiki/requirements.html)。

#### 性能 Performance

* 更高的吞吐量，更低的延迟
* 减少资源消耗
* 最小化不必要的内存复制

#### 安全 Security

* 完整的SSL / TLS和StartTLS支持

#### 社区 Community

- 早发布，经常发布
- 自2003年以来，作者一直在编写类似的框架，他仍然发现你的反馈很珍贵！

### Netty 版本

Netty官网的中提供了三个版本`3.x`、`4.0.x`、`4.1.x`，

建议学习使用`4.1.x`版本。

源代码：[GitHub](https://github.com/netty/netty)

源代码结构是基于功能划分。

### 架构

![架构图](https://netty.io/images/components.png)

按照结构划分为三个部分：

* Transport Services 

  传输服务

  * 支持Socket和Datagram
  * HTTP 隧道
  * VM 管道

* Protocol Support

  协议支持

* Core 

  * 可扩展事件模型
  * 统一的通信API
  * 零拷贝的富ByteBuffer

## 2、Netty使用

Netty的设计架构决定，Netty可以做如下三件事情

* Http服务器

  ```
  类似Tomcat，在Netty开发HTTP服务器的时候不要参考servlet来完成，而是一种更接近底层的一种方式，对比于spring mvc来说，这些框架可以提供很多基础的东西，而Netty并没有提供，最明显的就是请求路由。这个也是和Netty设计有关，Netty更加接近于底层，底层之上，开发人员可以自由组合
  ```

* Socket服务器

  ```
  应用最为广泛的功能，进行socket server和socket client开发、RPC开发等
  支持自定义协议
  ```

* 长连接开发

  ```
  主要应用在消息推送、聊天等
  ```

> 注意
>
> **虽然在一些功能上来看，Netty像Servlet的容器(Tomcat)一样，但是Netty并没有实现Servlet规范，而是基于自己的一套机制来实现的，所以在学习Netty的时候最好不要与Servlet中进行比较，忘记Servlet最好，Netty更加接近于底层**

### 2.1 hello world

#### 2.1.1 NettyHttpServer

``` java
public static void main(String[] args) throws InterruptedException {
        // 建立两个线程组
        // 轮询组别： 主 从客户端接收连接 转发给worker
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 轮询组别： 从 获取转发过来的数据 处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            // Netty 提供服务端启动类 简化Netty服务器启动
            // 用于启动Netty服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 启动类参数设置
            serverBootstrap
                    // 设置老板与员工 事件循环组
                    // 用于处理 ServerChannel和Channel传输过来的IO和Event
                    .group(bossGroup, workerGroup)
                    // 指定channel 使用反射方式注册
                    .channel(NioServerSocketChannel.class)
                    // 指定channel对应handler初始化类
                    // 在这个handler初始化类中指定使用那些handler、这些handler顺序等
                    .childHandler(new NettyHttpServerInitializer());
            // 开启监听端口
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            // 关闭端口监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
```

#### 2.1.2 NettyHttpServerInitializer

```java
public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 这个方法在注册Channel后将调用此方法。
     * 方法返回后，此实例将从Channel的ChannelPipeline中删除。
     *
     * @param ch channel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取管道
        // 这个类似一个拦截器组，在各个方法中做不同的事情
        ChannelPipeline pipeline = ch.pipeline();
        // 在处理最后增加一个处理
        // 第一个参数为 handler名称
        // 第二个参数为 handler
        // HttpServerCodec 对http请求信息和响应信息进行编解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        // 设置自定义处理器
        pipeline.addLast("nettyHttpServerHandler", new NettyHttpServerHandler());
    }
}
```

#### 2.1.3 NettyHttpServerHandler

```java
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 每次处理HttpObject类型消息的时候都会调用这个方法
     * 也就是读取这个类型的请求，并返回响应，也就是处理结果
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 直接启动后使用 curl 访问会出现异常
        // io.netty.channel.DefaultChannelPipeline onUnhandledInboundException
        //  警告: An exceptionCaught() event was fired, and it reached at the tail of the pipeline.
        //  It usually means the last handler in the pipeline did not handle the exception.
        //  java.io.IOException: 你的主机中的软件中止了一个已建立的连接。
        System.out.println("请求实例:"+msg.getClass());
        System.out.println("请求远程地址:"+ctx.channel().remoteAddress());
        // 异常处理
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("请求方法类型：" + httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            // 请求URL处理
            if (Constants.ICO_PATH.equals(uri.getPath())) {
                System.out.println("请求favicon.ico");
                return;
            }
            System.out.println("请求URI：" + new URI(httpRequest.uri()).getPath());
            // 构建响应内容
            ByteBuf content =
                    Unpooled.copiedBuffer("Hello World, this response from netty",
                            CharsetUtil.UTF_8);
            // 构建响应
            FullHttpResponse response =
                    // 指定协议、状态码、响应内容
                    new DefaultFullHttpResponse(
                            HttpVersion.HTTP_1_1,
                            HttpResponseStatus.OK,
                            content);

            // 设置消息头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 返回响应结果
            ctx.writeAndFlush(response);

            // channel关闭
            // 判断请求http协议版本和keep-alive时间
            ctx.channel().close();
        }

    }
}    
```

#### 2.1.4 总结

1. Netty 开发流程

   ```
   Netty 服务端开发步骤
   1、定义两个线程组 EventLoopGroup 父与子
   2、创建Netty服务端程序
   3、通过childHandler指定 Initializer (这个就是注册自定义Netty请求与响应的handler配置类)
   4、在Initializer的initChannel方法中 通过类似于FilterChains一样的方式设定handler顺序来处理请求
   5、最后在重写/覆盖handler中对应事件回调函数的处理代码
   ```

2. Netty处理流程

   ```
   常规处理流程：
    *   handler Added 添加handler
    *      --> channel Registered 注册 channel
    *          --> channel Active  激活 channel
    *              --> channelRead0 请求处理
    *                  -->channel Inactive 待用 channel
    *                      --> channel Unregistered 解除注册 channel
    *                          --> handler Removed 移除 handler
    注意上面的这个处理流程并不是完全正确的
   使用curl是按照上面的流程执行，但是对于从浏览器访问这个则会存在一定的不确定性
   问题是：
     浏览器请求的时候发现：channel Inactive/Unregistered 和handler Removed没有执行
   原因是：
     Netty并不是完全遵照servlet的定义来操作的，对于http 1.1协议上面存在一个keep-alive的属性，在这个属性规定的时间内，服务端不会将客户端的请求给切断。
    在servlet中，servlet容器会对这些进行判断，完成切断连接的操作
    在Netty中，就没有这些，这个需要开发人员确定，通过判断请求http协议版本和keep-alive时间，然后服务端主动结束连接
     所以才会出现上面这个不一致
   解决方案是：
   	 // channel关闭
       // 或者判断请求http协议版本和keep-alive时间
       ctx.channel().close();
   ```

   > 注意上面的流程在Handler类中都就有对应的事件处理的回调函数

### 2.2 Netty Socket

Netty在大多数的使用者中都是使用这个功能，这个可以很好地规避使用JDK原生NIO编写的难度

#### 2.2.1 Netty Socket Server

* NettySocketServer

  ```java
  public class NettySocketServer {
      public static void main(String[] args) throws InterruptedException {
          EventLoopGroup bossGroup = new NioEventLoopGroup();
          EventLoopGroup workerGroup = new NioEventLoopGroup();
          try {
              ServerBootstrap serverBootstrap = new ServerBootstrap();
  
              serverBootstrap.group(bossGroup,workerGroup)
                      .channel(NioServerSocketChannel.class)
                      // handler 用于处理bossGroup相关信息
                      //.handler()
                      // childHandler 用于处理workerGroup相关信息
                      .childHandler(new NettySocketServerInitializer());
              ChannelFuture channelFuture = serverBootstrap.bind(8080)
                      // 表示会一直等待
                      .sync();
  
              channelFuture.channel().closeFuture().sync();
          } finally {
              bossGroup.shutdownGracefully();
              workerGroup.shutdownGracefully();
          }
      }
  }
  ```

  

* NettySocketServerInitializer

  ```java
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
  ```

  

* NettySocketServerHandler

  ```java
  public class NettySocketServerHandler extends SimpleChannelInboundHandler<String> {
      /**
       * 请求处理 回调函数
       *
       * @param ctx 上下文，包含URI、方法类型等
       * @param msg 接收到请求对象
       * @throws Exception
       */
      @Override
      protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
          System.out.println("客户端地址："+ctx.channel().remoteAddress() + "-->" + msg);
          // 返回消息
          ctx.channel().writeAndFlush("收到Client消息 @" +
                  DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
      }
  
      /**
       * 异常回调方法
       * @param ctx 上下文
       * @param cause 异常
       * @throws Exception
       */
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          cause.printStackTrace();
          ctx.close();
      }
  }
  ```

#### 2.2.2 Netty Socket Client

* NettySocketClient

  ```java
  public class NettySocketClient {
  
      public static void main(String[] args) throws InterruptedException {
          // 客户端 线程组 用于与服务端连接
          EventLoopGroup workerGroup = new NioEventLoopGroup();
          try {
              Bootstrap bootstrap = new Bootstrap();
              bootstrap.group(workerGroup)
                      .channel(NioSocketChannel.class)
                      // 指定workerGroup 处理信息
                      .handler(new NettySocketClientInitializer());
              ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
              channelFuture.channel().closeFuture().sync();
          } finally {
              workerGroup.shutdownGracefully();
          }
      }
  }
  ```

* NettySocketClientInitializer

  ```java
  public class NettySocketClientInitializer extends ChannelInitializer<SocketChannel> {
      /**
       * channel 初始化 回调函数
       * @param ch 通道
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
          pipeline.addLast("nettySocketClientHandler",new NettySocketClientHandler());
  
      }
  }
  ```

* NettySocketClientHandler

  ```java
  public class NettySocketClientHandler extends SimpleChannelInboundHandler<String> {
      /**
       * 接收到消息后 回调函数
       * @param ctx 上下文
       * @param msg 传输内容
       * @throws Exception
       */
      @Override
      protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
  
          System.out.println("服务端地址：" + ctx.channel().remoteAddress() + "--> 接收到服务端消息: " + msg);
          System.out.println("服务端消息接收成功，1s后开始继续通信");
          TimeUnit.SECONDS.sleep(1);
          ctx.channel().writeAndFlush("请求服务端 @ "+
                  DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
      }
  
      /**
       * 通道激活 回调函数
       * @param ctx 上下文
       * @throws Exception
       */
      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
          System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")+" : 与服务端通道建立成功，1s后开始通信");
          TimeUnit.SECONDS.sleep(1);
          // 触发服务端与客户端通信
          ctx.channel().writeAndFlush("与服务端通道建立成功，开始通信");
      }
  }
  ```

  #### 2.2.3 总结

  ```
  Netty 开发Socket Server与Client 总结
  相同部分：
    两部分开发的基础模板是一致的
     都是先定义线程池、定义服务启动类、设置Initializer、设置Handler这个模式
     并且在Initializer中定义的公共的处理类也是一致的
   
  不同部分：
    在细节方面还是存在一定差别
      1、线程池 服务端两个，客户端一个
      2、使用的启动类在名称上面有差别
      3、handler数量  handler主要是针对线程池来的，所以服务端是两个，客户端是一个
      4、服务端监听端口，客户端连接host和port
   
   触发交互开始
    在客户端的handler通过重写channelActive方法，来做首次交互的开始
  ```

  