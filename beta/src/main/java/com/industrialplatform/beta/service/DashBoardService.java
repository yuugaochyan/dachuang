package com.industrialplatform.beta.service;

import com.industrialplatform.beta.mapper.dataBaseMapper;
import com.industrialplatform.beta.mapper.dbItemMapper;
import com.industrialplatform.beta.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DashBoardService {

    @Autowired
    dbItemMapper dbItemMapper;
    @Autowired
    dataBaseMapper dataBaseMapper;

//    获取item信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public dashBoardItem getdbItemByItemID(int ItemID){
        dashBoardItem dashBoardItem=dbItemMapper.getItemInfoByItemID(ItemID);
        Map<String,Integer> ObjectMap=new HashMap<>();
        ObjectMap.put("chart",1);
        ObjectMap.put("mqttline",2);
        ObjectMap.put("mqttnum",2);
        ObjectMap.put("table",3);
        System.out.println(dashBoardItem.getType());
        switch (ObjectMap.get(dashBoardItem.getType()))
        {
            case 1:
                dashBoardItem.setObjectData(getGraphByID(ItemID));
                break;
            case 2:
                dashBoardItem.setObjectData(getMQTTGraph(ItemID));
            case 3:
                dashBoardItem.setObjectData(getTable(ItemID));
                break;
            default:
        }
        System.out.println(dashBoardItem);
        return dashBoardItem;
    }


//    获取Graph信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public Graph getGraphByID(int ItemID){
        String type=dbItemMapper.getChartTypeById(ItemID);
//        dashBoardItem dashBoardItem=dbItemMapper.getItemInfoByItemID(ItemID);
        Map<String,Integer> chartMap=new HashMap<>();
        chartMap.put("bar",1);
        chartMap.put("line",2);
        chartMap.put("pie",3);
        chartMap.put("scatter",4);
        System.out.println(type);
        switch(chartMap.get(type))
        {
            case 1:
                Graph<BarChart> barGraph=dbItemMapper.getGraphByItemID(ItemID);
                int graphid=barGraph.getGraphID();
                BarChart barChart=dbItemMapper.getBarChartByGraphID(graphid);
                barGraph.setChart(barChart);
                barGraph.setLegend(new ArrayList<>());
                barGraph.getChart().setXArray(dbItemMapper.getBarChartXArrayData(graphid));
                for(BarDetail serie:barGraph.getChart().getSeries()){
                    serie.setData(dbItemMapper.getBarData(graphid,serie.getLegendID()));
                    barGraph.getLegend().add(serie.getName());
                }
//                dashBoardItem.setObjectData(barGraph);
                return barGraph;
            case 2:
                Graph<LineChart> lineGraph=dbItemMapper.getGraphByItemID(ItemID);
                graphid=lineGraph.getGraphID();
                LineChart lineChart=dbItemMapper.getLineChartByGraphID(graphid);
                lineGraph.setChart(lineChart);
                lineGraph.setLegend(new ArrayList<>());
                lineGraph.getChart().setXArray(dbItemMapper.getLineChartXArrayData(graphid));
                for(LineDetail serie:lineGraph.getChart().getSeries()){
                    serie.setData(dbItemMapper.getLineData(graphid,serie.getLegendID()));
                    lineGraph.getLegend().add(serie.getName());
                }
//                dashBoardItem.setObjectData(lineGraph);
                return lineGraph;
            case 3:
                Graph<PieChart> pieGraph=dbItemMapper.getGraphByItemID(ItemID);
                graphid=pieGraph.getGraphID();
                List<String> pieCol=dbItemMapper.getPieColumn(graphid);
                int[] pieValue=dbItemMapper.getPieValue(graphid);
                pieGraph.setChart(new PieChart());
                pieGraph.setLegend(new ArrayList<>());
                pieGraph.getChart().setSeries(new ArrayList<>());
                int i=0;
                PieDetail pieDetail=new PieDetail();
                pieDetail.setData(new ArrayList<>());
                for (String piecol:pieCol){
                    int finalI = i;
                    pieDetail.getData().add(new HashMap<>(){{
                        put("name",piecol);
                        put("value",pieValue[finalI]);
                    }});
                    i++;
                    pieGraph.getLegend().add(piecol);
                }
                pieGraph.getChart().getSeries().add(pieDetail);
//                dashBoardItem.setObjectData(pieGraph);
                return pieGraph;
            case 4:
                Graph<ScatterChart> scatterGraph=dbItemMapper.getGraphByItemID(ItemID);
                graphid=scatterGraph.getGraphID();
                float[] xData=dbItemMapper.getScatterXData(graphid);
                float[] yData=dbItemMapper.getScatterYData(graphid);
                ScatterChart scatterChart=dbItemMapper.getScatterChartByGraphID(graphid);
                scatterChart.setSeries(new ArrayList<>());
                ScatterDetail scatterDetail=new ScatterDetail();
                scatterDetail.setData(new float[xData.length][2]);
                i=0;
                for (float x:xData){
                    int finalI = i;
                    scatterDetail.getData()[finalI][0]=x;
                    scatterDetail.getData()[finalI][1]=yData[finalI];
                    i++;
                }
                scatterChart.getSeries().add(scatterDetail);
                scatterGraph.setChart(scatterChart);
                return scatterGraph;
//                dashBoardItem.setObjectData(scatterGraph);
            default:
        }
//        System.out.println(dashBoardItem);
        return null;
    }

