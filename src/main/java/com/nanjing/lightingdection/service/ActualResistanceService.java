package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.ActualResistance;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ActualResistanceService {
  public Integer insertActualResistance(ActualResistance actualResistance);

  public List<ActualResistance> getAll();

  public List<ActualResistance> findAll(
      Integer locationId, Integer leibieId, Integer chanpinId, String startDate, String endDate);
}
