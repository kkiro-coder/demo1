package com.example.demo1.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.demo1.entity.CustomerExcelModel;
import com.example.demo1.entity.T_CUSTOMER;
import com.example.demo1.repo.entityrepo.CustomRepo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomerDataListener extends AnalysisEventListener<CustomerExcelModel> {


    private static final int BATCH_COUNT = 5;
    List<CustomerExcelModel> dataList = new ArrayList<>();

    private CustomRepo customRepo;

    public CustomerDataListener(CustomRepo customRepo) {
        this.customRepo = customRepo;
    }

    @Override
    public void invoke(CustomerExcelModel data, AnalysisContext analysisContext) {
        log.info("invoke");
        dataList.add(data);
        if (dataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            dataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("doAfterAllAnalysed");
    }

    private void saveData() {
//        List<T_CUSTOMER> collect = dataList.stream().map(data -> {
//            T_CUSTOMER customer = new T_CUSTOMER();
//            customer.setUsername(data.getUserName());
//            customer.setCouplename(data.getCoupleName());
//            return customer;
//        }).collect(Collectors.toList());
//        customRepo.saveAll(collect);
//        log.info("saveData");
    }


}
