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
    private int DB_MAX_SIZE=10000;

    @Autowired
    dbItemMapper dbItemMapper;
    @Autowired
    dataBaseMapper dataBaseMapper;

//    获取根据dashboardID调取所有信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<dashBoardItem> getdbItemBydbID(int dbID){
        List<dashBoardItem> items=new ArrayList<>();
        int[] itemIDs=dbItemMapper.getdbItemIDList(dbID);
        for(int itemID:itemIDs){
//            System.out.println(itemID);
            items.add(getdbItemByItemID(itemID));
        }
//        System.out.println(items);
        return items;
    }


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
                break;
            case 3:
                dashBoardItem.setObjectData(getTable(ItemID));
                break;
            default:
        }
        System.out.println(dashBoardItem);
        return dashBoardItem;
    }


//    添加图表至仪表盘中
    public boolean addGraphsToDashBoard(int[] graphIDs,int dbID){
        List<dashBoardItem> itemsLoc=dbItemMapper.getItemLocationListBydbID(dbID);
        int[][] dbLoc=new int[DB_MAX_SIZE][DB_MAX_SIZE];
        int x,y,w,h,flag;
        for(dashBoardItem Loc:itemsLoc){
            x=Loc.getX();
            y=Loc.getY();
            w=Loc.getW();
            h=Loc.getH();
            for(int j=0;j<h;j++) {
                for (int i = 0; i < w; i++) {
                    dbLoc[x+j][y+i]=1;
                }
            }
        }
        for(int graphID:graphIDs){
            flag=0;
            x=0;
            y=0;
            while(flag==0){


            }

        }





        return true;
    }


//    将一个图表放到仪表盘中
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean addOneGraphToDashBoard(int graphID,int dbID){
        String type=dbItemMapper.getGraphTypeByGraphID(graphID);
        int itemID=0;
        if (dbItemMapper.getCurrentItemNum()==0)itemID=1000;
        else itemID=dbItemMapper.getCurrentItemID();
        dbItemMapper.addNewItemToDB(itemID,dbID,type);
        dbItemMapper.bindGraphToItem(graphID,itemID);
        return true;
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
                    serie.setType("bar");
                    barGraph.getLegend().add(serie.getName());
                }
                barGraph.setSeries(barChart.getSeries());
                barGraph.setXType(barChart.getXType());
                barGraph.setYType(barChart.getYType());
                barGraph.setXarray(barChart.getXArray());
                barGraph.setChart(null);
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
                    serie.setType("line");
                    lineGraph.getLegend().add(serie.getName());
                }
                lineGraph.setXType(lineChart.getXType());
                lineGraph.setYType(lineChart.getYType());
                lineGraph.setXarray(lineChart.getXArray());
                lineGraph.setSeries(lineChart.getSeries());
                lineGraph.setChart(null);
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
                pieDetail.setType("pie");
                pieGraph.getChart().getSeries().add(pieDetail);
                pieGraph.setSeries(pieGraph.getChart().getSeries());
                pieGraph.setChart(null);
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
                scatterDetail.setType("scatter");
                scatterChart.getSeries().add(scatterDetail);
                scatterGraph.setSeries(scatterChart.getSeries());
                scatterGraph.setXType(scatterChart.getXType());
                scatterGraph.setYType(scatterChart.getYType());
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
        if(mqttGraph.getType()!=null)
        {
            mqttGraph.setLegend(new ArrayList<>());
            mqttGraph.getLegend().add(mqttGraph.getTagName());
        }
//        System.out.println(mqttGraph);
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
//        System.out.println(table);
        return table;
    }

//    添加柱状图
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addBarGraph(int userID,Graph<BarChart> barGraph){
        int graphID=0;
        if(dbItemMapper.getCurrentGraphNum()==0)graphID=10001;
        else graphID= dbItemMapper.getCurrentGrpahID()+1;
        dbItemMapper.registGraph(graphID,userID,"chart");
        barGraph.setGraphID(graphID);
        dbItemMapper.addGraph(barGraph);
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
        dbItemMapper.registGraph(graphID,userID,"chart");
        lineGraph.setGraphID(graphID);
        dbItemMapper.addGraph(lineGraph);
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
        dbItemMapper.registGraph(graphID,userID,"chart");
        pieGraph.setGraphID(graphID);
        dbItemMapper.addGraph(pieGraph);
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
        dbItemMapper.registGraph(graphID,userID,"chart");
        scatterGraph.setGraphID(graphID);
        dbItemMapper.addGraph(scatterGraph);
        ScatterChart scatterChart=scatterGraph.getChart();
        dbItemMapper.addScatterChart(scatterChart,graphID);
        if (graphID!=0) return graphID;
        else return -1;
    }

//    添加MQTTgraph
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addMQTTGraph(mqttGraph mqttGraph){
        int mqttID=0;
        if(dbItemMapper.getCurrentGraphNum()==0) mqttID=10001;
        else mqttID=dbItemMapper.getCurrentGrpahID()+1;
        if(mqttGraph.getType()!=null)
            dbItemMapper.registGraph(mqttID,mqttGraph.getUserID(),"mqttline");
        else
            dbItemMapper.registGraph(mqttID,mqttGraph.getUserID(),"mqttnum");
        mqttGraph.setMqttID(mqttID);
        dbItemMapper.addMQTTGraph(mqttGraph);
        if (mqttID!=0) return mqttID;
        else return -1;
    }

//    添加table
    @Transactional(propagation = Propagation.SUPPORTS)
    public int addTable(Table table){
        int tableID=0;
        if(dbItemMapper.getCurrentGraphNum()==0) tableID=10001;
        else tableID=dbItemMapper.getCurrentGrpahID()+1;
        dbItemMapper.registGraph(tableID,table.getUserID(),"table");
        table.setTableID(tableID);
        dbItemMapper.addTable(table);
        if(tableID!=0) return tableID;
        else return -1;
    }

}
