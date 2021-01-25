package com.industrialplatform.beta.pojo;

import lombok.Data;

import java.util.List;

@Data
public class LineChart {
    private String xType;
    private String yType;
    private String xArraySource;
    private List<String> xArray;
    private List<LineDetail> series;
}
