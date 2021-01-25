package com.industrialplatform.beta.service;

import com.industrialplatform.beta.mapper.dbItemMapper;
import com.industrialplatform.beta.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
        System.out.println(type);
        switch(chartMap.get(type))
        {
            case 1:
                Graph<BarChart> barGraph=dbItemMapper.getGraphByItemID(ItemID);
                int graphid=barGraph.getGraphID();
                BarChart barChart=dbItemMapper.getBarChartByGraphID(graphid);
                barGraph.setChart(barChart);
                barGraph.getChart().setXArray(dbItemMapper.getBarChartXArrayData(graphid));
                for(BarDetail serie:barGraph.getChart().getSeries()){
                    serie.setData(dbItemMapper.getBarData(graphid,serie.getLegendID()));
                }
                dashBoardItem.setObjectData(barGraph);
                break;
            case 2:
                Graph<LineChart> lineGraph=dbItemMapper.getGraphByItemID(ItemID);
                graphid=lineGraph.getGraphID();
                LineChart lineChart=dbItemMapper.getLineChartByGraphID(graphid);
                lineGraph.setChart(lineChart);
                lineGraph.getChart().setXArray(dbItemMapper.getLineChartXArrayData(graphid));
                for(LineDetail serie:lineGraph.getChart().getSeries()){
                    serie.setData(dbItemMapper.getLineData(graphid,serie.getLegendID()));
                }
                dashBoardItem.setObjectData(lineGraph);
                break;
            default:
        }
        System.out.println(dashBoardItem);
        return dashBoardItem;
    }

}
