/**
 * @author chensj
 * @classDesc JAVA处理大文件 io与nio对比
 * @project architect-parent-module
 * @version v1.0
 * @date 2019-07-04 22:08
 *
 * <P>JAVA处理大文件，一般用BufferedReader,BufferedInputStream这类带缓冲的IO类，不过如果文件超大的话，更快的方式是采用MappedByteBuffer。</P>
 * <P>MappedByteBuffer是NIO引入的文件内存映射方案，读写性能极高。NIO最主要的就是实现了对异步操作的支持。</P>
 * <P>其中一种通过把一个套接字通道(SocketChannel)注册到一个选择器(Selector)中,不时调用后者的选择(select)方法就能返回满足的选择键(SelectionKey),键中包含了SOCKET事件信息。这就是select模型。</P>
 * <P>而SocketChannel的读写是通过一个类叫ByteBuffer来操作的.这个类本身的设计是不错的,比直接操作byte[]方便多了.</P>
 * <P>ByteBuffer有两种模式:直接/间接.</P>
 * <P>间接模式</P>
 * <P>最典型(也只有这么一种)的就是HeapByteBuffer,即操作堆内存 (byte[]).但是内存毕竟有限,如果我要发送一个1G的文件怎么办?不可能真的去分配1G的内存.这时就必须使用"直接"模式,</P>
 * <P>直接模式</P>
 * <P>即 MappedByteBuffer,文件映射.</P>
 * <P>在操作系统中，内存分两部分:物理内存、虚拟内存。</P>
 * <P>物理内存：指通过物理内存条而获得的内存空间</P>
 * <P>虚拟内存一般使用的是页面映像文件,即硬盘中的某个(某些)特殊的文件.操作系统负责页面文件内容的读写,这个过程叫"页面中断/切换".</P>
 * <P>MappedByteBuffer也是类似的,你可以把整个文件(不管文件有多大)看成是一个ByteBuffer.MappedByteBuffer 只是一种特殊的ByteBuffer，即是ByteBuffer的子类。</P>
 * <P>MappedByteBuffer将文件直接映射到内存（这里的内存指的是虚拟内存，并不是物理内存）。通常，可以映射整个文件，如果文件比较大的话可以分段进行映射，</P>
 * <P>只要指定文件的那个部分就可以。</P>
 * <h2>
 * MappedByteBuffer使用
 * </h2>
 *
 * <p>
 * FileChannel提供了map方法来把文件影射为内存映像文件： MappedByteBuffer map(int mode,long position,long size);
 * 可以把文件的从position开始的size大小的区域映射为内存映像文件，mode指出了可访问该内存映像文件的方式
 * </P>
 * <p>
 * mode类型
 * <ul>
 * <li>READ_ONLY,（只读）： 试图修改得到的缓冲区将导致抛出 ReadOnlyBufferException.(MapMode.READ_ONLY)</li>
 * <li>READ_WRITE（读/写）： 对得到的缓冲区的更改最终将传播到文件；该更改对映射到同一文件的其他程序不一定是可见的。 (MapMode.READ_WRITE)</li>
 * <li>PRIVATE（专用）： 对得到的缓冲区的更改不会传播到文件，并且该更改对映射到同一文件的其他程序也不是可见的；相反，会创建缓冲区已修改部分的专用副本。 (MapMode.PRIVATE)</li>
 * </ul>
 * </P>
 * <p>
 * MappedByteBuffer是ByteBuffer的子类，其扩充了三个方法
 * <ul>
 * <li>force()：缓冲区是READ_WRITE模式下，此方法对缓冲区内容的修改强行写入文件；</li>
 * <li>load()：将缓冲区的内容载入内存，并返回该缓冲区的引用；</li>
 * <li>isLoaded()：如果缓冲区的内容在物理内存中，则返回真，否则返回假；</li>
 * </ul>
 * </P>
 */
package com.winning.devops.cloud.io.memory;
