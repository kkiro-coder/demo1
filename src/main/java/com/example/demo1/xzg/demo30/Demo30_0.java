package com.example.demo1.xzg.demo30;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Demo30_0 {
    public static void main(String[] args) throws IOException {
        try (PipedOutputStream out = new PipedOutputStream();
             PipedInputStream in = new PipedInputStream(out)) {
            Thread t1 = new Thread(() -> {
                String flag = "it a false value for you";
                try {
                    out.write(flag.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Thread t2 = new Thread(() -> {
                byte[] bytes = new byte[100];
                try {
                    in.read(bytes);
                    boolean value = Boolean.parseBoolean(Arrays.toString(bytes));
                    System.out.println(Thread.currentThread().getName()  + " I got it : " + value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, "接受消息的线程");

            t1.start();
            Thread.sleep(1000);
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
