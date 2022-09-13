package com.example.demo1.niotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class BioEchoServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            Objects.requireNonNull(serverSocket, "获取socket失败").bind(new InetSocketAddress("127.0.0.1", 8090)); // 监听本地的90端口
            while (true) {
                Socket clientSocket = serverSocket.accept(); // 获取客户端ack后的socket，此时tcp的三次握手已经完成
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为每个可用的socket创建一个线程去处理
     */
    private static final class ClientHandler implements Runnable{

        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            byte[] buf = new byte[1024 * 4];
            while (true) {
                try {
                    socket.getInputStream().read(buf);
                    socket.getOutputStream().write(buf);
                } catch (IOException e) {
                    break;
                }
            }
        }
    }


}
