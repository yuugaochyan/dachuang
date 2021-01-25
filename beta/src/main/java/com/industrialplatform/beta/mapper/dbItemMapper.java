package com.industrialplatform.beta.mapper;

import com.industrialplatform.beta.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface dbItemMapper {
    String getChartTypeById(int id);
    dashBoardItem getItemInfoByItemID(int id);
    Graph getGraphByItemID(int id);

    BarChart getBarChartByGraphID(int graphid);
    LineChart getLineChartByGraphID(int graphid);

    List<String> getBarChartXArrayData(int graphID);
    int[] getBarData(int graphID,int legendID);

    List<String> getLineChartXArrayData(int graphID);
    int[] getLineData(int graphID,int legendID);

}
