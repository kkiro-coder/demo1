package com.example.demo1.mytest.juc;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) {
        System.out.println("begin park");
        LockSupport.park();
        // 许可证未持有，则让当前主线程持有，之后 park()方法立即返回
//        LockSupport.unpark(Thread.currentThread());
//
//        LockSupport.park();
        System.out.println("end park!");
    }

    @Test
    public void test1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park!");
            LockSupport.park();
            System.out.println("child thread end park!");
        });

        thread.start();

        Thread.sleep(5000);

        System.out.println("main(test1) thread begin unpark!");
        LockSupport.unpark(thread);
    }

    @Test
    public void test2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park!");


            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
            }
            System.out.println("child thread unpark!");
        });

        thread.start();
        Thread.sleep(5000);

        System.out.println("main(test2) thread unpark");

        thread.interrupt();
    }

}
