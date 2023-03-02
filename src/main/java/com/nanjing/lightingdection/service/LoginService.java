package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.Login;
import com.nanjing.lightingdection.utils.DataGridResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface LoginService {
  public Login getAll();

  public Login queryByCompany(String companyname);

  public DataGridResult queryAll(Integer page, Integer rows);

  public Integer updateLogin(MultipartFile import_file, Login login);

  public Integer deleteLoginById(Integer id);

  public Integer InsertLogin(MultipartFile import_file, Login login);
}
