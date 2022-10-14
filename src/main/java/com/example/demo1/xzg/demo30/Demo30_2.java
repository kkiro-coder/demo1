package com.example.demo1.xzg.demo30;

import java.util.stream.IntStream;

/**
 * 一个线程竞争共享变量的例子
 */
public class Demo30_2 {
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
//            synchronized (Demo30_2.class) {
                IntStream.rangeClosed(1, 10000).forEach(i -> count++);
//            }
        });
        Thread t2 = new Thread(() -> {
//            synchronized (Demo30_2.class) {
                IntStream.rangeClosed(1, 10000).forEach(i -> count++);
//            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count);
    }
}
