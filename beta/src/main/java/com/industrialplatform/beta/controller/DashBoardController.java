package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.mapper.EquipmentRepairMapper;
import com.industrialplatform.beta.mapper.dbItemMapper;
import com.industrialplatform.beta.pojo.*;
import com.industrialplatform.beta.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @Autowired
    private com.industrialplatform.beta.mapper.dbItemMapper dbItemMapper;


//    调取仪表盘所有item信息
    @RequestMapping(value = "/getDBItemInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getDBitemInfo(int dbID){
        System.out.println(dbID);
        Map<String,Object> map=new HashMap<>();
        map.put("status",200);
        map.put("data",dashBoardService.getdbItemBydbID(dbID));
        map.put("msg","获取仪表盘组件信息成功！");
        return map;
    }

//    创建仪表盘
    @RequestMapping(value = "/addDB",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addDB(DashBoard dashBoard){
        Map<String,Object> map=new HashMap<>();
        if (dashBoardService.createDB(dashBoard)){
            map.put("status",200);
            map.put("msg","仪表盘创建成功！");
        }else{
            map.put("status",404);
            map.put("msg","仪表盘创建失败！");
        }
        return map;
    }

//    获取仪表盘列表
    @RequestMapping(value = "/getDBList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getDBList(@RequestParam("userID") int userID,
                                        @RequestParam("pagenum") int pagenum,
                                        @RequestParam("pagesize") int pagesize){
//        System.out.println(pagenum);
        Map<String,Object> map=new HashMap<>();
            map.put("status",200);
            map.put("msg","仪表盘列表获取成功！");
            map.put("data",dashBoardService.getDBList(userID,pagenum,pagesize));
        return map;
    }


//    获取图表列表
    @RequestMapping(value = "/getGraphList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getGraphList(@RequestParam("userID") int userID,
                                           @RequestParam("pagenum") int pagenum,
                                           @RequestParam("pagesize") int pagesize){
        Map<String,Object> map=new HashMap<>();
        map.put("status",200);
        map.put("msg","图表列表获取成功！");
        map.put("data",dashBoardService.getGraphList(userID,pagenum,pagesize));
        return map;
    }


//    删除仪表盘
    @RequestMapping(value = "/deleteDB",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteDB(int dbID){
        Map<String,Object> map=new HashMap<>();
        if(dashBoardService.deleteDB(dbID)) {
            map.put("status",200);
            map.put("msg","删除成功!");
        }else{
            map.put("status",404);
            map.put("msg","删除失败!");
        }
        return map;
    }


//    删除图表
    @RequestMapping(value = "/deleteGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteGraph(int graphID){
        Map<String,Object> map=new HashMap<>();
        if(dashBoardService.deleteGraph(graphID)) {
            map.put("status",200);
            map.put("msg","删除成功!");
        }else{
            map.put("status",404);
            map.put("msg","删除失败!");
        }
        return map;
    }


//    从仪表盘中移除图表
    @RequestMapping(value = "/removeGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> removeGraph(int itemID){
        Map<String,Object> map=new HashMap<>();
        if(dashBoardService.removeGraph(itemID)) {
            map.put("status",200);
            map.put("msg","删除成功!");
        }else{
            map.put("status",404);
            map.put("msg","删除失败!");
        }
        return map;
    }


//    获取图表信息
    @RequestMapping(value = "/getGraphInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getGraphInfo(int graphID){
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> DATA=new HashMap<>();
        DATA.put("type",dbItemMapper.getGraphTypeByGraphID(graphID));
        DATA.put("Graph",dashBoardService.getGraphInfo(graphID));
        map.put("status",200);
        map.put("msg","图表信息获取成功！");
        map.put("data",DATA);
        return map;
    }


//    保存item位置信息
    @RequestMapping(value = "/saveItemLoc",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> saveItemLoc(@RequestBody List<dashBoardItem> items){
        Map<String,Object> map=new HashMap<>();
        if(dashBoardService.saveItemLoc(items)){
            map.put("status",200);
            map.put("msg","保存成功！");
        }else{
            map.put("status",404);
            map.put("msg","保存失败！");
        }
        return map;
    }

//    增添图表至仪表盘中
    @RequestMapping(value = "/addGraphToDB",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addGraphToDB(int dbID,int[] tblist){
        System.out.println("addGraphToDB");
        Map<String,Object> map=new HashMap<>();
        if(dashBoardService.addGraphsToDashBoard(tblist,dbID)){
            map.put("status",200);
            map.put("msg","图表添加成功！");
        }else{
            map.put("status",404);
            map.put("msg","图表添加失败！");
        }
        return map;
    }

//    存储/编辑柱状图
    @RequestMapping(value = "/addBarGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addBarGraph(@RequestBody Graph<BarChart> barGraph){
        System.out.println(barGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=-1;
        if(barGraph.getGraphID()==0){
            ID=dashBoardService.addBarGraph(barGraph.getUserID(),barGraph);
        }else{
            dashBoardService.deleteGraph(barGraph.getGraphID());
            ID=dashBoardService.addBarGraph(barGraph.getUserID(),barGraph);
        }
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

    //    存储/编辑折线图
    @RequestMapping(value = "/addLineGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addLineGraph(@RequestBody Graph<LineChart> lineGraph){
        System.out.println(lineGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=-1;
        if(lineGraph.getGraphID()==0) {
            ID = dashBoardService.addLineGraph(lineGraph.getUserID(), lineGraph);
        }else{
            dashBoardService.deleteGraph(lineGraph.getGraphID());
            ID = dashBoardService.addLineGraph(lineGraph.getUserID(), lineGraph);
        }
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

    //    存储/编辑饼图
    @RequestMapping(value = "/addPieGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addPieGraph(@RequestBody Graph<PieChart> pieGraph){
        System.out.println(pieGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=-1;
        if(pieGraph.getGraphID()==0) {
            ID = dashBoardService.addPieGraph(pieGraph.getUserID(), pieGraph);
        }else{
            dashBoardService.deleteGraph(pieGraph.getGraphID());
            ID = dashBoardService.addPieGraph(pieGraph.getUserID(), pieGraph);
        }
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

    //    存储/编辑散点图
    @RequestMapping(value = "/addScatterGraph",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addScatterGraph(@RequestBody Graph<ScatterChart> scatterGraph){
        scatterGraph.getChart().setXType("value");
        scatterGraph.getChart().setYType("value");
        System.out.println(scatterGraph);
//        System.out.println(userID);
        Map<String,Object> map=new HashMap<>();
        int ID=-1;
        if(scatterGraph.getGraphID()==0) {
            ID = dashBoardService.addScatterGraph(scatterGraph.getUserID(), scatterGraph);
        }else{
            dashBoardService.deleteGraph(scatterGraph.getGraphID());
            ID = dashBoardService.addScatterGraph(scatterGraph.getUserID(), scatterGraph);
        }
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

//    存储/编辑table
    @RequestMapping(value = "/addTable",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addTable(@RequestBody Table table){
        System.out.println(table);
//        System.out.println(userID);
        Map<String,Object> map=new HashMap<>();
        int ID=-1;
        if(table.getTableID()==0) {
            ID = dashBoardService.addTable(table);
        }else{
            dashBoardService.deleteGraph(table.getTableID());
            ID = dashBoardService.addTable(table);
        }
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

//    存储/编辑mqtt
    @RequestMapping(value = "/addMQTT",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addMQTT(@RequestBody mqttGraph mqttGraph){
        System.out.println(mqttGraph);
        Map<String,Object> map=new HashMap<>();
        int ID=-1;
        if(mqttGraph.getMqttID()==0) {
            ID = dashBoardService.addMQTTGraph(mqttGraph);
        }else{
            dashBoardService.deleteGraph(mqttGraph.getMqttID());
            ID = dashBoardService.addMQTTGraph(mqttGraph);
        }
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
