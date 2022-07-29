package com.example.demo1.xzg.demo9;

import com.example.demo1.xzg.demo9.domain.B;
import com.example.demo1.xzg.demo9.domain.MyObject;
import org.openjdk.jol.info.ClassLayout;

public class Demo9_1 {
    public static void main(String[] args) {
        String s = ClassLayout.parseInstance(new MyObject()).toPrintable();
        String s2 = ClassLayout.parseInstance(new B()).toPrintable();
        System.out.println(s2);
    }
}
