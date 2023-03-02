package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.AppManage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AppManageService {
  public List<AppManage> queryAll();

  public List<AppManage> queryAllByPage(String name, Integer page, Integer size);

  public Integer updateApp(AppManage app);

  public Integer deleteAppById(AppManage app);

  public void doImport(File mfile, String type) throws IOException;
}
