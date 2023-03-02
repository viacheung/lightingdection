package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.ErrorSet;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ErrorSetDao {
  public List<ErrorSet> findAll();

  public ErrorSet findAllByChanpinAndDatameaningId(
      @Param("chanpinId") Integer chanpinId, @Param("datameaningId") Integer datameaningId);

  public Integer insertErrorSet(ErrorSet errorSet);

  public Integer updateErrorSet(ErrorSet errorSet);

  public Integer deleteErrorSetById(Integer id);

  public ErrorSet findById(Integer id);
}
