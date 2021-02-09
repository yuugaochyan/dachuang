package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.pojo.*;
import com.industrialplatform.beta.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;


//    调取仪表盘所有item信息
    @RequestMapping(value = "/getDBItemInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getDBitemInfo(int dbID){
        Map<String,Object> map=new HashMap<>();
        map.put("status",200);
        map.put("data",dashBoardService.getdbItemBydbID(dbID));
        map.put("msg","获取仪表盘组件信息成功！");
        return map;
    }

//    存储柱状图
    @RequestMapping(value = "/addBarGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addBarGraph(@RequestBody Graph<BarChart> barGraph){
        System.out.println(barGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=dashBoardService.addBarGraph(barGraph.getUserID(),barGraph);
        if(ID!=-1){
            map.put("status",200);
            map.put("msg","柱状图添加成功!");
            map.put("ID",ID);
        }
        else{
            map.put("status",404);
            map.put("msg","柱状图添加失败!");
        }
        return map;
    }

    //    存储折线图
    @RequestMapping(value = "/addLineGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addLineGraph(@RequestBody Graph<LineChart> lineGraph){
        System.out.println(lineGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=dashBoardService.addLineGraph(lineGraph.getUserID(), lineGraph);
        if(ID!=-1){
            map.put("status",200);
            map.put("msg","柱状图添加成功!");
            map.put("ID",ID);
        }
        else{
            map.put("status",404);
            map.put("msg","折线图添加失败!");
        }
        return map;
    }

    //    存储饼图
    @RequestMapping(value = "/addPieGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addPieGraph(@RequestBody Graph<PieChart> pieGraph){
        System.out.println(pieGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=dashBoardService.addPieGraph(pieGraph.getUserID(),pieGraph);
        if(ID!=-1){
            map.put("status",200);
            map.put("msg","饼图添加成功!");
            map.put("ID",ID);
        }
        else{
            map.put("status",404);
            map.put("msg","饼图添加失败!");
        }
        return map;
    }

    //    存储散点图
    @RequestMapping(value = "/addScatterGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addScatterGraph(@RequestBody Graph<ScatterChart> scatterGraph){
        scatterGraph.getChart().setXType("value");
        scatterGraph.getChart().setYType("value");
        System.out.println(scatterGraph);
//        System.out.println(userID);
        Map<String,Object> map=new HashMap<>();
        int ID=dashBoardService.addScatterGraph(scatterGraph.getUserID(),scatterGraph);
        if(ID!=-1){
            map.put("status",200);
            map.put("msg","散点图添加成功!");
            map.put("ID",ID);
        }
        else{
            map.put("status",404);
            map.put("msg","散点图添加失败!");
        }
        return map;
    }

//    存储table
    @RequestMapping(value = "/addTable",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addTable(@RequestBody Table table){
        System.out.println(table);
//        System.out.println(userID);
        Map<String,Object> map=new HashMap<>();
        int ID=dashBoardService.addTable(table);
        if(ID!=-1){
            map.put("status",200);
            map.put("msg","table添加成功!");
            map.put("ID",ID);
        }
        else{
            map.put("status",404);
            map.put("msg","table添加失败!");
        }
        return map;
    }

//    存储mqtt
    @RequestMapping(value = "/addMQTT",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addMQTT(@RequestBody mqttGraph mqttGraph){
        System.out.println(mqttGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=dashBoardService.addMQTTGraph(mqttGraph);
        if(ID!=-1){
            map.put("status",200);
            map.put("msg","mqtt添加成功!");
            map.put("ID",ID);
        }
        else{
            map.put("status",404);
            map.put("msg","mqtt添加失败!");
        }
        return map;
    }


//    根据表名将数据调取为table格式
    @RequestMapping(value = "/getTable",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getTable(String dataSource){
//        System.out.println(dataSource);
        Map<String,Object> map=new HashMap<>();
        Table table=dashBoardService.getTableByTableName(dataSource);
        if(table!=null){
            map.put("status",200);
            map.put("msg","table数据获取成功");
            map.put("data",table);
        }
        else{
            map.put("status",404);
            map.put("msg","table数据获取失败");
        }
        return map;
    }




}
