package com.industrialplatform.beta.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ScatterChart {
    @JsonProperty("xArraySource")
    private String xArraySource;
    @JsonProperty("yArraySource")
    private String yArraySource;
    @JsonProperty("xType")
    private String xType;
    @JsonProperty("yType")
    private String yType;
    private List<ScatterDetail> series;

}
