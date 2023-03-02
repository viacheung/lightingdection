package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.Login;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginDao {
  public Login getAll();

  public Login queryByCompany(String companyname);

  public Login queryById(Integer id);

  public List<Login> queryAll();

  public Integer insertLogin(Login login);

  public Integer updateLogin(Login login);

  public Integer deleteLoginById(Integer id);

  public Integer testInsert();
}
