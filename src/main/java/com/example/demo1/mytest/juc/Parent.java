package com.example.demo1.mytest.juc;

public class Parent {

    private boolean isCommon;

    public Parent(boolean isCommon) {
        this.isCommon = isCommon;
    }

    public Parent() {
    }

    public void doRun() {
        if (isCommon) {

        } else {
            System.out.println("parent class do run！");
        }
    }

    private class ChildOne extends Son{
        public void doRun() {
            System.out.println(this.getClass().getName() + "do run");
        }
    }

    public static void main(String[] args) {
        System.out.println(ChildOne.class.isAssignableFrom(Son.class));
    }
}
