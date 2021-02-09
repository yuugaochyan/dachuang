package com.industrialplatform.beta;

import com.industrialplatform.beta.mapper.dataBaseMapper;
import com.industrialplatform.beta.mapper.dbItemMapper;
import com.industrialplatform.beta.pojo.*;
import com.industrialplatform.beta.service.DashBoardService;
import com.industrialplatform.beta.service.DataBaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class BetaApplicationTests {

    @Autowired
    DashBoardService dashBoardService;

    @Autowired
    DataBaseService dataBaseService;

    @Autowired
    dataBaseMapper  dataBaseMapper;

    @Autowired
    dbItemMapper  dbItemMapper;

    @Test
    void contextLoads() {
//        System.out.println(dataBaseMapper.getRowData("equipmentrepair"));
//        调取数据测试
//        dataBaseService.getTableData("equipmentrepair");
//        dashBoardService.getdbItemByItemID(1007);
        dashBoardService.getdbItemBydbID(1);






    }

}
