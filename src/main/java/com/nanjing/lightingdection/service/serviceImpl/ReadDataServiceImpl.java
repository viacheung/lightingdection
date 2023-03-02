package com.nanjing.lightingdection.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.lightingdection.dao.ReadDataDao;
import com.nanjing.lightingdection.entity.ReadData;
import com.nanjing.lightingdection.service.ReadDataService;
import com.nanjing.lightingdection.utils.DataGridResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadDataServiceImpl implements ReadDataService {
  @Autowired private ReadDataDao readDataDao;

  @Override
  public Integer deleteReadDataById(Integer id) {
    return readDataDao.deleteReadDataById(id);
  }

  @Override
  public Integer insertReadData(ReadData readData) {
    return readDataDao.insertReadData(readData);
  }

  @Override
  public Integer updateReadData(ReadData readData) {
    return readDataDao.updateReadData(readData);
  }

  @Override
  public ReadData findById(Integer id) {
    return readDataDao.findById(id);
  }

  @Override
  public DataGridResult findAll(Integer page, Integer rows) {
    PageHelper.startPage(page, rows);
    List<ReadData> list = readDataDao.findAll();
    PageInfo<ReadData> pageInfo = new PageInfo<>(list);
    DataGridResult result = new DataGridResult();
    result.setTotal((int) pageInfo.getTotal());
    result.setRows(pageInfo.getList());
    return result;
  }

  @Override
  public List<ReadData> findAllByDatameaningId(Integer datameaningId) {
    return readDataDao.findAllByDatameaningId(datameaningId);
  }
}
