package com.industrialplatform.beta.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.EXISTING_PROPERTY,
//        property = "graphType",
//        visible = true)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = BarChart.class, name = "bar"),
//        @JsonSubTypes.Type(value = LineChart.class, name = "line"),
//        @JsonSubTypes.Type(value = PieChart.class, name = "pie"),
//        @JsonSubTypes.Type(value = ScatterChart.class, name = "scatter"),
//})
public class Graph<T> {
    private int userID;
    private int graphID;
    private String graphName;
    private String graphType;
    private String dataSource;
    private List<String> legend;
    @JsonProperty("Chart")
    private T Chart;

    private Object series;
    private Object xarray;
    private Object xType;
    private Object yType;
}
