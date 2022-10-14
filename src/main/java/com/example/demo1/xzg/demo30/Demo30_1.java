package com.example.demo1.xzg.demo30;

/**
 * 一个并非线程间内存可见性但看起来是这个原因的例子
 * 先启动线程1，然后用线程2去修改标志位希望打断线程1的循环，结果是无法打断
 * 涉及cpu缓存一致性问题，因为MESI迟早可以被看到，但无法被立即看到，所以没有sleep时，最终是可以打破循环的
 * 此处是因为JIT编译的原因，ready设置为volatile也可以达到效果
 */
public class Demo30_1 {
    private static int count = 0;
    private static boolean ready = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (ready) {
                count++;
            }
            System.out.println("count = " + count);
        });

        Thread t2 = new Thread(() -> {
            ready = false;
        });

        t1.start();
//        Thread.sleep(1);
        t2.start();

        t1.join();
        t2.join();
    }
}
