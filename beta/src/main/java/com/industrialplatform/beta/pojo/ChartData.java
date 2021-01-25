package com.industrialplatform.beta.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ChartData {
    private int[] data;
    private List<String> xArray;
}
