package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.InfoRecord;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface InfoRecordService {
  public List<InfoRecord> findAll(
      Integer locationId,
      Integer leibieId,
      Integer chanpinId,
      Integer datameaningId,
      String startDate,
      String endDate);

  public Integer deleteInfoRecordById(Integer id);

  public Integer insertInfoRecord(InfoRecord infoRecord);

  public Integer updateInfoRecord(InfoRecord infoRecord);

  public InfoRecord findById(Integer id);
}
