package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.utils.DatameaningTree;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DatameaningDao {
  public List<Datameaning> queryAll();

  public List<DatameaningTree> selectDatameaningList();

  public Datameaning selectDatameaningById(@Param("id") Integer id);

  public List<Datameaning> selectDatameaningListById(@Param("id") Integer id);

  public List<Datameaning> selectDatameaningIdName(@Param("pid") Integer pid);

  public Integer insertDatameaning(Datameaning datameaning);

  public Integer deleteDatameaningById(Integer id);

  public Integer updateDatameaningById(Datameaning datameaning);

  public List<Datameaning> getAllSons(Integer id);

  public List<Datameaning> getAllJiance();
}
