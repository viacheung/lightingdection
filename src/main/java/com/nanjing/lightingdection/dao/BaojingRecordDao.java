package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.BaojingRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BaojingRecordDao {
  public Integer insertBaojingRecord(BaojingRecord baojingRecord);

  public Integer updateBaojingRecord(BaojingRecord baojingRecord);

  public Integer deleteBaojingRecordById(Integer id);

  public BaojingRecord findById(Integer id);

  public List<BaojingRecord> findAll(
      @Param("chanpinId") Integer chanpinId,
      @Param("datameaningId") Integer datameaningId,
      @Param("startDate") String startDate,
      @Param("endDate") String endDate);

  public List<BaojingRecord> getAll(
      @Param("chanpinId") Integer chanpinId, @Param("datameaningId") Integer datameaningId);
}
