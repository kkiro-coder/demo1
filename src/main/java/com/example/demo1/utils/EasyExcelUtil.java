package com.example.demo1.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class EasyExcelUtil {
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer, int threshold) {
        return new AnalysisEventListener<T>() {
            private LinkedList<T> dataList = new LinkedList<>();

            @Override
            public void invoke(T data, AnalysisContext context) {
                dataList.add(data);
                if (dataList.size() == threshold) {
                    consumer.accept(dataList);
                    dataList.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                if (dataList.size() > 0) consumer.accept(dataList);
            }
        };
    }

    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer) {
        return getListener(consumer, 10);
    }
}
