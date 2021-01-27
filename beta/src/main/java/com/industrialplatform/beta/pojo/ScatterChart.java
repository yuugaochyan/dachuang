package com.industrialplatform.beta.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ScatterChart {
    private String xArraySource;
    private String yArraySource;
    private String xType;
    private String yType;
    private List<ScatterDetail> series;

}
