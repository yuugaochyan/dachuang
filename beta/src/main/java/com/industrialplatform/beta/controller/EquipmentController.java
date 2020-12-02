package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.mapper.EquipmentRepairMapper;
import com.industrialplatform.beta.pojo.Equipment;
import com.industrialplatform.beta.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/getChart/news2")
    public Map<String,Object> orderEquipmentBreakTimes(){
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> datamap=new HashMap<>();
        List<Map<String,Object>> Data=new ArrayList<>();
        List<Equipment> equipmentList=equipmentService.queryEquipmentList();
        for(Equipment equipment: equipmentList) {
           // datamap.put("name", equipment.getEqpname());
           // datamap.put("value", equipment.getBreaktimes());
            Data.add(new HashMap<>(){{
                put("name",equipment.getEqpname());
                put("value", equipment.getBreaktimes());
            }});
           // System.out.println(datamap);
            System.out.println(Data);
        }

        map.put("unit","次");
        map.put("data",Data);
        return map;
    }


    @GetMapping("/getChart/news1")
    public Map<String,Object> queryEquipmentList(){
        Map<String,Object> map=new HashMap<>();
        List<String[]> Data = new ArrayList<>();
        List<Equipment> equipmentList=equipmentService.queryEquipmentList();
        for(Equipment equipment:equipmentList ) {
            Data.add(new String[]{equipment.getEqpname(),String.valueOf(equipment.getBreaktimes())});
        }
        map.put("data",Data);
        map.put("header",new String[]{"设备名称","维修次数"});
        return map;
    }
}
