package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.ActualResistance;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ActualResistanceDao {
  public List<ActualResistance> findAll(
      @Param("chanpinId") Integer chanpinId,
      @Param("startDate") String startDate,
      @Param("endDate") String endDate);

  public List<ActualResistance> getAll(Integer chanpinId);

  public Integer deleteActualResistanceById(Integer id);

  public Integer insertActualResistance(ActualResistance actualResistance);

  public Integer updateActualResistance(ActualResistance actualResistance);

  public ActualResistance findById(Integer id);

  public List<ActualResistance> getAllDatas();
}
