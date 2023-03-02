package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.ReadData;
import com.nanjing.lightingdection.utils.DataGridResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ReadDataService {
  public DataGridResult findAll(Integer page, Integer rows);

  public List<ReadData> findAllByDatameaningId(Integer datameaningId);

  public Integer deleteReadDataById(Integer id);

  public Integer insertReadData(ReadData readData);

  public Integer updateReadData(ReadData readData);

  public ReadData findById(Integer id);
}
