package com.example.demo1.mytest.genericity;

import java.util.ArrayList;
import java.util.List;

public class GenericityTest {
    public static void main(String[] args) {
        ArrayList<Caffi> caffis = new ArrayList<>();
        caffis.add(new Caffi());
        caffis.add(new Caffi());
        caffis.add(new Caffi());
        List<? extends Cat> cat1 = caffis;
        System.out.println(cat1);
        List<? super Cat> cat2 = new ArrayList<>();
        cat2.add(new Caffi());


        List<? super Cat> cat3;
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new Animal());
        List<Persian> persians = new ArrayList<>();
        persians.add(new Persian());
        persians.add(new Persian());
        cat3 = animals;
        System.out.println(cat3);
    }
}
