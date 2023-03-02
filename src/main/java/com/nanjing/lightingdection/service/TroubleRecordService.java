package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.TroubleRecord;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TroubleRecordService {
  public List<TroubleRecord> findAll(
      Integer locationId,
      Integer leibieId,
      Integer chanpinId,
      Integer datameaningId,
      String startDate,
      String endDate);

  public Integer deleteTroubleRecordById(Integer id);

  public Integer insertTroubleRecord(TroubleRecord troubleRecord);

  public Integer updateTroubleRecord(TroubleRecord troubleRecord);

  public TroubleRecord findById(Integer id);
}
