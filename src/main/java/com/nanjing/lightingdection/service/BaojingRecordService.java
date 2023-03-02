package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.BaojingRecord;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BaojingRecordService {
  public List<BaojingRecord> findAll(
      Integer locationId,
      Integer leibieId,
      Integer chanpinId,
      Integer datameaningId,
      String startDate,
      String endDate);

  public Integer insertBaojingRecord(BaojingRecord baojingRecord);

  public Integer updateBaojingRecord(BaojingRecord baojingRecord);

  public Integer deleteBaojingRecordById(Integer id);

  public BaojingRecord findById(Integer id);
}
