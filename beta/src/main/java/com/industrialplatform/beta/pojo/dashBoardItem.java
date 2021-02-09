package com.industrialplatform.beta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class dashBoardItem {
    private int i;
    private int x;
    private int y;
    private int w;
    private int h;
    private String title;
    private String type;
    private Object ObjectData;
}
