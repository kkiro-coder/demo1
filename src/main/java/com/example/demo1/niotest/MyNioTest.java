package com.example.demo1.niotest;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MyNioTest {

    @Test
    public void channelDemo() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("nio-data.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(8); // 分配空间
        int byteRead = channel.read(buf); // 往byte buffer写数据 position移动 limit capacity不变
        while (byteRead != -1) {
            System.out.println("Read:" + byteRead);
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get()); // 从 byteBuffer中读数据 limit = position; position = 0;
            }
            buf.clear();
            byteRead = channel.read(buf);
        }
    }

    /**
     * Java NIO开始支持scatter/gather，scatter/gather用于描述从Channel（译者注：Channel在中文经常翻译为通道）中读取或者写入到Channel的操作。
     * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
     * 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
     *
     * scatter / gather经常用于需要将传输的数据分开处理的场合，例如传输一个由消息头和消息体组成的消息，你可能会将消息体和消息头分散到不同的buffer中，这样你可以方便的处理消息头和消息体。
     */
    @Test
    public void testScatterting() throws IOException {
        ByteBuffer head = ByteBuffer.allocate(256);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] buffers = {head, body};
        RandomAccessFile accessFile = new RandomAccessFile("nio-data.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        channel.read(buffers); // 从channel将数据读数据并写到buffers
        channel.write(buffers); // 往channel中写所有buffer读到的数据
    }


    /**
     * 两种方式
     * @throws IOException
     */
    @Test
    public void testChannel2Channel() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("nio-data.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("data.txt", "rw");
        FileChannel sourceChannel = accessFile.getChannel();
        FileChannel targetChannel = writeFile.getChannel();

        targetChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
//        sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
    }

    /**
     * Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
     * 这样，一个单独的线程可以管理多个channel，从而管理多个网络连接
     */
    @Test
    public void testSelector() {

    }
}
