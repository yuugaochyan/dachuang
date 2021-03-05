package com.industrialplatform.beta.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class mqttGraph {
    private int userID;
    @JsonProperty("graphID")
    private int mqttID;
    private String graphName;
    private String tag;
    private String tagName;
    private String type;
    private String xType;
    private String yType;
    private int max;
    private int min;
    private int lengs;
    private List<String> legend;

}

