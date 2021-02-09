package com.industrialplatform.beta.pojo;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class PieDetail {

    private String type;
    private List<HashMap<String,Object>> data;

}
