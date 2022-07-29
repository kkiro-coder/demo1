package com.example.demo1.service.impl;

import com.example.demo1.entity.CustomerExcelModel;
import com.example.demo1.entity.T_CUSTOMER;
import com.example.demo1.repo.entityrepo.CustomRepo;
import com.example.demo1.service.CustomerSvc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerSvcImpl implements CustomerSvc {

    private CustomRepo customRepo;

    @Autowired
    public void setCustomRepo(CustomRepo customRepo) {
        this.customRepo = customRepo;
    }

    @Override
    public void saveCustomers(List<CustomerExcelModel> customerList) {
        List<T_CUSTOMER> collect = customerList.stream().map(data -> {
            T_CUSTOMER customer = new T_CUSTOMER();
            customer.setUsername(data.getUserName());
            customer.setCouplename(data.getCoupleName());
            return customer;
        }).collect(Collectors.toList());
        customRepo.saveAll(collect);
        log.info("svc => saveCustomers");
    }

    @Override
    public List<CustomerExcelModel> selectCustomers() {
        List<CustomerExcelModel> res = new ArrayList<>();
        List<T_CUSTOMER> all = customRepo.findAll();
        if (CollectionUtils.isEmpty(all)) {
            return res;
        }
        all.stream().map(tc -> {
            CustomerExcelModel customerExcelModel = new CustomerExcelModel();
            customerExcelModel.setUserName(tc.getUsername());
            customerExcelModel.setCoupleName(tc.getCouplename());
            return customerExcelModel;
        }).forEach(res::add);
        return res;
    }
}
