package com.nanjing.lightingdection.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.lightingdection.dao.ImageDao;
import com.nanjing.lightingdection.dao.LoginDao;
import com.nanjing.lightingdection.entity.Image;
import com.nanjing.lightingdection.entity.Login;
import com.nanjing.lightingdection.service.ImageService;
import com.nanjing.lightingdection.utils.DataGridResult;
import com.nanjing.lightingdection.utils.FileUtil;
import com.nanjing.lightingdection.utils.SessionUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {
  @Autowired ImageDao imagedao;
  @Autowired LoginDao logindao;

  @Override
  public Integer updateImage(Image image) {
    // TODO Auto-generated method stub
    return imagedao.updateImage(image);
  }

  @Override
  public Integer deleteImageById(Integer id) {
    // TODO Auto-generated method stub
    Image deimage = imagedao.queryById(id);
    HttpServletRequest request = SessionUtils.getCurrentServletRequest();
    String realPath = request.getSession().getServletContext().getRealPath("/");
    // String[] filenames=deimage.getUrl().split("/");//需要转义
    // String filename=filenames[filenames.length-1];
    String path = realPath + deimage.getUrl();
    File delfile = new File(path);
    boolean tag = delfile.delete();
    Integer result = imagedao.deleteImageById(id);
    if (!tag) result = 0;
    return result;
  }

  @Override
  public void doImport(MultipartFile file, String dots, Double size) throws IOException {
    Image image = new Image(null, null, null, null, null);
    // System.out.println(mfile);
    List<Image> images = imagedao.queryAll();
    List<Login> logins = logindao.queryAll();
    String[] filenames = file.getOriginalFilename().split("\\.");
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
    HttpServletRequest request = SessionUtils.getCurrentServletRequest();
    String realPath = request.getSession().getServletContext().getRealPath("/");
    String path = realPath + "WEB-INF/images/" + filenames[0] + "." + filenames[1]; // 这里不用转义
    File file1 = new File(path);
    FileUtil.copy(file, file1);
    image.setName(filenames[0]);
    image.setDots(dots);
    image.setDotsize(size);
    image.setUrl("WEB-INF/images/" + filenames[0] + "." + filenames[1]);
    imagedao.insertImage(image);
  }

  public DataGridResult queryAll(Integer page, Integer rows) {
    PageHelper.startPage(page, rows);
    List<Image> list = imagedao.queryAll();
    PageInfo<Image> pageInfo = new PageInfo<>(list);
    DataGridResult result = new DataGridResult();
    result.setTotal((int) pageInfo.getTotal());
    result.setRows(pageInfo.getList());
    return result;
  }

  @Override
  public List<Image> getAll() {
    return imagedao.queryAll();
  }
}
