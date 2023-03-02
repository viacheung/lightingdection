package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.AppManageDao;
import com.nanjing.lightingdection.entity.AppManage;
import com.nanjing.lightingdection.service.AppManageService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppManageServiceImpl implements AppManageService {

  @Autowired AppManageDao appManageDao;

  @Override
  public List<AppManage> queryAll() {
    return appManageDao.queryAll();
  }

  @Override
  public List<AppManage> queryAllByPage(String name, Integer page, Integer size) {
    if (page == null) {
      page = 1;
    }
    if (size == null) {
      size = 10;
    }
    Integer start = (page - 1) * size;
    List<AppManage> list = appManageDao.queryAllByPage(name, start, size);
    return list;
  }

  @Override
  public Integer updateApp(AppManage app) {
    // TODO Auto-generated method stub
    return appManageDao.updateApp(app);
  }

  @Override
  public Integer deleteAppById(AppManage app) {
    // TODO Auto-generated method stub
    AppManage deapp = appManageDao.queryById(app.getId());
    String realpath = System.getProperty("user.dir");
    String filename = deapp.getUrl();
    String path = realpath + "/resource/WEB-INF/" + filename;
    File delfile = new File(path);
    boolean tag = delfile.delete();
    Integer result = appManageDao.deleteAppById(app);
    if (!tag) result = 0;

    return result;
  }

  @Override
  public void doImport(File mfile, String type) throws IOException {}
}
