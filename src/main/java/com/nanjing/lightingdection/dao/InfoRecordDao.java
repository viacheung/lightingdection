package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.InfoRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InfoRecordDao {
  public List<InfoRecord> findAll(
      @Param("chanpinId") Integer chanpinId,
      @Param("datameaningId") Integer datameaningId,
      @Param("startDate") String startDate,
      @Param("endDate") String endDate);

  public List<InfoRecord> getAll(
      @Param("chanpinId") Integer chanpinId, @Param("datameaningId") Integer datameaningId);

  public Integer deleteInfoRecordById(Integer id);

  public Integer insertInfoRecord(InfoRecord infoRecord);

  public Integer updateInfoRecord(InfoRecord infoRecord);

  public InfoRecord findById(Integer id);
}
