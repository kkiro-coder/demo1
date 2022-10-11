package com.example.demo1.niotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class NioEchoServer {
    public static void main(String[] args) throws IOException {
        try (Selector selector = Selector.open();
             // select 通常与非阻塞channel配合使用，将channel注册到selector中，
             // selector本质也是操作系统提供的多路复用监听
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8801));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 用一个select专门注册为获取完成的连接事件

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                int channelCount = selector.select();
                if (channelCount > 0) {
                    for (SelectionKey selectionKey : selector.keys()) {
                        if (selectionKey.isAcceptable()) {
                            SocketChannel clientChannel = serverSocketChannel.accept();
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector, SelectionKey.OP_READ); // 若已经可以接受，则注册为读监听
                        } else if (selectionKey.isReadable()) {
                            try (SocketChannel clientChannel = (SocketChannel) selectionKey.channel()) {
                                clientChannel.read(buffer);
                                buffer.flip();
                                if (buffer.hasRemaining()) { // 客户端发什么返回什么
                                    clientChannel.write(buffer);
                                }
                                buffer.clear();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
