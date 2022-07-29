package com.example.demo1.service;

import com.example.demo1.entity.TreeData;
import com.example.demo1.entity.TreeNodeVo;

import java.util.List;
import java.util.Map;

public interface TreeDataService {
    void saveTreeData(TreeData data);
    void batchSaveTreeData(List<TreeData> dataList);

    List<Map<String, Object>> getAllTreeData();

    List<TreeNodeVo> getTreeVo();
}
