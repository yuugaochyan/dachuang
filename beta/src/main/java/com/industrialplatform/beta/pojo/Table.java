package com.industrialplatform.beta.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Table {
    private int userID;
    @JsonProperty("graphID")
    private int tableID;
    private String tableName;
    private String graphName;
    private List<String> header;
    private Object data;
    private boolean index;
    private String align;
}
