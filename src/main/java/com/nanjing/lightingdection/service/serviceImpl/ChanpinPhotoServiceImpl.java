package com.nanjing.lightingdection.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.dao.ChanpinPhotoDao;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.ChanpinPhoto;
import com.nanjing.lightingdection.service.ChanpinPhotoService;
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
public class ChanpinPhotoServiceImpl implements ChanpinPhotoService {
  @Autowired private ChanpinPhotoDao chanpinPhotoDao;
  @Autowired private ChanpinDao chanpinDao;

  @Override
  public DataGridResult findAll(Integer page, Integer rows) {
    PageHelper.startPage(page, rows);
    List<ChanpinPhoto> list = chanpinPhotoDao.findAll();
    PageInfo<ChanpinPhoto> pageInfo = new PageInfo<>(list);
    DataGridResult result = new DataGridResult();
    result.setTotal((int) pageInfo.getTotal());
    result.setRows(pageInfo.getList());
    return result;
  }

  @Override
  public ChanpinPhoto getByChanpinId(Integer id) {
    return chanpinPhotoDao.findByChanpinId(id);
  }

  @Override
  public ChanpinPhoto findByChanpinPhotoId(Integer id) {
    return chanpinPhotoDao.findByChanpinPhotoId(id);
  }

  @Override
  public Integer insertChanpinPhoto(ChanpinPhoto chanpinPhoto) {
    return chanpinPhotoDao.insertChanpinPhoto(chanpinPhoto);
  }

  @Override
  public Integer deleteChanpinPhoto(Integer id) {

    ChanpinPhoto chanpinPhoto = chanpinPhotoDao.findByChanpinPhotoId(id);
    HttpServletRequest request = SessionUtils.getCurrentServletRequest();
    String realPath = request.getSession().getServletContext().getRealPath("/");
    //  String[] filenames=chanpinPhoto.getUrl().split("/");//需要转义

    // String filename=filenames[filenames.length-1];
    String path = realPath + chanpinPhoto.getUrl();

    File delfile = new File(path);
    boolean tag = delfile.delete();
    Integer result = chanpinPhotoDao.deleteChanpinPhoto(id);
    if (!tag) result = 0;
    return result;
  }

  @Override
  public Integer updateChanpinPhoto(ChanpinPhoto chanpinPhoto) {
    return chanpinPhotoDao.updateChanpinPhoto(chanpinPhoto);
  }

  @Override
  public void doImport(MultipartFile mfile, Integer chanpinId) throws IOException {
    Chanpin chanpin = chanpinDao.selectChanpinById(chanpinId);

    List<ChanpinPhoto> chanpinPhotos = chanpinPhotoDao.findAll();
    // System.out.println("chanpinPhotos: "+chanpinPhotos);

    String[] filenames = mfile.getOriginalFilename().split("\\.");

    boolean ischongfu = false;
    int i = 0;
    String temp = filenames[0];
    System.out.println("mfile: " + mfile.getOriginalFilename());
    System.out.println("temp: " + temp);
    System.out.println("filenames[1]: " + filenames[1]);
    while (true) {
      ischongfu = false;
      for (ChanpinPhoto chanpinImage : chanpinPhotos) {
        if (chanpinImage.getUrl().equals("WEB-INF/chanpinPhoto/" + temp + "." + filenames[1]))
          ischongfu = true;
      }
      if (ischongfu) {
        i++;
        temp = filenames[0] + "(" + i + ")";
      } else break;
    }
    if (i > 0) filenames[0] = filenames[0] + "(" + i + ")";
    HttpServletRequest request = SessionUtils.getCurrentServletRequest();
    String realPath = request.getSession().getServletContext().getRealPath("/");
    String path = realPath + "WEB-INF/chanpinPhoto/" + filenames[0] + "." + filenames[1]; // 这里不用转义
    File file = new File(path);
    // System.out.println(path);
    FileUtil.copy(mfile, file);
    ChanpinPhoto chanpinPhoto =
        new ChanpinPhoto(
            null,
            chanpin.getLocation(),
            chanpin.getName(),
            chanpinId,
            filenames[0],
            "WEB-INF/chanpinPhoto/" + filenames[0] + "." + filenames[1]);
    // chanpinPhoto.setName(filenames[0]);
    // chanpinPhoto.setUrl("WEB-INF/chanpinPhoto/"+filenames[0]+"."+filenames[1]);
    // chanpinPhoto.setLeibie(chanpin.getName());
    // chanpinPhoto.setLocation(chanpin.getLocation());
    // chanpinPhoto.setChanpinId(chanpinId);
    chanpinPhotoDao.insertChanpinPhoto(chanpinPhoto);
  }

  @Override
  public List<ChanpinPhoto> getAll() {
    return chanpinPhotoDao.findAll();
  }
}
