package com.example.demo1.mytest.genericity;

import java.util.ArrayList;
import java.util.List;

public class GenericityTest {
    public static void main(String[] args) {
        ArrayList<Garfield> garfields = new ArrayList<>();
        garfields.add(new Garfield());
        garfields.add(new Garfield());
        garfields.add(new Garfield());
        List<? extends Cat> cat1 = garfields; // ? extends T 通配子类的类型时，赋值时，值的类型必须是Cat的子类，如将Garfield赋值给Cat
        Cat cat = cat1.get(0);               //  获取时默认向上转型为Cat，不支持add元素
//        cat1.add(0, new Cat());            爆错
        System.out.println(cat);



        List<? super Cat> cat3; // ? super T 通配超类的类型时，赋值时，值的类型必须是Cat超类，如将animal赋值给下界是cat的集合，
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new Animal());
//        List<Persian> persians = new ArrayList<>();
//        persians.add(new Persian());
//        persians.add(new Persian());
//        cat3 = persians  如此赋值报错
        cat3 = animals;
        System.out.println(cat3);
        cat3.add(0, new Cat());   // 集合添加元素时仍然需要时Cat的子类
        cat3.add(0, new Garfield()); // 或者其本身
        Object object = cat3.get(0);
        System.out.println(object);      // 获取时会被转为Object 泛型会被擦除，但实例仍是其本身
        System.out.println(cat3.size());
    }
}
