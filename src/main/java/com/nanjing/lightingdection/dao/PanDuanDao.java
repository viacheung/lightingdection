package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.PanDuan;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PanDuanDao {
  public PanDuan findByDataMeaningId(
      @Param("data") int data, @Param("datameaningId") Integer datameaningId);

  public List<PanDuan> findAll(@Param("start") Integer start, @Param("limit") Integer limit);

  public Integer insertPanDuanData(PanDuan panDuan);

  public Integer deletePanDuanData(
      @Param("data") int data, @Param("datameaningId") Integer datameaningId);

  public Integer updatePanDuandata(PanDuan panDuan);
}
