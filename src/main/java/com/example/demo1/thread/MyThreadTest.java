package com.example.demo1.thread;

import com.example.demo1.thread.exception.ApiException;
import com.example.demo1.thread.exception.ThrowingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class MyThreadTest {

    private static ConcurrentHashMap<String, Exception> exMap = new ConcurrentHashMap<>();

    private static ThreadLocal<ConcurrentHashMap<String, String>> local = new ThreadLocal<>();

    private static final Thread.UncaughtExceptionHandler exHandler = new Thread.UncaughtExceptionHandler() {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
                exMap.put(t.getName(), (Exception) e);
        }
    };

    public static void main(String[] args) throws InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool(10, ForkJoinPool.defaultForkJoinWorkerThreadFactory, exHandler, false);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ForkJoinTask<?> task = forkJoinPool.submit(() -> {
            list.parallelStream().forEach(ThrowingConsumer.doInvoke((i) -> {
                try {
                    List<String> a = new ArrayList<>();
                    a.add("d");
                    a.get(3);
                    log.error("{}", i);
                } catch (Exception e) {
                    throw new ApiException("API " + i + " 出错");
                }
            }));
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println(exMap);
        try {
            task.get();
        } catch (ExecutionException e) {
            log.error("出错啦", e);
        }
    }

    static class Food{}
    static class Meat extends Food{}

    static class Pork extends Meat{}
    static class Beef extends Meat{}

    static class Fruit extends Food{
        private String name;
    }

    static class Apple extends Fruit{
        private Double price;
    }
    static class RedApple extends Apple{}
    static class GreenApple extends Apple{}


    static class Banana extends Fruit{
        private Double price;
    }

    static class Strawberry extends Fruit{
        private Double price;
    }
}
