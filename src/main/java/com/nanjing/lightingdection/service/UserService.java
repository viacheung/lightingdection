package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.User;
import com.nanjing.lightingdection.utils.DataGridResult;
import com.nanjing.lightingdection.utils.GlobalResult;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

  int isAdmin(String code);

  User queryById(String id);

  DataGridResult queryAll(Integer page, Integer rows);

  public GlobalResult insertUser(User user);

  public GlobalResult updateUser(User user);

  public GlobalResult deleteUserById(Integer user_id);
}
