package com.example.demo1.utils;

import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class ProxyUtil {

    private static final String PATH = System.getProperty("user.dir") + File.separator + "tmp" + File.separator;

    public static void writeByteCode2File(String clazzName, Class<?>[] clazz, String outFileName){
        String outPath = PATH + outFileName;
        File file = new File(outPath);
        if (file.exists()) {
            if (!file.delete()) {
                return;
            }
        }
        boolean created = false;
        try {
            created = file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (created) {
            byte[] byteCodes = ProxyGenerator.generateProxyClass(clazzName, clazz);
            try (BufferedOutputStream bufOut =
                         new BufferedOutputStream(
                                 Files.newOutputStream(Paths.get(outPath)))) {
                bufOut.write(byteCodes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T, C> InvocationHandler getProxyHandler(Object proxiedObj, Consumer<T> pre, T t, Consumer<C> post, C c) {
        return (proxy, method, args) -> {
            pre.accept(t);
            Object res = method.invoke(proxiedObj, args);
            post.accept(c);
            return res;
        };
    }
}
