package com.industrialplatform.beta;

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

    @Test
    void contextLoads() {
//        调取数据测试
        dataBaseService.getTableData("equipmentrepair");


//        添加柱状图测试
//        Graph<BarChart> graph=new Graph<>();
//        graph.setGraphName("test");
//        graph.setDataSource("equipmentrepair");
//        graph.setGraphType("bar");
//        graph.setChart(new BarChart());
//        graph.getChart().setXType("category");
//        graph.getChart().setYType("value");
//        graph.getChart().setXArraySource("eqpname");
//        BarDetail barDetail=new BarDetail();
//        barDetail.setDataCol("price");
//        barDetail.setName("价格");
//        graph.getChart().setSeries(new ArrayList<>());
//        graph.getChart().getSeries().add(barDetail);
//        System.out.println(graph);
//        dashBoardService.addBarGraph(32,graph);

//        Graph<ScatterChart> graph=new Graph<>();
//        graph.setGraphName("test");
//        graph.setDataSource("equipmentrepair");
//        graph.setGraphType("scatter");
//        graph.setChart(new ScatterChart());
//        graph.getChart().setXArraySource("price");
//        graph.getChart().setYArraySource("breaktimes");
//        graph.getChart().setXType("value");
//        graph.getChart().setYType("value");
//        System.out.println(graph);
//        dashBoardService.addScatterGraph(32,graph);


    }

}
