package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.ReadData;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReadDataDao {
  public List<ReadData> findAll();

  public List<ReadData> findAllByDatameaningId(Integer datameaningId);

  public Integer insertReadData(ReadData readData);

  public Integer updateReadData(ReadData readData);

  public Integer deleteReadDataById(Integer id);

  public ReadData findById(Integer id);
}
