package com.industrialplatform.beta.mapper;

import com.industrialplatform.beta.pojo.mqttTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface dataBaseMapper {
//    mqtt数据调取
    List<mqttTag>  getMqttTagList();
//    sql关系数据调取
    List<String> getTableColNameByTableName(String tableName);
    List<String> getColData(String tableName,String colName);
    List<Map<String,Object>> getRowData(String tableName);
}
