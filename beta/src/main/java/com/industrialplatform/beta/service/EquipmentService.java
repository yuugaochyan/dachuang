package com.industrialplatform.beta.service;

import com.industrialplatform.beta.mapper.EquipmentRepairMapper;
import com.industrialplatform.beta.pojo.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipmentService {
    @Autowired
    private EquipmentRepairMapper equipmentRepairMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Equipment> queryEquipmentList(){
        return equipmentRepairMapper.queryEquipmentList();
    }

}
