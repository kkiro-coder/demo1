package com.example.demo1.mytest.aqs;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.stream.IntStream;

public class NonReentrantLockTest {

    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition empty = lock.newCondition();
    final static Condition full = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static int threshold = 20;

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "run");
                    while (queue.size() == threshold) {
                        System.out.println(Thread.currentThread().getName() + "await");
                        full.await(); //当前线程加入条件等待队列
                    }

                    System.out.println("add elem");
                    queue.add("elem");
                    empty.signalAll(); // 唤醒因为queue变成空而挂起的线程
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, "producer" + i).start();
        }

        for (int i = 0; i < 15; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "run");
                    while (queue.size() == 0) {
                        System.out.println(Thread.currentThread().getName() + "await");
                        empty.await();
                    }

                    System.out.println("pull" + queue.poll());
                    full.signalAll(); // 唤醒因为queue达到阈值而挂起的线程
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, "consumer" + i).start();
        }
    }
}
