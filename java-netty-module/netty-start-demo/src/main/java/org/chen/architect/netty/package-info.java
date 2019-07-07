/**
 * @author chensj
 * @classDesc 根目录
 * @project java-netty-module
 * @version v1.0
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 16:54
 *
 *  Netty 没有实现servlet规范，而是基于自己的一套机制的来完成的
 *  比起servlet 执行效率更高，更接近于底层
 *  Netty可以做的是三件事：
 *   1、HTTP服务器
 *      类似Tomcat，在Netty开发HTTP服务器的时候不要参考servlet来完成，而是一种更接近底层的一种方式
 *      对比于spring mvc来说，这些框架可以提供很多基础的东西，而Netty并没有提供，最明显的就是请求路由。
 *      这个也是和Netty设计有关，Netty更加接近于底层，底层之上，开发人员可以自由组合
 *
 *   2、socket开发
 *      应用最为广泛的功能，进行socket server和socket client开发、RPC开发等
 *      支持自定义协议
 *
 *   3、长连接开发
 *      主要应用在消息推送、聊天等
 *
 * Netty 服务端开发步骤
 *  1、定义两个线程组 EventLoopGroup 父与子
 *  2、创建Netty服务端程序
 *  3、通过childHandler指定 Initializer (这个就是注册自定义Netty请求与响应的handler配置类)
 *  4、在Initializer的initChannel方法中 通过类似于FilterChains一样的方式设定handler顺序来处理请求
 *  5、最后在重写/覆盖handler中对应事件回调函数的处理代码
 *
 * 包说明：
 *  base 存放公共信息
 *  socket socket 开发
 *  http http服务器
 *  chat 聊天室
 */
package org.chen.architect.netty;
