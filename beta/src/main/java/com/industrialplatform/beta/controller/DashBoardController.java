package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @RequestMapping(value = "/getDBItemInfo",method = RequestMethod.GET)
    public Map<String,Object> getDBItemInfo(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",dashBoardService.getItemByID(1002));
        return map;
    }
}
