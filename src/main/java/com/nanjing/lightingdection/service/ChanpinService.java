package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.utils.ChanpinTree;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ChanpinService {
  public List<Chanpin> queryAll();

  public List<ChanpinTree> findChanpinList();

  public Chanpin findChanpinById(Integer id);

  public List<Chanpin> findChanpinListById(Integer id);

  public Integer insertChanpin(Chanpin chanpin);

  public Integer updateChanpinById(Chanpin chanpin);

  public Integer deleteChanpinById(Integer id);

  public List<Chanpin> searchByName(String name);

  public List<Chanpin> getAllSonPoint(List<Chanpin> chanpin, Integer pid);

  public List<Chanpin> getAllSons(Integer id);

  public List<Datameaning> getAllSonDatameaning(Integer id);

  public List<Chanpin> findBySlaveId(Integer slaveId);

  public List<Chanpin> getAllChanpins();
}
