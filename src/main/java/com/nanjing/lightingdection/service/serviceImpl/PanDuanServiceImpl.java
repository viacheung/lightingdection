package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.PanDuanDao;
import com.nanjing.lightingdection.entity.PanDuan;
import com.nanjing.lightingdection.service.PanDuanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanDuanServiceImpl implements PanDuanService {
  @Autowired private PanDuanDao panDuanDao;

  @Override
  public PanDuan findByDataMeaningId(int data, Integer datameaningId) {
    return panDuanDao.findByDataMeaningId(data, datameaningId);
  }

  @Override
  public List<PanDuan> findAll(Integer page, Integer size) {
    if (page == null) {
      page = 1;
    }
    if (size == null) {
      size = 10;
    }
    Integer start = (page - 1) * size;
    return panDuanDao.findAll(start, size);
  }

  @Override
  public Integer insertPanDuanData(PanDuan panDuan) {
    return panDuanDao.insertPanDuanData(panDuan);
  }

  @Override
  public Integer deletePanDuanData(int data, Integer datameaningId) {
    return panDuanDao.deletePanDuanData(data, datameaningId);
  }

  @Override
  public Integer updatePanDuandata(PanDuan panDuan) {
    return panDuanDao.updatePanDuandata(panDuan);
  }
}
