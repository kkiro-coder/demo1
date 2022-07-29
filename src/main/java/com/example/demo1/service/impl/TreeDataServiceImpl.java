package com.example.demo1.service.impl;

import com.example.demo1.entity.TreeData;
import com.example.demo1.entity.TreeNodeVo;
import com.example.demo1.repo.entityrepo.TreeDataRepo;
import com.example.demo1.service.TreeDataService;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TreeDataServiceImpl implements TreeDataService {

    private TreeDataRepo repo;

    @Autowired
    public void setRepo(TreeDataRepo repo) {
        this.repo = repo;
    }

    @Override
    public void saveTreeData(TreeData treeData) {

    }

    @Override
    public void batchSaveTreeData(List<TreeData> dataList) {
        repo.saveAll(dataList);
    }

    @Override
    public List<Map<String, Object>> getAllTreeData() {
        List<TreeData> all = repo.findAll();
        List<Map<String, Object>> dataMapList = all.stream().map(data -> {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("name", data.getName());
            dataMap.put("id", data.getId());
            dataMap.put("level", data.getLevel());
            dataMap.put("parentId", data.getParentId());
            return dataMap;
        }).collect(Collectors.toList());
        List<Map<String, Object>> trees = dataMapList.stream().filter(data -> MapUtils.getLong(data, "parentId").equals(0L)).collect(Collectors.toList());
        Map<Long, List<Map<String, Object>>> map = new HashMap<>();
        for (Map<String, Object> data : dataMapList) {
            Long id = MapUtils.getLong(data, "parentId");
            List<Map<String, Object>> childData = map.computeIfAbsent(id, k -> new ArrayList<>());
            childData.add(data);
        }
        for (Map<String, Object> root : trees) {
            buildTree2(root, MapUtils.getLong(root, "id"), map);
        }
        return trees;
    }

    @Override
    public List<TreeNodeVo> getTreeVo() {
        List<TreeData> all = repo.findAll();
        List<TreeNodeVo> convert = all.stream().map(data -> {
            TreeNodeVo treeNodeVo = new TreeNodeVo();
            treeNodeVo.setId(data.getId());
            treeNodeVo.setLevel(data.getLevel());
            treeNodeVo.setName(data.getName());
            treeNodeVo.setParentId(data.getParentId());
            return treeNodeVo;
        }).collect(Collectors.toList());
        Map<Long, List<TreeNodeVo>> pGroup = convert.stream().collect(Collectors.groupingBy(TreeNodeVo::getParentId));
//        List<TreeNodeVo> trees = convert.stream().filter(x -> x.getParentId().equals(0L)).collect(Collectors.toList()); // 筛选出父级菜单
        List<TreeNodeVo> trees = pGroup.get(0L); // 筛选出父级菜单
        trees.forEach(root -> buildTreeVo(root, root.getId(), pGroup));
        return trees;
    }

  /**
   * @author kkiro
   * @param node
   * @param idAbstractHandlerMethodMapping
   * @param pGroup
   * @return void
   * @creation 2020/12/23 14:40
   * @description
   */
    private void buildTreeVo(TreeNodeVo node, Long id, Map<Long, List<TreeNodeVo>> pGroup) {
        if (!pGroup.containsKey(id)) {
            node.setLeaf(true);
            return;
        }
        List<TreeNodeVo> childrenList = node.getChildrenList();
        if (null == childrenList) {
            childrenList = new ArrayList<>();
            node.setChildrenList(childrenList);
        }
        childrenList.addAll(pGroup.get(id));
        childrenList.forEach(child -> buildTreeVo(child, child.getId(), pGroup));
    }

    private void buildTree2(Map<String, Object> node, Long id, Map<Long, List<Map<String, Object>>> pGroup) {
        if (!pGroup.containsKey(id)) {
            node.put("leaf", true);
            return;
        }
        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) node.get("childrenList");
        if (null == childrenList) {
            childrenList = new ArrayList<>();
            node.put("childrenList", childrenList);
        }
        childrenList.addAll(pGroup.get(id));
        for (Map<String, Object> map : childrenList) {
            buildTree2(map, MapUtils.getLong(map, "id"), pGroup);
        }

    }
}
