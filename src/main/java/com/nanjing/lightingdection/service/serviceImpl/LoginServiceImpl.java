package com.nanjing.lightingdection.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.lightingdection.dao.ImageDao;
import com.nanjing.lightingdection.dao.LoginDao;
import com.nanjing.lightingdection.entity.Image;
import com.nanjing.lightingdection.entity.Login;
import com.nanjing.lightingdection.service.LoginService;
import com.nanjing.lightingdection.utils.DataGridResult;
import com.nanjing.lightingdection.utils.FileUtil;
import com.nanjing.lightingdection.utils.SessionUtils;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LoginServiceImpl implements LoginService {
  @Autowired LoginDao logindao;
  @Autowired ImageDao imagedao;

  @Override
  public Login queryByCompany(String companyname) {
    return logindao.queryByCompany(companyname);
  }

  @Override
  public DataGridResult queryAll(Integer page, Integer rows) {
    PageHelper.startPage(page, rows);
    List<Login> list = logindao.queryAll();
    PageInfo<Login> pageInfo = new PageInfo<>(list);
    DataGridResult result = new DataGridResult();
    result.setTotal((int) pageInfo.getTotal());
    result.setRows(pageInfo.getList());
    return result;
  }

  @Override
  public Integer updateLogin(MultipartFile import_file, Login login) {
    // TODO Auto-generated method stub
    if (import_file.isEmpty()) {
      return logindao.updateLogin(login);
    } else {
      List<Image> images = imagedao.queryAll();
      List<Login> logins = logindao.queryAll();
      String[] filenames = import_file.getOriginalFilename().split("\\.");
      boolean ischongfu = false;
      int i = 0;
      String temp = filenames[0];
      while (true) {
        ischongfu = false;
        for (Image img : images) {
          if (img.getUrl().equals("images/" + temp + "." + filenames[1])) ischongfu = true;
        }
        if (ischongfu) {
          i++;
          temp = filenames[0] + "(" + i + ")";
        } else break;
      }
      if (i > 0) filenames[0] = filenames[0] + "(" + i + ")";

      i = 0;
      temp = filenames[0];
      while (true) {
        ischongfu = false;
        for (Login log : logins) {
          if (log.getUrl().equals("images/" + temp + "." + filenames[1])) ischongfu = true;
        }
        if (ischongfu) {
          i++;
          temp = filenames[0] + "(" + i + ")";
        } else break;
      }
      if (i > 0) filenames[0] = filenames[0] + "(" + i + ")";
      // 开始进行图片导入
      HttpServletRequest request = SessionUtils.getCurrentServletRequest();
      String realPath = request.getSession().getServletContext().getRealPath("/");
      String path = realPath + "WEB-INF/images/" + filenames[0] + "." + filenames[1]; // 新导入的图片位置
      String oldpath = realPath + login.getUrl();
      File delfile = new File(oldpath);
      boolean tag = delfile.delete();
      // 开始进行新图片的复制
      File file1 = new File(path);
      FileUtil.copy(import_file, file1);
      login.setUrl("WEB-INF/images/" + filenames[0] + "." + filenames[1]);
      int a = logindao.updateLogin(login);
      if (a > 0 && tag) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  @Override
  public Integer deleteLoginById(Integer id) {
    // TODO Auto-generated method stub
    Login delogin = logindao.queryById(id);
    HttpServletRequest request = SessionUtils.getCurrentServletRequest();
    String realpath = request.getSession().getServletContext().getRealPath("/");
    String path = realpath + delogin.getUrl();
    File delfile = new File(path);
    boolean tag = delfile.delete();
    Integer result = logindao.deleteLoginById(id);
    if (!tag) result = 0;
    return result;
  }

  @Override
  public Login getAll() {
    // TODO Auto-generated method stub
    return logindao.getAll();
  }

  @Override
  public Integer InsertLogin(MultipartFile import_file, Login login) {
    // TODO Auto-generated method stub
    if (import_file.isEmpty()) {
      return 0;
    } else {
      List<Image> images = imagedao.queryAll();
      List<Login> logins = logindao.queryAll();
      String[] filenames = import_file.getOriginalFilename().split("\\.");
      boolean ischongfu = false;
      int i = 0;
      String temp = filenames[0];
      while (true) {
        ischongfu = false;
        for (Image img : images) {
          if (img.getUrl().equals("images/" + temp + "." + filenames[1])) ischongfu = true;
        }
        if (ischongfu) {
          i++;
          temp = filenames[0] + "(" + i + ")";
        } else break;
      }
      if (i > 0) filenames[0] = filenames[0] + "(" + i + ")";

      i = 0;
      temp = filenames[0];
      while (true) {
        ischongfu = false;
        for (Login log : logins) {
          if (log.getUrl().equals("images/" + temp + "." + filenames[1])) ischongfu = true;
        }
        if (ischongfu) {
          i++;
          temp = filenames[0] + "(" + i + ")";
        } else break;
      }
      if (i > 0) filenames[0] = filenames[0] + "(" + i + ")";
      // 开始进行图片导入
      HttpServletRequest request = SessionUtils.getCurrentServletRequest();
      String realPath = request.getSession().getServletContext().getRealPath("/");
      String path = realPath + "WEB-INF/images/" + filenames[0] + "." + filenames[1]; // 新导入的图片位置

      // 开始进行新图片的复制
      File file1 = new File(path);
      FileUtil.copy(import_file, file1);
      login.setUrl("WEB-INF/images/" + filenames[0] + "." + filenames[1]);
      int a = logindao.insertLogin(login);
      if (a > 0) {
        return 1;
      } else {
        return 0;
      }
    }
  }
}
