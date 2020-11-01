package com.industrialplatform.beta.mapper;

import com.industrialplatform.beta.pojo.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EquipmentRepairMapper {
    List<Equipment> queryEquipmentList();

}
