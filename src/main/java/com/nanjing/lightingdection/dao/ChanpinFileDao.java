package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.ChanpinFile;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChanpinFileDao {
  public List<ChanpinFile> queryAll();

  public ChanpinFile queryById(Integer id);

  public List<ChanpinFile> queryAllByPage(
      @Param("chanpinName") String name,
      @Param("start") Integer start,
      @Param("limit") Integer limit);

  public Integer insertCF(ChanpinFile App);

  public Integer updateCF(ChanpinFile App);

  public Integer deleteCFById(ChanpinFile App);
}
