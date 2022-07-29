package com.example.demo1.mytest.funci;

public class Outer {

    public Outer() {}

    public Outer(String field1) {
        this.field1 = field1;
    }

    private String field1;

    public class Inner {

        public Inner() {}

        public void print() {
            System.out.println(field1);
        }

    }

    public Inner newInner() {
        return new Inner();
    }

    public static void main(String[] args) {
        Outer outer = new Outer("outer field1");
        Inner inner = outer.newInner();
        inner.print();
    }

}
