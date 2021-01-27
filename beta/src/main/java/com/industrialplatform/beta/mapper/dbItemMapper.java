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
    String getChartTypeById(int id);
    int getGraphIdByItemId(int id);
    int getCurrentGrpahID();
    int getCurrentBarLengendID(int graphID);
    int getCurrentLineLengendID(int graphID);

    dashBoardItem getItemInfoByItemID(int id);
    Graph getGraphByItemID(int id);

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

    int addGraph(Graph graph,@Param("userID") int userID);

    int addBarChart(BarChart barChart,@Param("graphID")int graphID);
    int addBarDetail(BarDetail barDetail,@Param("graphID")int graphID);

    int addLineChart(LineChart lineChart,@Param("graphID")int graphID);
    int addLineDetail(LineDetail lineDetail,@Param("graphID")int graphID);

    int addPieChart(PieChart pieChart,@Param("graphID")int graphID);

    int addScatterChart(ScatterChart scatterChart,@Param("graphID")int graphID);


}
