package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.ChanpinFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ChanpinFileService {
  public List<ChanpinFile> queryAll();

  public List<ChanpinFile> queryAllByPage(String chanpinName, Integer page, Integer size);

  public Integer updateCF(ChanpinFile app);

  public Integer deleteCFById(ChanpinFile app);

  public void doImport(File mfile, String chanpinId, String chanpinName) throws IOException;
}
