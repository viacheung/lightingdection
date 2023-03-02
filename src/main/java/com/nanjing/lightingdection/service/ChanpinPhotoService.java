package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.ChanpinPhoto;
import com.nanjing.lightingdection.utils.DataGridResult;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ChanpinPhotoService {
  public DataGridResult findAll(Integer page, Integer rows);

  public ChanpinPhoto findByChanpinPhotoId(Integer id);

  public Integer insertChanpinPhoto(ChanpinPhoto chanpinPhoto);

  public Integer deleteChanpinPhoto(Integer id);

  public Integer updateChanpinPhoto(ChanpinPhoto chanpinPhoto);

  public List<ChanpinPhoto> getAll();

  public void doImport(MultipartFile mfile, Integer chanpinId) throws IOException;

  public ChanpinPhoto getByChanpinId(Integer id);
}
