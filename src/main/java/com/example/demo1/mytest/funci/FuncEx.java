package com.example.demo1.mytest.funci;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class FuncEx {
    @FunctionalInterface
    public interface FunctionEx<T, R, E extends Throwable>{
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface ConsumerEx<T, E extends Throwable>{
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface SupplierEx<R, E extends Throwable>{
        R get() throws E;
    }

    @FunctionalInterface
    public interface PredicateEx<T, E extends Throwable>{
        boolean test(T t) throws E;
    }


    public static <T, R, E extends Throwable> Function<T, R> wrap(FunctionEx<T, R, E> fx) {
        return t -> {
            try {
                return fx.apply(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T, E extends Throwable> Consumer<T> wrap(ConsumerEx<T, E> cx) {
        return t -> {
            try {
                cx.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <R, E extends Throwable> Supplier<R> wrap(SupplierEx<R, E> sx) {
        return () -> {
            try {
                return sx.get();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T, E extends Throwable> Predicate<T> wrap(PredicateEx<T, E> px) {
        return t -> {
            try {
                return px.test(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
