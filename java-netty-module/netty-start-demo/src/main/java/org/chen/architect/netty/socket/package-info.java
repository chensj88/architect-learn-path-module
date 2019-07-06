/**
 * @author chensj
 * @classDesc Netty Socket Server和Client功能开发
 * @project java-netty-module
 * @version v1.0
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:16
 *
 * Netty 开发Socket Server与Client 总结
 * 相同部分：
 * 两部分开发的基础模板是一致的
 *  都是先定义线程池、定义服务启动类、设置Initializer、设置Handler这个模式
 *  并且在Initializer中定义的公共的处理类也是一致的
 *
 * 不同部分：
 *  在细节方面还是存在一定差别
 *   1、线程池 服务端两个，客户端一个
 *   2、使用的启动类在名称上面有差别
 *   3、handler数量  handler主要是针对线程池来的，所以服务端是两个，客户端是一个
 *
 * 触发交互开始
 *  在客户端的handler通过重写channelActive方法，来做首次交互的开始
 *
 */
package org.chen.architect.netty.socket;
