package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.PanDuan;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PanDuanService {
  public PanDuan findByDataMeaningId(int data, Integer datameaningId);

  public List<PanDuan> findAll(Integer page, Integer size);

  public Integer insertPanDuanData(PanDuan panDuan);

  public Integer deletePanDuanData(int data, Integer datameaningId);

  public Integer updatePanDuandata(PanDuan panDuan);
}
