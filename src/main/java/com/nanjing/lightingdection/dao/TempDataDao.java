package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.TempData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TempDataDao {
  public Integer addTempData(TempData tempData);

  public Integer updateTempData(TempData tempData);

  public TempData findTempDataByDatameaningAndChanpinId(
      @Param("chanpinId") Integer chanpinId, @Param("datameaningId") Integer datameaningId);

  public Integer selectCountByDatameaningAndChanpinId(
      @Param("chanpinId") Integer chanpinId, @Param("datameaningId") Integer datameaningId);
}
