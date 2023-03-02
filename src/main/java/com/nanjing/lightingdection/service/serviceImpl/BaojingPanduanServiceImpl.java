package com.nanjing.lightingdection.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.lightingdection.dao.BaojingPanduanDao;
import com.nanjing.lightingdection.entity.BaojingPanduan;
import com.nanjing.lightingdection.service.BaojingPanduanService;
import com.nanjing.lightingdection.utils.DataGridResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaojingPanduanServiceImpl implements BaojingPanduanService {
  @Autowired private BaojingPanduanDao baojingPanduanDao;

  @Override
  public DataGridResult queryAll(Integer page, Integer rows) {
    PageHelper.startPage(page, rows);
    List<BaojingPanduan> list = baojingPanduanDao.queryAll();
    PageInfo<BaojingPanduan> pageInfo = new PageInfo<>(list);
    DataGridResult result = new DataGridResult();
    result.setTotal((int) pageInfo.getTotal());
    result.setRows(pageInfo.getList());
    return result;
  }

  @Override
  public Integer insertBaojingPanduan(BaojingPanduan baojingPanduan) {
    return baojingPanduanDao.insertBaojingPanduan(baojingPanduan);
  }

  @Override
  public Integer updateBaojingPanduan(BaojingPanduan baojingPanduan) {
    return baojingPanduanDao.updateBaojingPanduan(baojingPanduan);
  }

  @Override
  public Integer deleteBaojingPanduanById(Integer id) {
    return baojingPanduanDao.deleteBaojingPanduanById(id);
  }

  @Override
  public BaojingPanduan selectById(Integer chanpinId, Integer datameaningId) {
    return baojingPanduanDao.selectByChanpinAndDatameaning(chanpinId, datameaningId);
  }
}