//    获取mqtt图表信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public mqttGraph getMQTTGraph(int itemID){
        mqttGraph mqttGraph=dbItemMapper.getMQTTGraphDataByItemID(itemID);
        System.out.println(mqttGraph);
        return mqttGraph;
    }

//    获取tableItem信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public Table getTable(int itemID){
        Table table=dbItemMapper.getTableInfoByItemID(itemID);
        table.setHeader(dataBaseMapper.getTableColNameByTableName(table.getTableName()));
        List<Map<String,Object>> result=dataBaseMapper.getRowData(table.getTableName());
        List<String> cols=table.getHeader();
        List<List<Object>> data=new ArrayList<>();
        for (Map<String,Object> map:result){
            List<Object> row=new ArrayList<>();
            for(String col:cols){
                row.add(map.get(col));
            }
            data.add(row);
        }
        table.setData(data);
        table.setAlign("center");
        table.setIndex(true);
        return table;
    }

//    根据表名输出table形式数据
    public Table getTableByTableName(String tableName){
        Table table= new Table();
        table.setTableName(tableName);
        table.setHeader(dataBaseMapper.getTableColNameByTableName(tableName));
        List<Map<String,Object>> result=dataBaseMapper.getRowData(tableName);
        List<String> cols=table.getHeader();
        List<List<Object>> data=new ArrayList<>();
        for (Map<String,Object> map:result){
            List<Object> row=new ArrayList<>();
            for(String col:cols){
                row.add(map.get(col));
            }
            data.add(row);
        }
        table.setData(data);
        table.setAlign("center");
        table.setIndex(true);
        System.out.println(table);
        return table;
    }

//    添加柱状图
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addBarGraph(int userID,Graph<BarChart> barGraph){
        int graphID=0;
        if(dbItemMapper.getCurrentGraphNum()==0)graphID=10001;
        else graphID= dbItemMapper.getCurrentGrpahID()+1;
        barGraph.setGraphID(graphID);
        dbItemMapper.addGraph(barGraph,userID);
        BarChart barChart=barGraph.getChart();
        dbItemMapper.addBarChart(barChart,graphID);
        int legendID=0;
        if(dbItemMapper.getCurrentBarLegendNum(graphID)==0)legendID=1;
        else legendID=dbItemMapper.getCurrentBarLegendID(graphID)+1;
        for(BarDetail serie:barChart.getSeries()){
            serie.setLegendID(legendID);
            dbItemMapper.addBarDetail(serie,graphID);
            legendID++;
        }
        if (graphID!=0)return graphID;
        else return -1;
    }

//    添加折线图
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addLineGraph(int userID,Graph<LineChart> lineGraph){
        int graphID=0;
        if(dbItemMapper.getCurrentGraphNum()==0)graphID=10001;
        else graphID= dbItemMapper.getCurrentGrpahID()+1;
        lineGraph.setGraphID(graphID);
        dbItemMapper.addGraph(lineGraph,userID);
        LineChart lineChart=lineGraph.getChart();
        dbItemMapper.addLineChart(lineChart,graphID);
        int legendID=0;
        if(dbItemMapper.getCurrentLineLegendNum(graphID)==0)legendID=1;
        else legendID=dbItemMapper.getCurrentLineLegendID(graphID)+1;
        for(LineDetail serie:lineChart.getSeries()){
            serie.setLegendID(legendID);
            dbItemMapper.addLineDetail(serie,graphID);
            legendID++;
        }
        if (graphID!=0)return graphID;
        else return -1;
    }

//    添加饼图
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addPieGraph(int userID,Graph<PieChart> pieGraph){
        int graphID=0;
        if(dbItemMapper.getCurrentGraphNum()==0)graphID=10001;
        else graphID= dbItemMapper.getCurrentGrpahID()+1;
        pieGraph.setGraphID(graphID);
        dbItemMapper.addGraph(pieGraph,userID);
        PieChart pieChart=pieGraph.getChart();
        dbItemMapper.addPieChart(pieChart,graphID);
        if (graphID!=0)return graphID;
        else return -1;
    }

//    添加散点图
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addScatterGraph(int userID,Graph<ScatterChart> scatterGraph){
        int graphID=0;
        if(dbItemMapper.getCurrentGraphNum()==0) graphID=10001;
        else graphID=dbItemMapper.getCurrentGrpahID()+1;
        scatterGraph.setGraphID(graphID);
        dbItemMapper.addGraph(scatterGraph,userID);
        ScatterChart scatterChart=scatterGraph.getChart();
        dbItemMapper.addScatterChart(scatterChart,graphID);
        if (graphID!=0) return graphID;
        else return -1;
    }

//    添加MQTTgraph
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addMQTTGraph(mqttGraph mqttGraph){
        int mqttID=0;
        if (dbItemMapper.getCurrentTableNum()==0) mqttID = 10001;
        else mqttID=dbItemMapper.getCurrentMQTTID()+1;
        mqttGraph.setMqttID(mqttID);
        dbItemMapper.addMQTTGraph(mqttGraph);
        if (mqttID!=0) return mqttID;
        else return -1;
    }

//    添加table
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addTable(Table table){
        int tableID=0;
        if (dbItemMapper.getCurrentTableNum()==0) tableID = 10001;
        else tableID=dbItemMapper.getCurrentTableID()+1;
        table.setTableID(tableID);
        dbItemMapper.addTable(table);
        if(tableID!=0) return tableID;
        else return -1;
    }

}
