package com.industrialplatform.beta.controller;


import com.industrialplatform.beta.service.DataBaseService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DataBaseController {

    @Autowired
    private DataBaseService dataBaseService;

    @RequestMapping(value = "/getTableData",method = RequestMethod.POST)
    public Map<String,Object> getTableData(@RequestParam("dataSource") String tableName){
        Map<String,Object> map=new HashMap<>();
        map.put("data",dataBaseService.getTableData(tableName));
        map.put("status",200);
        map.put("msg","获取数据成功！");
        return map;
    }
}
