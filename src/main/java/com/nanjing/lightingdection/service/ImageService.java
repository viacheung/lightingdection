package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.Image;
import com.nanjing.lightingdection.utils.DataGridResult;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
  public DataGridResult queryAll(Integer page, Integer rows);

  public Integer updateImage(Image image);

  public Integer deleteImageById(Integer id);

  public void doImport(MultipartFile file, String dots, Double size) throws IOException;

  public List<Image> getAll();
}
