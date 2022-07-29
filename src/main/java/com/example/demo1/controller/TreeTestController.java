package com.example.demo1.controller;

import com.example.demo1.entity.TreeData;
import com.example.demo1.entity.TreeNodeVo;
import com.example.demo1.service.TreeDataService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/tree/test")
@RestController
@Slf4j
public class TreeTestController {

    private TreeDataService treeDataSvc;

    @Autowired
    public void setTreeDataSvc(TreeDataService treeDataSvc) {
        this.treeDataSvc = treeDataSvc;
    }

    @PostMapping(value = "/insert")
    public void insertTreeData(@RequestBody List<TreeData> dataList) {
        treeDataSvc.batchSaveTreeData(dataList);
    }

    @GetMapping(value = "/getTree")
    public String getTree() {
        Gson gson = new Gson();
        return gson.toJson(treeDataSvc.getAllTreeData());
    }

    @PostMapping(value = "/getTreeVo")
    public List<TreeNodeVo> getTreeVo(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        return treeDataSvc.getTreeVo();
    }

}
