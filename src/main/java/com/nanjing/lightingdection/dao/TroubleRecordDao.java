package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.TroubleRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TroubleRecordDao {
  public List<TroubleRecord> findAll(
      @Param("chanpinId") Integer chanpinId,
      @Param("datameaningId") Integer datameaningId,
      @Param("startDate") String startDate,
      @Param("endDate") String endDate);

  public List<TroubleRecord> getAll(
      @Param("chanpinId") Integer chanpinId, @Param("datameaningId") Integer datameaningId);

  public Integer deleteTroubleRecordById(Integer id);

  public Integer insertTroubleRecord(TroubleRecord troubleRecord);

  public Integer updateTroubleRecord(TroubleRecord troubleRecord);

  public TroubleRecord findById(Integer id);
}
