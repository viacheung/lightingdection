package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.BaojingPanduan;
import com.nanjing.lightingdection.utils.DataGridResult;
import org.springframework.stereotype.Service;

@Service
public interface BaojingPanduanService {
  public DataGridResult queryAll(Integer page, Integer rows);

  public Integer insertBaojingPanduan(BaojingPanduan baojingPanduan);

  public Integer updateBaojingPanduan(BaojingPanduan baojingPanduan);

  public Integer deleteBaojingPanduanById(Integer id);

  public BaojingPanduan selectById(Integer chanpinId, Integer datameaningId);
}
