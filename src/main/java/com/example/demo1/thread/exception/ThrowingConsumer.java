package com.example.demo1.thread.exception;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> {
    static <T, E extends Throwable> Consumer<T> doInvoke(ThrowingConsumer<T, E> f) {
        return t -> {
            try {
                f.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    void accept(T t) throws E;
}
