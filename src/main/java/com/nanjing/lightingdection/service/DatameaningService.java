package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.dto.ChanAndDataShai;
import com.nanjing.lightingdection.dto.DataDisplay;
import com.nanjing.lightingdection.dto.DataDisplayAdd;
import com.nanjing.lightingdection.dto.RecordData;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.entity.PhraseData;
import com.nanjing.lightingdection.utils.DatameaningTree;
import gnu.io.PortInUseException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

@Service
public interface DatameaningService {
  public List<Datameaning> queryAll();

  public List<DatameaningTree> findDatameaningList();

  public Datameaning findDatameaningById(Integer id);

  public List<Datameaning> findDatameaningListById(Integer id);

  public Integer insertDatameaning(Datameaning datameaning);

  public Integer updateDatameaningById(Datameaning datameaning);

  public Integer deleteDatameaningById(Integer id);

  public CopyOnWriteArrayList<DataDisplay> getDisplayData(
      Integer chanpinId, Integer rejisterId, Map<Integer, Byte> map) // 此处应该获得chanpin的编号和对应的寄存器类别
      throws NumberFormatException, UnknownHostException, IOException, PortInUseException,
          SocketException;

  public List<Datameaning> getAllSonPoint(List<Datameaning> datameaningList, Integer pid);

  public List<Datameaning> getDMAllSons(Integer id);

  public List<Datameaning> getAllInputRejister(Integer chanpinLeibieId);

  public List<Datameaning> getAllHoldingRejister(Integer chanpinLeibieId);

  public List<Datameaning> getAllCoilRejister(Integer chanpinLeibieId);

  public Integer getAllSonsLength(Integer type, Integer leibeiId);

  public CopyOnWriteArrayList<DataDisplay> getNullDisplayData(
      Integer chanpinId, Integer rejisterId);

  public CopyOnWriteArrayList<DataDisplayAdd> Init();

  public Map<Integer, Byte> readData(Integer chanpinId, Integer rejisterId)
      throws NumberFormatException, UnknownHostException, IOException, PortInUseException;

  public byte[] getReturnData(Integer chanpinId, Integer leibieId)
      throws NumberFormatException, UnknownHostException, IOException, PortInUseException;

  public List<RecordData> getAllRecordData(Integer chanpinId, Integer datameaningId);

  public ChanAndDataShai getShaiRecords(
      String type, Integer chanpinId, Integer datameaningId, String startDate, String endDate);

  // 一个用于统计更新时间的操作
  public void timesCount(String type, Integer chanpinId, Integer datameaningId);

  // 将解析数据解藕成单独的一个方法
  public PhraseData getPhraseData(
      byte[] bt, Integer chanpinId, Datameaning datameaning, PhraseData phraseData);
}
