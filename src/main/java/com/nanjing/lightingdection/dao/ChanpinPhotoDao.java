package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.ChanpinPhoto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChanpinPhotoDao {
  public List<ChanpinPhoto> findAll();

  public ChanpinPhoto findByChanpinPhotoId(Integer id);

  public ChanpinPhoto findByChanpinId(Integer chanpinId);

  public Integer insertChanpinPhoto(ChanpinPhoto chanpinPhoto);

  public Integer deleteChanpinPhoto(Integer id);

  public Integer updateChanpinPhoto(ChanpinPhoto chanpinPhoto);
}
