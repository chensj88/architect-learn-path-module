package org.chen.architect.thread;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 线程创建样例demo
 * @project architect-parent-module
 * @date 2019-07-03 21:23
 * <ul>
 * <li>
 * 什么是线程？ <br>
 * 线程就是一个执行路径，每个线程都互不影响
 * </li>
 * <li>
 * 什么是多线程? <br>
 * 多线程：在一个进程中，有多个不同的执行路径，并行进行，目的是为了提供程序执行效率
 * </li>
 * <li>
 * 线程分类: 用户线程、守护线程<br>
 * 具体分类：主线程、子线程、GC线程<br>
 * 在一个进程中，一定会有主线程，主线程就是程序的入口<br>
 * </li>
 * <li>
 * 同步概念：代码从上向下进行执行<br>
 * 异步概念：开一个新的执行路径，进行执行，不会影响其他线程<br>
 * 多线程包含异步概率<br>
 * </li>
 * </ul>
 */
public class ThreadDemo01 {

    public static void main(String[] args) {
        System.out.println("主线程名称：[" + Thread.currentThread().getName() + "] 开始执行");
        // 线程创建方式
        // 1. 继承Thread类
        // 启动线程
        new ThreadDemoExtendThread().start();
        // 2. 实现Runnable接口
        // 启动线程
        new Thread(new ThreadDemoImplementRunnable()).start();
        // 3. 匿名内部类方式
        // 创建并启动线程
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    String name = Thread.currentThread().getName();
                    System.out.println(getClass()+ " 子线程名称：[" + name + "]  i:" + i);
                }
            }
        }).start();
        // 4. 使用线程池进行管理

        for (int i = 0; i < 10; i++) {
            System.out.println("主线程名称：[" + Thread.currentThread().getName() + "]，结果: i:" + i);
        }
        System.out.println("主线程名称：[" + Thread.currentThread().getName() + "] 执行结束");
    }
}


/**
 * 实现Runnable接口
 */
class ThreadDemoImplementRunnable implements Runnable{

    public void run() {
        for (int i = 0; i < 10; i++) {
            String name = Thread.currentThread().getName();
            System.out.println(getClass()+ " 子线程名称：[" + name + "]  i:" + i);
        }
    }
}

/**
 * 继承Thread类,重写run方法
 * 缺点：java不支持多继承
 */
class ThreadDemoExtendThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            String name = Thread.currentThread().getName();
            System.out.println(getClass()+ " 子线程名称：[" + name + "]  i:" + i);
        }
    }
}
