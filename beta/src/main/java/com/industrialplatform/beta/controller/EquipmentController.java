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

    @GetMapping("/queryEquipmentList")
    public Map<String,Object> queryEquipmentList(){
        Map<String,Object> map=new HashMap<>();
        List<String[]> Data = new ArrayList<>();
        List<Equipment> equipmentList=equipmentService.queryEquipmentList();
        for(Equipment equipment:equipmentList ) {
            System.out.println(equipment);
            Data.add(new String[]{equipment.getEqpname(),String.valueOf(equipment.getBreaktimes())});
        }
        map.put("data",Data);
        map.put("header",new String[]{"设备名称","维修次数"});
        return map;
    }
}
