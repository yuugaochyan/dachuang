package com.industrialplatform.beta.mapper;

import com.industrialplatform.beta.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface dbItemMapper {
    int[] getdbItemIDList(int dbID);

    String getChartTypeById(int id);
    String getGraphTypeByGraphID(int graphID);
    int getGraphIdByItemId(int id);
    int getCurrentItemID();
    int getCurrentItemNum();
    int getCurrentGrpahID();
    int getCurrentGraphNum();
    int getCurrentBarLegendID(int graphID);
    int getCurrentBarLegendNum(int graphID);
    int getCurrentLineLegendID(int graphID);
    int getCurrentLineLegendNum(int graphID);

    List<dashBoardItem> getItemLocationListBydbID(int dbID);
    dashBoardItem getItemInfoByItemID(int id);
    Graph getGraphByItemID(int id);
    mqttGraph getMQTTGraphDataByItemID(int itemID);
    Table getTableInfoByItemID(int itemID);

    BarChart getBarChartByGraphID(int graphid);
    LineChart getLineChartByGraphID(int graphid);
    ScatterChart getScatterChartByGraphID(int graphid);

    List<String> getBarChartXArrayData(int graphID);
    int[] getBarData(int graphID,int legendID);

    List<String> getLineChartXArrayData(int graphID);
    int[] getLineData(int graphID,int legendID);

    List<String> getPieColumn(int graphID);
    int[] getPieValue(int graphID);

    float[] getScatterXData(int graphID);
    float[] getScatterYData(int graphID);

    int registGraph(@Param("ID")int ID,@Param("userID")int userID,@Param("type")String type);
    int addNewItemToDB(int itemID,int dbID,String type);
    int bindGraphToItem(int graphID,int itemID);


    int addGraph(Graph graph);
    int addMQTTGraph(mqttGraph mqttGraph);
    int addTable(Table table);

    int addBarChart(BarChart barChart,@Param("graphID")int graphID);
    int addBarDetail(BarDetail barDetail,@Param("graphID")int graphID);

    int addLineChart(LineChart lineChart,@Param("graphID")int graphID);
    int addLineDetail(LineDetail lineDetail,@Param("graphID")int graphID);

    int addPieChart(PieChart pieChart,@Param("graphID")int graphID);

    int addScatterChart(ScatterChart scatterChart,@Param("graphID")int graphID);


}
