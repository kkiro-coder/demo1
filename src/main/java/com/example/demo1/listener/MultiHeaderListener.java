package com.example.demo1.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;

public class MultiHeaderListener extends AnalysisEventListener<MultiHeaderModel> {


    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        System.out.println("extra");
        if (extra.getType().equals(CellExtraTypeEnum.MERGE)) {
            System.out.println(extra.getLastRowIndex());
            System.out.println(extra.getText());
        }
    }

    @Override
    public void invoke(MultiHeaderModel data, AnalysisContext context) {
        System.out.println("invoke");
        System.out.println(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("doAfterAllAnalysed");
    }
}
