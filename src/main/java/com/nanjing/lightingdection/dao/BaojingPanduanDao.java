package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.BaojingPanduan;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BaojingPanduanDao {
  public List<BaojingPanduan> queryAll();

  public Integer insertBaojingPanduan(BaojingPanduan baojingPanduan);

  public Integer updateBaojingPanduan(BaojingPanduan baojingPanduan);

  public Integer deleteBaojingPanduanById(Integer id);

  public BaojingPanduan selectByChanpinAndDatameaning(
      @Param("chanpinId") Integer chanpinId, @Param("datameaningId") Integer datameaningId);
}
