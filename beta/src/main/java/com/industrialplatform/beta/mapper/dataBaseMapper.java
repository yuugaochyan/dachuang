package com.industrialplatform.beta.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface dataBaseMapper {
    List<String> getTableColNameByTableName(String tableName);
    List<String> getColData(String tableName,String colName);
    List<Map<String,Object>> getRowData(String tableName);
}
