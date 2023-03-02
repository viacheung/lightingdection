package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

  public User queryById(String id);

  public List<User> queryAll();

  public Integer insertUser(User user);

  public Integer updateUser(User user);

  public Integer deleteUserById(Integer userId);
}
