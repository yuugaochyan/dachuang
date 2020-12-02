package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.mapper.EquipmentRepairMapper;
import com.industrialplatform.beta.pojo.Equipment;
import com.industrialplatform.beta.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    //折线图
    @GetMapping("/getChart/line")
    public Map<String,Object> lineQueryEquipment(){
        Map<String,Object> map=new HashMap<>();
        List<Map<String,Object>> Data=new ArrayList<>();
        List<String> nameList=new ArrayList<>();
        List<Equipment> equipmentList=equipmentService.queryEquipmentList();
        Random r=new Random(1);
        for(Equipment equipment:equipmentList){
            nameList.add(equipment.getEqpname());
            Data.add(new HashMap<>(){{
                put("name",equipment.getEqpname());
                put("type","line");
                put("stack","测试");
                int[] randomint=new int[7];
                for(int i=0;i<7;i++){
                    randomint[i]=r.nextInt(100);
                }
                put("data",randomint);
            }});
        }
        map.put("linelist",Data);
        map.put("legend",nameList);
        return map;
    }

    //排行轮播
    @GetMapping("/getChart/news2")
    public Map<String,Object> orderEquipmentBreakTimes(){
        Map<String,Object> map=new HashMap<>();
        List<Map<String,Object>> Data=new ArrayList<>();
        List<Equipment> equipmentList=equipmentService.queryEquipmentList();
        for(Equipment equipment: equipmentList) {
            Data.add(new HashMap<>(){{
                put("name",equipment.getEqpname());
                put("value", equipment.getBreaktimes());
            }});
        }

        map.put("unit","次");
        map.put("data",Data);
        return map;
    }

    //消息轮播
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
