package com.zou.zyqblogs.service.impl;

import com.github.pagehelper.PageHelper;
import com.zou.zyqblogs.mapper.GeneralMapper;
import com.zou.zyqblogs.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GeneralServiceImpl implements GeneralService {
    @Autowired
    @Qualifier("generalMapper")
    private GeneralMapper gm;

    private String tableName;

    public GeneralServiceImpl(){
    }
    public GeneralServiceImpl(String tableName){
        this.tableName=tableName;
    }

    //查询所表中的数据数
    @Override
    public int finallObjectNum(Map<String, Object> map) {
        map.put("tableName",tableName);
        return gm.finallObjectNum(map);
    }

    //查询表中的所有数据
    @Override
    public List<Map<String, Object>> finallObjects(Map<String, Object> map) {
        map.put("tableName",tableName);
        if(map.get("limit")!=null && map.get("page")!=null && map.get("orderBy")!=null){
            int limit = Integer.parseInt((String) map.get("limit"));
            int page = Integer.parseInt((String) map.get("page"));
            PageHelper.startPage(page,limit,(String) map.get("orderBy"));
            map.remove("limit");
            map.remove("page");
            map.remove("orderBy");
        }
        return gm.finallObjects(map);
    }

    //查询表中指定条件的数据
    @Override
    public Map<String, Object> finallObjectById(Map<String, Object> map) {
        map.put("tableName",tableName);
        return gm.finallObjectById(map);
    }

    //为表添加数据
    @Override
    public int addObjects(Map<String, Object> map) {
        map.put("tableName",tableName);
        return gm.addObjects(map);
    }

    //批量删除
    @Override
    public int delObjects(Map<String, Object> map) {
        map.put("tableName",tableName);
        return gm.delObjects(map);
    }

    //删除
    @Override
    public int delObject(Map<String, Object> map) {
        map.put("tableName",tableName);
        return gm.delObject(map);
    }

    @Override
    public List<Map<String, Object>> finallObjectByFields(Map<String, Object> map) {
        map.put("tableName",tableName);
        return gm.finallObjectByFields(map);
    }

//    @Override
//    public int updObject(Map<String, Object> map) {
//        map.put("tableName",tableName);
//        return gm.updObject(map);
//    }
}

