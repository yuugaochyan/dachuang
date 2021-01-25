package com.industrialplatform.beta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph<T> {
    private int graphID;
    private String graphName;
    private String graphType;
    private String dataSource;
    private T Chart;
}
