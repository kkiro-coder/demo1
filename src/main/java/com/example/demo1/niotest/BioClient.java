package com.example.demo1.niotest;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioClient {

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            try (Socket socket = new Socket("127.0.0.1", 8090)) {
                try (
                        BufferedOutputStream write2Server = new BufferedOutputStream(socket.getOutputStream());// 向服务器写
                ) {
                    write2Server.write(String.format("client sending the latest signal %s.", i).getBytes(StandardCharsets.UTF_8));
                    socket.shutdownOutput();
                    int len;
                    byte[] buff = new byte[1024];
                    try (BufferedInputStream readFromServer = new BufferedInputStream(socket.getInputStream())) {
                        StringBuffer stringBuffer = new StringBuffer();
                        while ( (len = readFromServer.read(buff)) != -1) {
                            stringBuffer.append(new String(buff, 0, len, StandardCharsets.UTF_8));
                        }
                        System.out.println(stringBuffer);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
