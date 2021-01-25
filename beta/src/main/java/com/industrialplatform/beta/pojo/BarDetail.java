package com.industrialplatform.beta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BarDetail {
    private int legendID;
    private String name;
    private String dataCol;
    private int[] data;
}
