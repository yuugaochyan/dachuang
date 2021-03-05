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
    private Object xArraySource;
    private Object yArraySource;
    private Object xType;
    private Object yType;
    private Object pieSource;
    private Object valueSource;
}
