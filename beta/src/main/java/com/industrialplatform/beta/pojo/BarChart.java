package com.industrialplatform.beta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BarChart {
    private String xType;
    private String yType;
    private String xArraySource;
    private List<String> xArray;
    private List<BarDetail> series;

}
