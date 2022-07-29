package com.example.demo1.mytest.aqs;


import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockList {
    private ArrayList<String> arrayList = new ArrayList<>();

    private volatile ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * 读写锁 --->
     * 不同线程读写互斥，相同线程有写锁优先，后可获取读锁
     * 写锁同线程可重入，不同线程互斥，
     * 读锁共享
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void add(String elem) {
        writeLock.lock();
        try {
            arrayList.add(elem);
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(String elem) {
        writeLock.lock();
        try {
            arrayList.remove(elem);
        } finally {
            writeLock.unlock();
        }
    }

    public String getElem(int idx) {
        readLock.lock();
        try {
            return arrayList.get(idx);
        } finally {
            readLock.unlock();
        }
    }

}
