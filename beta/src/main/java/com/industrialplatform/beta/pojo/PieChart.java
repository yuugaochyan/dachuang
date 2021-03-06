package com.industrialplatform.beta.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class PieChart {
    private String pieSource;
    private String valueSource;
    private List<PieDetail> series;

}
