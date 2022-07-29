package com.example.demo1.utils;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolHelper {

    private static final String NAME = "ThreadPoolHelper";
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            6, 6, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(128),
            new BasicThreadFactory.Builder().namingPattern("pool-" + NAME + "-%d").daemon(true).build(),
            new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getInstance() {
        return threadPoolExecutor;
    }


}
