package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.ChanpinFileDao;
import com.nanjing.lightingdection.entity.ChanpinFile;
import com.nanjing.lightingdection.service.ChanpinFileService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChanpinFileServiceImpl implements ChanpinFileService {

  @Autowired ChanpinFileDao chanpinFileDao;

  @Override
  public List<ChanpinFile> queryAll() {
    return chanpinFileDao.queryAll();
  }

  @Override
  public List<ChanpinFile> queryAllByPage(String chanpinName, Integer page, Integer size) {
    if (page == null) {
      page = 1;
    }
    if (size == null) {
      size = 10;
    }
    Integer start = (page - 1) * size;
    List<ChanpinFile> list = chanpinFileDao.queryAllByPage(chanpinName, start, size);
    return list;
  }

  @Override
  public Integer updateCF(ChanpinFile app) {
    // TODO Auto-generated method stub
    return chanpinFileDao.updateCF(app);
  }

  @Override
  public Integer deleteCFById(ChanpinFile cf) {
    // TODO Auto-generated method stub
    ChanpinFile decf = chanpinFileDao.queryById(cf.getId());
    String realpath = System.getProperty("user.dir");
    String filename = decf.getUrl();
    String path = realpath + "/resource/WEB-INF/" + filename;
    File delfile = new File(path);
    boolean tag = delfile.delete();
    Integer result = chanpinFileDao.deleteCFById(cf);
    if (!tag) result = 0;

    return result;
  }

  @Override
  public void doImport(File mfile, String chanpinId, String chanpinName) throws IOException {}
}
