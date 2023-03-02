package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.BaojingRecordDao;
import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.dao.DatameaningDao;
import com.nanjing.lightingdection.entity.BaojingRecord;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.service.BaojingRecordService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaojingRecordServiceImpl implements BaojingRecordService {
  @Autowired private BaojingRecordDao baojingRecordDao;
  @Autowired private ChanpinDao chanpinDao;
  @Autowired private DatameaningDao datameaningDao;

  @Override
  public Integer insertBaojingRecord(BaojingRecord baojing) {
    // TODO Auto-generated method stub
    // System.out.println(baojing);
    return baojingRecordDao.insertBaojingRecord(baojing);
  }

  @Override
  public Integer updateBaojingRecord(BaojingRecord user) {
    // TODO Auto-generated method stub
    // System.out.println(user);
    return baojingRecordDao.updateBaojingRecord(user);
  }

  @Override
  public List<BaojingRecord> findAll(
      Integer locationId,
      Integer leibieId,
      Integer chanpinId,
      Integer datameaningId,
      String startDate,
      String endDate) {
    List<BaojingRecord> all =
        baojingRecordDao.findAll(chanpinId, datameaningId, startDate, endDate);
    List<BaojingRecord> all1 = new ArrayList<>();
    List<BaojingRecord> all2 = new ArrayList<>();
    if (locationId != null && leibieId != null) {
      for (BaojingRecord bao : all) {
        Chanpin chan = chanpinDao.selectChanpinById(bao.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(bao);
        }
      }
      for (BaojingRecord bao : all1) {
        Chanpin chan = chanpinDao.selectChanpinById(bao.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all2.add(bao);
        }
      }
      return all2;
    } else if (locationId != null && leibieId == null) {
      for (BaojingRecord bao : all) {
        Chanpin chan = chanpinDao.selectChanpinById(bao.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(bao);
        }
      }
      return all1;
    } else if (locationId == null && leibieId != null) {
      for (BaojingRecord bao : all) {
        Chanpin chan = chanpinDao.selectChanpinById(bao.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all1.add(bao);
        }
      }
      return all1;
    } else {
      return all;
    }
  }

  @Override
  public Integer deleteBaojingRecordById(Integer id) {
    return baojingRecordDao.deleteBaojingRecordById(id);
  }

  @Override
  public BaojingRecord findById(Integer id) {
    return baojingRecordDao.findById(id);
  }
}
