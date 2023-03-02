package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.dao.InfoRecordDao;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.InfoRecord;
import com.nanjing.lightingdection.service.InfoRecordService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoRecordServiceImpl implements InfoRecordService {
  @Autowired private InfoRecordDao infoRecordDao;
  @Autowired private ChanpinDao chanpinDao;

  @Override
  public List<InfoRecord> findAll(
      Integer locationId,
      Integer leibieId,
      Integer chanpinId,
      Integer datameaningId,
      String startDate,
      String endDate) {
    List<InfoRecord> all = infoRecordDao.findAll(chanpinId, datameaningId, startDate, endDate);
    List<InfoRecord> all1 = new ArrayList<>();
    List<InfoRecord> all2 = new ArrayList<>();
    if (locationId != null && leibieId != null) {
      for (InfoRecord info : all) {
        Chanpin chan = chanpinDao.selectChanpinById(info.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(info);
        }
      }
      for (InfoRecord info : all1) {
        Chanpin chan = chanpinDao.selectChanpinById(info.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all2.add(info);
        }
      }
      return all2;
    } else if (locationId != null && leibieId == null) {
      for (InfoRecord info : all) {
        Chanpin chan = chanpinDao.selectChanpinById(info.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(info);
        }
      }
      return all1;
    } else if (locationId == null && leibieId != null) {
      for (InfoRecord info : all) {
        Chanpin chan = chanpinDao.selectChanpinById(info.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all1.add(info);
        }
      }
      return all1;
    } else {
      return all;
    }
  }

  @Override
  public Integer deleteInfoRecordById(Integer id) {
    return infoRecordDao.deleteInfoRecordById(id);
  }

  @Override
  public Integer insertInfoRecord(InfoRecord infoRecord) {
    return infoRecordDao.insertInfoRecord(infoRecord);
  }

  @Override
  public Integer updateInfoRecord(InfoRecord infoRecord) {
    return infoRecordDao.updateInfoRecord(infoRecord);
  }

  @Override
  public InfoRecord findById(Integer id) {
    return infoRecordDao.findById(id);
  }
}
