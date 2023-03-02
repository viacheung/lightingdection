package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.AppManage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AppManageDao {
  public List<AppManage> queryAll();

  public AppManage queryById(Integer id);

  public List<AppManage> queryAllByPage(
      @Param("type") String type, @Param("start") Integer start, @Param("limit") Integer limit);

  public Integer insertApp(AppManage App);

  public Integer updateApp(AppManage App);

  public Integer deleteAppById(AppManage App);
}
