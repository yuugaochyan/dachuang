package com.industrialplatform.beta.service;

import com.industrialplatform.beta.mapper.dbItemMapper;
import com.industrialplatform.beta.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
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

//    获取item信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public dashBoardItem getItemByID(int ItemID){
        String type=dbItemMapper.getChartTypeById(ItemID);
        dashBoardItem dashBoardItem=dbItemMapper.getItemInfoByItemID(ItemID);
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
                dashBoardItem.setObjectData(barGraph);
                break;
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
                dashBoardItem.setObjectData(lineGraph);
                break;
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
                dashBoardItem.setObjectData(pieGraph);
                break;
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
                dashBoardItem.setObjectData(scatterGraph);
                break;
            default:
        }
        System.out.println(dashBoardItem);
        return dashBoardItem;
    }

//    添加柱状图
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean addBarGraph(int userID,Graph<BarChart> barGraph){
        int graphID=dbItemMapper.getCurrentGrpahID()+1;
        barGraph.setGraphID(graphID);
        dbItemMapper.addGraph(barGraph,userID);
        BarChart barChart=barGraph.getChart();
        dbItemMapper.addBarChart(barChart,graphID);
        int legendID=dbItemMapper.getCurrentBarLengendID(graphID)+1;
        for(BarDetail serie:barChart.getSeries()){
            serie.setLegendID(legendID);
            dbItemMapper.addBarDetail(serie,graphID);
            legendID++;
        }
        return true;
    }

//    添加折线图
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean addLineGraph(int userID,Graph<LineChart> lineGraph){
        int graphID=dbItemMapper.getCurrentGrpahID()+1;
        lineGraph.setGraphID(graphID);
        dbItemMapper.addGraph(lineGraph,userID);
        LineChart lineChart=lineGraph.getChart();
        dbItemMapper.addLineChart(lineChart,graphID);
        int legendID=dbItemMapper.getCurrentLineLengendID(graphID)+1;
        for(LineDetail serie:lineChart.getSeries()){
            serie.setLegendID(legendID);
            dbItemMapper.addLineDetail(serie,graphID);
            legendID++;
        }
        return true;
    }

//    添加饼图
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean addPieGraph(int userID,Graph<PieChart> pieGraph){
        int graphID=dbItemMapper.getCurrentGrpahID()+1;
        pieGraph.setGraphID(graphID);
        dbItemMapper.addGraph(pieGraph,userID);
        PieChart pieChart=pieGraph.getChart();
        dbItemMapper.addPieChart(pieChart,graphID);
        return true;
    }
//    添加散点图
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean addScatterGraph(int userID,Graph<ScatterChart> scatterGraph){
        int graphID=dbItemMapper.getCurrentGrpahID()+1;
        scatterGraph.setGraphID(graphID);
        dbItemMapper.addGraph(scatterGraph,userID);
        ScatterChart scatterChart=scatterGraph.getChart();
        dbItemMapper.addScatterChart(scatterChart,graphID);
        return true;
    }

}
