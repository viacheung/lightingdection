package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.utils.ChanpinTree;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChanpinDao {
  public List<Chanpin> queryAll();

  public List<ChanpinTree> selectChanpinList();

  public Chanpin selectChanpinById(@Param("id") Integer id);

  public List<Chanpin> selectChanpinListById(@Param("id") Integer id);

  public List<Chanpin> selectChanpinIdName(@Param("pid") Integer pid);

  public List<Chanpin> selectChanpinByName(@Param("name") String name);

  public Integer insertChanpin(Chanpin chanpin);

  public Integer deleteChanpinById(Integer id);

  public Integer updateChanpinById(Chanpin chanpin);

  public List<Chanpin> getAllSons(Integer id);

  public Integer selectCount();

  public List<Chanpin> findLocation();

  public List<Chanpin> findBaojingchanpin(Integer id);

  public List<Chanpin> findChanpinBySlaveId(Integer slaveId);

  public List<Chanpin> getAllChanpins();
}
