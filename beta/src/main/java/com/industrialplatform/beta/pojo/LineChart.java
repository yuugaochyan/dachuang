package com.industrialplatform.beta.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LineChart {
    @JsonProperty("xType")
    private String xType;
    @JsonProperty("yType")
    private String yType;
    @JsonProperty("xArraySource")
    private String xArraySource;
    private List<String> xArray;
    private List<LineDetail> series;
}
