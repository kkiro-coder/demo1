package com.example.demo1.thread;

import com.example.demo1.entity.T_COFFEE;
import com.example.demo1.entity.T_COFFEE_ORDER;
import com.example.demo1.entity.T_CUSTOMER;
import com.example.demo1.repo.entityrepo.CoffeOrderRepo;

import java.util.List;
import java.util.concurrent.Callable;

public class CoffeeOrderThread implements Callable<T_COFFEE_ORDER> {

    private CoffeOrderRepo coffeOrderRepo;

    private List<T_COFFEE> coffees;

    private T_CUSTOMER customer;


    public CoffeeOrderThread() {
    }

    public CoffeeOrderThread(CoffeOrderRepo coffeOrderRepo, List<T_COFFEE> coffees, T_CUSTOMER customer) {
        this.coffeOrderRepo = coffeOrderRepo;
        this.coffees = coffees;
        this.customer = customer;
    }

    @Override
    public T_COFFEE_ORDER call() throws Exception {
        return null;
    }

    private T_COFFEE_ORDER buildCofeeOrder() {
        String coffeeids = "";
        return null;
    }
}
