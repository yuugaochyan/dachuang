package com.industrialplatform.beta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class dashBoardItem {
    private int itemID;
    private int x;
    private int y;
    private int w;
    private int h;
    private String title;
    private Graph ObjectData;
}
