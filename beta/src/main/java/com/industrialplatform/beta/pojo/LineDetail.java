package com.industrialplatform.beta.pojo;

import lombok.Data;

@Data
public class LineDetail {
    private int legendID;
    private String name;
    private String type;
    private String dataCol;
    private String color;
    private int[] data;
}
