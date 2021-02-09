package com.industrialplatform.beta.pojo;

import lombok.Data;

import java.util.List;

@Data
public class mqttGraph {
    private int userID;
    private int mqttID;
    private String graphName;
    private String tag;
    private String tagName;
    private String type;
    private int max;
    private int min;
    private int lengs;
    private List<String> legend;

}

