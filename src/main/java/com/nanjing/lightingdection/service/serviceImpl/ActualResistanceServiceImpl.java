package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.ActualResistanceDao;
import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.entity.ActualResistance;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.service.ActualResistanceService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualResistanceServiceImpl implements ActualResistanceService {
  @Autowired private ActualResistanceDao actualResistanceDao;
  @Autowired private ChanpinDao chanpinDao;

  @Override
  public Integer insertActualResistance(ActualResistance actualResistance) {
    return actualResistanceDao.insertActualResistance(actualResistance);
  }

  @Override
  public List<ActualResistance> getAll() {
    return actualResistanceDao.getAllDatas();
  }

  @Override
  public List<ActualResistance> findAll(
      Integer locationId, Integer leibieId, Integer chanpinId, String startDate, String endDate) {
    List<ActualResistance> all = actualResistanceDao.findAll(chanpinId, startDate, endDate);
    List<ActualResistance> all1 = new ArrayList<>();
    List<ActualResistance> all2 = new ArrayList<>();
    if (locationId != null && leibieId != null) {
      for (ActualResistance actualResistance : all) {
        Chanpin chan = chanpinDao.selectChanpinById(actualResistance.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(actualResistance);
        }
      }
      for (ActualResistance actualResistance : all1) {
        Chanpin chan = chanpinDao.selectChanpinById(actualResistance.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all2.add(actualResistance);
        }
      }
      return all2;
    } else if (locationId != null && leibieId == null) {
      for (ActualResistance actualResistance : all) {
        Chanpin chan = chanpinDao.selectChanpinById(actualResistance.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        Chanpin location = chanpinDao.selectChanpinById(leibie.getPid());
        if (location.getId().equals(locationId)) {
          all1.add(actualResistance);
        }
      }
      return all1;
    } else if (locationId == null && leibieId != null) {
      for (ActualResistance actualResistance : all) {
        Chanpin chan = chanpinDao.selectChanpinById(actualResistance.getChanpinId());
        Chanpin leibie = chanpinDao.selectChanpinById(chan.getPid());
        if (leibie.getId().equals(leibieId)) {
          all1.add(actualResistance);
        }
      }
      return all1;
    } else {
      return all;
    }
  }
}
