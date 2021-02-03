package com.industrialplatform.beta.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Table {
    private int userID;
    private int tableID;
    private String tableName;
    private List<String> header;
    private Object data;
    private boolean index;
    private String align;
}
