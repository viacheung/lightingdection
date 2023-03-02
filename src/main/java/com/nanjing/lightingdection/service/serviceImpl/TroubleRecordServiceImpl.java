package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.dao.TroubleRecordDao;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.TroubleRecord;
import com.nanjing.lightingdection.service.TroubleRecordService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TroubleRecordServiceImpl implements TroubleRecordService {
  @Autowired private TroubleRecordDao troubleRecordDao;
  @Autowired private ChanpinDao chanpinDao;

  @Override
  public Integer deleteTroubleRecordById(Integer id) {
    return troubleRecordDao.deleteTroubleRecordById(id);
  }

  @Override
  public Integer insertTroubleRecord(TroubleRecord troubleRecord) {
    return troubleRecordDao.insertTroubleRecord(troubleRecord);
  }

  @Override
  public Integer updateTroubleRecord(TroubleRecord troubleRecord) {
    return troubleRecordDao.updateTroubleRecord(troubleRecord);
  }

  @Override
  public TroubleRecord findById(Integer id) {
    return troubleRecordDao.findById(id);
  }

  @Override
  public List<TroubleRecord> findAll(
      Integer locationId,
      Integer leibieId,
      Integer chanpinId,
      Integer datameaningId,
      String startDate,
      String endDate) {
    List<TroubleRecord> all =
        troubleRecordDao.findAll(chanpinId, datameaningId, startDate, endDate);
    List<TroubleRecord> all1 = new ArrayList<>();
    List<TroubleRecord> all2 = new ArrayList<>();
    if (locationId != null && leibieId != null) {
      for (TroubleRecord trouble : all) {
        Chanpin chan = chanpinDao.selectChanpinById(trouble.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(trouble);
        }
      }
      for (TroubleRecord trouble : all1) {
        Chanpin chan = chanpinDao.selectChanpinById(trouble.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all2.add(trouble);
        }
      }
      return all2;
    } else if (locationId != null && leibieId == null) {
      for (TroubleRecord trouble : all) {
        Chanpin chan = chanpinDao.selectChanpinById(trouble.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(trouble);
        }
      }
      return all1;
    } else if (locationId == null && leibieId != null) {
      for (TroubleRecord trouble : all) {
        Chanpin chan = chanpinDao.selectChanpinById(trouble.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all1.add(trouble);
        }
      }
      return all1;
    } else {
      return all;
    }
  }
}
