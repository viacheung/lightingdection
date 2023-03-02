package com.nanjing.lightingdection.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.lightingdection.dao.ErrorSetDao;
import com.nanjing.lightingdection.entity.ErrorSet;
import com.nanjing.lightingdection.service.ErrorSetService;
import com.nanjing.lightingdection.utils.DataGridResult;
import com.nanjing.lightingdection.utils.RedisUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorSetServiceImpl implements ErrorSetService {
  @Autowired private ErrorSetDao errorSetDao;
  @Autowired private RedisUtil redisUtil;

  @Override
  public DataGridResult findAll(Integer page, Integer rows) {
    PageHelper.startPage(page, rows);
    List<ErrorSet> list = errorSetDao.findAll();
    PageInfo<ErrorSet> pageInfo = new PageInfo<>(list);
    DataGridResult result = new DataGridResult();
    result.setTotal((int) pageInfo.getTotal());
    result.setRows(pageInfo.getList());
    return result;
  }

  @Override
  public ErrorSet findAllByChanpinAndDatameaningId(Integer chanpinId, Integer datameaningId) {
    return (ErrorSet) redisUtil.get("Es:" + chanpinId + datameaningId);
  }

  @Override
  public Integer insertErrorSet(ErrorSet errorSet) {
    return errorSetDao.insertErrorSet(errorSet);
  }

  @Override
  public Integer updateErrorSet(ErrorSet errorSet) {
    return errorSetDao.updateErrorSet(errorSet);
  }

  @Override
  public Integer deleteErrorSetById(Integer id) {
    return errorSetDao.deleteErrorSetById(id);
  }

  @Override
  public ErrorSet findById(Integer id) {
    return errorSetDao.findById(id);
  }

  @Override
  public List<ErrorSet> getAll() {
    return errorSetDao.findAll();
  }
}
