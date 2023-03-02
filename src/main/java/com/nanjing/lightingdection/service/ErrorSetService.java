package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.ErrorSet;
import com.nanjing.lightingdection.utils.DataGridResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ErrorSetService {
  public DataGridResult findAll(Integer page, Integer rows);

  public ErrorSet findAllByChanpinAndDatameaningId(Integer chanpinId, Integer datameaningId);

  public Integer insertErrorSet(ErrorSet errorSet);

  public Integer updateErrorSet(ErrorSet errorSet);

  public Integer deleteErrorSetById(Integer id);

  public ErrorSet findById(Integer id);

  public List<ErrorSet> getAll();
}
