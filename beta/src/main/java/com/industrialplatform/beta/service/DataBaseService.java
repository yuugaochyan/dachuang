package com.industrialplatform.beta.service;

import com.industrialplatform.beta.mapper.dataBaseMapper;
import com.industrialplatform.beta.pojo.mqttTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DataBaseService {

    @Autowired
    dataBaseMapper dataBaseMapper;

//    获取MqttTag信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<mqttTag> getMqttTagList(){
        return dataBaseMapper.getMqttTagList();
    }

//  获取表的具体数据
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> getTableData(String tableName){
        Map<String,Object> map=new HashMap<>();
        HashMap<String,Object> list=new HashMap<>();
        List<String> cols=getTableColName(tableName);
        for (String col:cols) {
            List<String> colData = dataBaseMapper.getColData(tableName,col);
            list.put(col,colData);
        }
//        System.out.println(list);
        map.put("tableName",tableName);
        map.put("colList",cols);
        map.put("tableData",list);
        System.out.println(map);
        return map;
    }

//  获取表的列名
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getTableColName(String tableName){
        return dataBaseMapper.getTableColNameByTableName(tableName);
    }
}
