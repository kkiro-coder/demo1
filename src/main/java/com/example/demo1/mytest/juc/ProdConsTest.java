package com.example.demo1.mytest.juc;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.stream.IntStream;

public class ProdConsTest {

    static final NonReentrantLock lock = new NonReentrantLock();
    static final Condition notEmpty = lock.newCondition();
    static final Condition notFull = lock.newCondition();
    static final Queue<String> queue = new LinkedBlockingQueue<>();
    static final int threshold = 20;
    static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).forEach(x -> new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " start!");
                while (queue.size() == threshold) {
                    System.out.println("queue is full!");
                    notEmpty.await();
                }
                String elem = "elem" + counter.getAndIncrement();
                queue.add(elem);
                notFull.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "producer" + x).start());
        IntStream.rangeClosed(1, 10).forEach(y -> new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " start!");
                while (queue.size() == 0) {
                    System.out.println("queue is empty!");
                    notFull.await();
                }
                String elem = queue.poll();
                System.out.println(elem);
                notEmpty.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "consumer" + y).start());
    }
}
