package com.example.demo1.xzg.demo30;

/**
 * 指令重排序关于是否会是1存疑
 * 针对线程2指令重排情况可能是
 * ready = true -> while(!ready){} -> system.out.println(value) -> value = 2
 * ------------   ----------------   --------------------------    ---------
 *    thread2         thread1                 thread1               thread2
 * 这种情况下thread1的打印应该是value=1
 *  注：x86架构的cpu不会重排
 */
public class Demo30_3 {
    private static boolean ready = false;

    private static int value = 1;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!ready) {

            }
            System.out.println(Thread.currentThread().getName() + " ready go! value=" + value);

        }, "thread-1-while");
        Thread t2 = new Thread(() -> {
            value = 2;
            ready = true;
//            System.out.println(Thread.currentThread().getName() + " set value=" + value);
        },"thread-2-set-t");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
