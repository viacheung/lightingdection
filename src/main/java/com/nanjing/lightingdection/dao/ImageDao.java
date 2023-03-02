package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.Image;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageDao {
  public Image queryById(Integer id);

  public List<Image> queryAll();

  public Integer insertImage(Image image);

  public Integer updateImage(Image image);

  public Integer deleteImageById(Integer id);
}
