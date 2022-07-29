package com.example.demo1.mytest.aqs;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestUnSafe {
    private static Unsafe unsafe;

    private static long stateOffset;

    private volatile boolean state = true;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(TestUnSafe.class.getDeclaredField("state"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isState() {
        return state;
    }

    public boolean getState() {
        return unsafe.getBooleanVolatile(this, stateOffset);
    }

    public static void main(String[] args) {
        TestUnSafe testUnSafe = new TestUnSafe();
        boolean casRes = unsafe.compareAndSwapInt(testUnSafe, stateOffset, 1, 0);
        System.out.println(testUnSafe.isState());
        System.out.println(testUnSafe.getState());
    }
}
