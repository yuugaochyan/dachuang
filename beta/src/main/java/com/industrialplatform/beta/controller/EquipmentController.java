package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.mapper.EquipmentRepairMapper;
import com.industrialplatform.beta.pojo.Equipment;
import com.industrialplatform.beta.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/queryEquipmentList")
    public List<Equipment> queryEquipmentList(){
        return equipmentService.queryEquipmentList();
    }
}
