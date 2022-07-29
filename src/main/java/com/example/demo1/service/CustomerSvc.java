package com.example.demo1.service;

import com.example.demo1.entity.CustomerExcelModel;

import java.util.List;

public interface CustomerSvc {
    void saveCustomers(List<CustomerExcelModel> customerList);

    List<CustomerExcelModel> selectCustomers();
}
