package com.industrialplatform.beta.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BarChart {
    @JsonProperty("xType")
    private String xType;
    @JsonProperty("yType")
    private String yType;
    @JsonProperty("xArraySource")
    private String xArraySource;
    private List<String> xArray;
    private List<BarDetail> series;

}
