package com.industrialplatform.beta.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface dataBaseMapper {
    List<String> getTableColNameByTableName(String tableName);
    List<String> getColData(String tableName,String colName);
}
