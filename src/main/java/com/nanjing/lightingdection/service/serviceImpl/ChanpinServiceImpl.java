package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.dao.DatameaningDao;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.service.ChanpinService;
import com.nanjing.lightingdection.service.DatameaningService;
import com.nanjing.lightingdection.utils.ChanpinTree;
import com.nanjing.lightingdection.utils.RedisUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChanpinServiceImpl implements ChanpinService {
  static List<Chanpin> childMenu = new ArrayList<Chanpin>();
  @Autowired private ChanpinDao chanpindao;
  @Autowired private DatameaningDao datameanigDao;
  @Autowired private RedisUtil redisUtil;
  @Autowired private DatameaningService datameaningService;

  @Override
  public List<Chanpin> queryAll() { // 需要改成从redis获取
    // TODO Auto-generated method stub
    if (redisUtil.get("ac:") == null) {
      List<Chanpin> chanpins = chanpindao.queryAll();
      String str = "";
      for (Chanpin chanpin : chanpins) str += chanpin.getId() + "-";
      redisUtil.set("ac:", str);
      return chanpins;
    } else {
      String cs = (String) redisUtil.get("ac:");
      String[] chanpinId = cs.split("-");
      List<Chanpin> chanpins = new ArrayList<>();
      for (int i = 0; i < chanpinId.length; i++) {
        Chanpin chanpin = findChanpinById(Integer.parseInt(chanpinId[i]));
        chanpins.add(chanpin);
      }
      return chanpins;
    }
  }

  @Override
  public List<ChanpinTree> findChanpinList() {
    return chanpindao.selectChanpinList();
  }

  @Override
  public Chanpin findChanpinById(Integer id) {

    Chanpin chanpin = (Chanpin) redisUtil.get("cp:" + id);
    if (chanpin != null) {
      return chanpin;
    } else {
      Chanpin cp = chanpindao.selectChanpinById(id);
      redisUtil.set("cp:" + id, cp);
      return cp;
    }
  }

  @Override
  public List<Chanpin> findChanpinListById(Integer id) {
    return chanpindao.selectChanpinListById(id);
  }

  @Override
  public Integer insertChanpin(Chanpin chanpin) {
    // TODO Auto-generated method stub
    return chanpindao.insertChanpin(chanpin);
  }

  @Override
  public Integer updateChanpinById(Chanpin chanpin) { // 需要该成从Redis获取
    try {
      redisUtil.set("cp:" + chanpin.getId(), chanpin);
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public Integer deleteChanpinById(Integer id) {
    return chanpindao.deleteChanpinById(id);
  }

  @Override
  public List<Chanpin> searchByName(String name) {
    return chanpindao.selectChanpinByName(name);
  }

  @Override
  public List<Chanpin> getAllSonPoint(List<Chanpin> chanpinList, Integer pid) {
    for (Chanpin mu : chanpinList) {
      // 遍历出父id等于参数的id，add进子节点集合
      if (mu.getPid() == pid) {
        // 递归遍历下一级
        getAllSonPoint(chanpinList, mu.getId());
        childMenu.add(mu);
      }
    }
    return childMenu;
  }

  @Override
  public List<Chanpin> getAllSons(Integer id) {

    String keys = (String) redisUtil.get("CS:" + id);
    if (keys == null || keys.equals("")) {
      List<Chanpin> chanpins = chanpindao.getAllSons(id);
      if (chanpins != null) {
        String s = "";
        for (int i = 0; i < chanpins.size(); i++) {
          s += chanpins.get(i).getId() + "-";
        }
        redisUtil.set("CS:" + id, s);
      }
      return chanpins;
    } else {
      List<Chanpin> list = new ArrayList<>();
      String[] strs = keys.split("-");
      for (int i = 0; i < strs.length; i++) {
        list.add(findChanpinById(Integer.parseInt(strs[i])));
      }
      return list;
    }
  }

  @Override
  public List<Datameaning> getAllSonDatameaning(Integer id) {
    // TODO Auto-generated method stub
    List<Datameaning> list = new ArrayList<>();
    Chanpin chanpin = chanpindao.selectChanpinById(id);
    // System.out.println(chanpin);
    Datameaning datameaning = datameanigDao.selectDatameaningById(chanpin.getDatameaningId());
    if (datameaning != null) {
      list.add(datameaning);
      List<Datameaning> all = datameanigDao.queryAll();
      List<Datameaning> son = datameaningService.getAllSonPoint(all, datameaning.getId());
      if (son != null) {
        for (int i = 0; i < son.size(); i++) {
          list.add(son.get(i));
        }
      }
    }
    return list;
  }

  @Override
  public List<Chanpin> findBySlaveId(Integer slaveId) { // 改成从Redis读取,暂时可以先不改
    return chanpindao.findChanpinBySlaveId(slaveId);
  }

  @Override
  public List<Chanpin> getAllChanpins() {
    return chanpindao.getAllChanpins();
  }
}
