package com.example.demo1.xzg.demo5;

public class Demo5_1 {
    public static void main(String[] args) {
//        int count = countOneBits(0xfffffffd);
//        System.out.println(count);
        dec();
    }

    public static int countOneBits(int num) {
        int count = 0;
        while (num != 0) {
            if ((num&1) == 1) {
                count++;
            }
            num >>>= 1; // 高位补0 负数会变整数 -3补码 补码的设计思路来自于有限2进制位数超长部分会被截断
            // 3 0x3
            // -3 0xfffffffd
//            num >>= 1; // 高位补符号位
        }
        return count;
    }

    /**
     *
     */
    public static void dec() {
//        int x = 0xfffffffd + 0x3; // 0
        int x = 0xffffffff;
        int s1 = 0x4fffffff >> 29;

//        System.out.println("" + x);
        System.out.println("" + s1);
    }
}
