package com.nanjing.lightingdection.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.lightingdection.dao.UserDao;
import com.nanjing.lightingdection.entity.User;
import com.nanjing.lightingdection.service.UserService;
import com.nanjing.lightingdection.utils.DataGridResult;
import com.nanjing.lightingdection.utils.GlobalResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserDao userDao;

  @Override
  public int isAdmin(String code) {
    // TODO Auto-generated method stub
    if (code.trim().equals("admin")) {
      return 1;
    }
    return 0;
  }

  @Override
  public User queryById(String id) {
    // TODO Auto-generated method stub
    return userDao.queryById(id);
  }

  @Override
  public DataGridResult queryAll(Integer page, Integer rows) {
    PageHelper.startPage(page, rows);
    List<User> list = userDao.queryAll();
    PageInfo<User> pageInfo = new PageInfo<>(list);
    DataGridResult result = new DataGridResult();
    result.setTotal((int) pageInfo.getTotal());
    result.setRows(pageInfo.getList());
    return result;
    // return list;
  }

  @Override
  public GlobalResult insertUser(User user) {
    if (user == null) {
      return new GlobalResult(400, "用户信息为空，添加失败！", null);
    }
    Integer integer = userDao.insertUser(user);
    if (integer == 0) {
      return new GlobalResult(400, "用户添加失败", null);
    } else {
      return new GlobalResult(200, "用户添加成功", null);
    }
  }

  @Override
  public GlobalResult updateUser(User user) {
    if (user == null) {
      return new GlobalResult(400, "用户信息为空，修改失败！", 400);
    }

    Integer integer = userDao.updateUser(user);
    if (integer == 0) {
      return new GlobalResult(400, "用户信息更新失败", null);
    } else {
      return new GlobalResult(200, "用户信息更新成功", null);
    }
  }

  @Override
  public GlobalResult deleteUserById(Integer userId) {
    if (userId == null) {
      return new GlobalResult(400, "用户删除失败！", 400);
    }
    Integer integer = userDao.deleteUserById(userId);
    if (integer == 0) {
      return new GlobalResult(400, "用户删除失败", null);
    } else {
      return new GlobalResult(200, "用户删除成功", null);
    }
    // return userDao.deleteUserById(user);
  }
}
