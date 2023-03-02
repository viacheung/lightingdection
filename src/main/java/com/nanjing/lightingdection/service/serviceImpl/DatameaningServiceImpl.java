package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.cache.CacheManage;
import com.nanjing.lightingdection.config.ConfigValue;
import com.nanjing.lightingdection.dao.*;
import com.nanjing.lightingdection.dto.*;
import com.nanjing.lightingdection.entity.*;
import com.nanjing.lightingdection.service.DatameaningService;
import com.nanjing.lightingdection.service.ErrorSetService;
import com.nanjing.lightingdection.tools.*;
import com.nanjing.lightingdection.utils.DatameaningTree;
import com.nanjing.lightingdection.utils.DateUtil;
import com.nanjing.lightingdection.utils.RedisUtil;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.ServletContext;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.nanjing.lightingdection.cache.CacheManage.getMapSockets;
//import redis.clients.jedis.Jedis;

@Service
public class DatameaningServiceImpl implements DatameaningService {
  @Autowired
  private DatameaningDao datameaningDao;
  @Autowired
  private ChanpinDao chanpinDao;
  @Autowired
  private PanDuanDao panDuanDao;
  @Autowired
  private BaojingPanduanDao baojingPanduanDao;
  @Autowired
  private BaojingRecordDao baojingRecordDao;
  @Autowired
  private InfoRecordDao infoRecordDao;
  @Autowired
  private TroubleRecordDao troubleRecordDao;
  @Autowired
  private RedisUtil redisUtil;
  @Autowired
  private ServletContext servletContext;
  @Autowired
  private ErrorSetService errorSetService;
  @Autowired
  private ConfigValue configValue;
  static List<Datameaning> childMenu = new ArrayList<Datameaning>();

  @Override
  public List<Datameaning> queryAll() {
    return datameaningDao.queryAll();
  }

  @Override
  public List<DatameaningTree> findDatameaningList() {


    return datameaningDao.selectDatameaningList();
  }

  /**
   * 先进性redis读取,若无再从mysql读取，之后更新到redis
   */
  @Override
  public Datameaning findDatameaningById(Integer id) {
    Datameaning datameaning = (Datameaning) redisUtil.get("dm:" + id);
    if (datameaning != null) {
      return datameaning;
    } else {
      Datameaning damean = datameaningDao.selectDatameaningById(id);
      redisUtil.set("dm:" + id, damean);
      return damean;
    }

  }

  @Override
  public List<Datameaning> findDatameaningListById(Integer id) {
    return datameaningDao.selectDatameaningListById(id);
  }

  @Override
  public Integer insertDatameaning(Datameaning datameaning) {
    return datameaningDao.insertDatameaning(datameaning);
  }

  @Override
  public Integer updateDatameaningById(Datameaning datameaning) {
    return datameaningDao.updateDatameaningById(datameaning);
  }

  @Override
  public Integer deleteDatameaningById(Integer id) {
    return datameaningDao.deleteDatameaningById(id);
  }


  /**
   * 将子菜单以1-2-3对应的这种ID形式存储，直接从Redis读取
   * 形式为DS:1
   */

  @Override
  public List<Datameaning> getDMAllSons(Integer id) {//获得所有的需要解析的子树

    String keys = (String) redisUtil.get("DS:" + id);
    if (keys == null || keys.equals("")) {
      List<Datameaning> datameanings = datameaningDao.getAllSons(id);
      if (datameanings != null) {
        String s = "";
        for (int i = 0; i < datameanings.size(); i++) {
          s += datameanings.get(i).getId() + "-";
        }
        redisUtil.set("DS:" + id, s);
      }
      return datameanings;
    } else {
      List<Datameaning> list = new ArrayList<>();
      String[] strs = keys.split("-");
      for (int i = 0; i < strs.length; i++) {
        list.add(findDatameaningById(Integer.parseInt(strs[i])));
      }
      return list;
    }
  }


  @Override
  public CopyOnWriteArrayList<DataDisplay> getDisplayData(Integer chanpinId, Integer rejisterId, Map<Integer, Byte> map)//此处应该获得chanpin的编号和对应的寄存器类别
          throws NumberFormatException, UnknownHostException, IOException, PortInUseException {
    //chanpinId对应的是具体的产品的id,rejisterId对应的是具体的解析表的一级菜单
    //System.out.println("执行读取数据！");
    // Date start=new Date();
    //System.out.println("起始时间： "+start);
    CopyOnWriteArrayList<DataDisplay> dataDisplays = new CopyOnWriteArrayList<>();
    List<PhraseData> phraseDatas = new ArrayList<>();
    //Chanpin chanpin=chanpinDao.selectChanpinById(chanpinId);//获得具体是哪一个产品
    // List<Datameaning> erji=datameaningDao.getAllSons(rejisterId);//获得所有的二级菜单
    List<Datameaning> erji = getDMAllSons(rejisterId);//获得所有的二级菜单
    List<Datameaning> fathers = new ArrayList<>();//获得所有的三级菜单
    List<Datameaning> datameanings = new ArrayList<>();//获得所有的四级菜单
    //Jedis jedis=new Jedis("127.0.0.1",6379);
    for (Datameaning one : erji) {
      if (one.getType() == 1) {
        List<Datameaning> all = getDMAllSons(one.getId());
        for (Datameaning all1 : all) {
          fathers.add(all1);
        }
      }
    }
    for (Datameaning one : fathers) {
      List<Datameaning> all = getDMAllSons(one.getId());
      for (Datameaning all1 : all) {
        datameanings.add(all1);
      }
    }

    //开始遍历循环每一个需要解析的数据
    for (int i = 0; i < datameanings.size(); i++) {
      //Date star1t=new Date();
      //System.out.println("起始时间1： "+star1t);
      //System.out.println(" ");
      //预先设置好所有的id、时间、名称等信息
      Datameaning datameaning = datameanings.get(i);
      Date date = new Date();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      PhraseData phraseData = new PhraseData(null, null, null, null, null, null, null, null);
      phraseData.setTime(df.format(date));
      phraseData.setId(datameaning.getId());
      phraseData.setName(datameaning.getName());
      //Integer IsPaint = datameaning.getIsPaint();
      if (datameaning.getIsPaint()== null || datameaning.getIsPaint().equals(1)) {
        phraseData.setIsPaint(1);
      } else {
        phraseData.setIsPaint(0);
      }
      //给数据加上是否展示以及是否被记录的判断
      if (datameaning.getIsShown() == null) {
        phraseData.setIsShown(1);
      }
      else {
        if (datameaning.getIsShown().equals(0)) {
          phraseData.setIsShown(0);
        } else {
          phraseData.setIsShown(1);
        }
      }
      if (datameaning.getIsRecord() == null) {
        phraseData.setIsRecord(0);
      }
      else {
        if (datameaning.getIsRecord().equals(1)) {
          phraseData.setIsRecord(1);
        } else {
          phraseData.setIsRecord(0);
        }
      }
      if (map.get(datameaning.getByteAddress()) == null) {//缓存中没有读取到数据，直接显示当前无法读取到数据
        Random random = new Random();
        int s = 0;
        phraseData.setData(s+datameaning.getUnit());//模拟无法读取到的数据，暂时读取为
        phraseData.setZhuangtai(0);
      }
      else {//存在数据的时候开始进行相关的解析
        byte[] bt=null;
        if(datameaning.getPattern().equals(1)||(datameaning.getPattern().equals(2)&&datameaning.getByteNum().equals(1))){
          //单字节情况
          bt=new byte[1];
          bt[0]=map.get(datameaning.getByteAddress());
        }
        else{
          //多字节情况
          bt=new byte[datameaning.getByteNum()];
          for(int j=0;j<bt.length;j++)
            bt[j]=map.get(j+datameaning.getByteAddress());
        }
        phraseData=getPhraseData(bt,chanpinId,datameaning,phraseData);
      }
      //if(chanpinId.equals(25)||chanpinId.equals(40)) System.out.println("data:"+phraseData);
      phraseDatas.add(phraseData);
    }
    //System.out.println("phraseDatas:"+phraseDatas);
    for (int i = 0; i < fathers.size(); i++) {//进行displayData的最终整合
      Datameaning father = fathers.get(i);
      DataDisplay dataDisplay = new DataDisplay(null, null);
      dataDisplay.setName(father.getName());
      List<Datameaning> son = datameaningDao.getAllSons(father.getId());
      CopyOnWriteArrayList<SonData> sonDatas = new CopyOnWriteArrayList<>();
      for (int j = 0; j < son.size(); j++) {
        SonData sonData = new SonData(null, null, null, null, null, null, null, null, null);
        for (int k = 0; k < phraseDatas.size(); k++) {
          if (son.get(j).getId().equals(phraseDatas.get(k).getId())) {
            sonData.setId(phraseDatas.get(k).getId());
            sonData.setName(phraseDatas.get(k).getName());
            sonData.setData(phraseDatas.get(k).getData());
            sonData.setTime(phraseDatas.get(k).getTime());
            sonData.setZhuangtai(phraseDatas.get(k).getZhuangtai());
            sonData.setIsShown(phraseDatas.get(k).getIsShown());
            sonData.setIsRecord(phraseDatas.get(k).getIsRecord());
            sonData.setIsPaint(phraseDatas.get(k).getIsPaint());
            //此处对异常部分的数据进行重新解析和判断
            if(phraseDatas.get(k).getIsShown().equals(1)){//需要展示出来的数据才需要
              Object correctData=redisUtil.get("CV:" + chanpinId+phraseDatas.get(k).getId());
              if (phraseDatas.get(k).getZhuangtai().equals(1) || phraseDatas.get(k).getZhuangtai().equals(2)) {//预警或者报警，从缓存数据库读取
                //进行判断，是否是无穷大
                Double val=Double.parseDouble(Split.getNumber((String)sonData.getData()));
                if(val>=500.0){
                  sonData.setData(sonData.getData());
                  sonData.setZhuangtai(1);
                }
                else{
                  //先判断是否写死数据,此时不需要经过正确数据缓存的判断
                  ErrorSet errorSet=errorSetService.findAllByChanpinAndDatameaningId(chanpinId,phraseDatas.get(k).getId());
                  if(errorSet!=null){
                       sonData.setZhuangtai(0);
                       if(errorSet.getValue()!=null&&!errorSet.getValue().trim().equals("")){
                           sonData.setData(errorSet.getValue()+errorSet.getUnit());
                       }
                       else{
                         Datameaning datamean=findDatameaningById(phraseDatas.get(k).getId());
                         Double rtn=errorSet.getMinValue()+Math.random()*(errorSet.getMaxValue()-errorSet.getMinValue());
                         Integer Bit = datamean.getBit();//看保留小数点后几位
                         DecimalFormat dfs = null;
                         if (Bit == null) {
                           dfs = new DecimalFormat("######0.00");
                         } else if (Bit == 0) {
                           dfs = new DecimalFormat("######0");
                         } else {
                           String ds = "######0.";
                           for (int h = 0; h < Bit; h++) {
                             ds += "0";
                           }
                           dfs = new DecimalFormat(ds);
                         }
                         sonData.setData(dfs.format(rtn)+errorSet.getUnit());
                       }
                  }
                  else{
                      if (correctData!=null){//正确数据的缓存依然存在的时候
                        sonData.setData(correctData);
                        sonData.setZhuangtai(0);
                      }
                      else { // 进行数据插入到数据库
                          String str = "";
                          if (redisUtil.get("Mc:" + chanpinId) != null) {
                            byte[] cacheBytes =
                                ((String) (redisUtil.get("Mc:" + chanpinId)))
                                    .getBytes(CharEncoding.ISO_8859_1);
                            // String str = "";
                            if (cacheBytes != null) {
                              for (int ll = 0; ll < cacheBytes.length; ll++) {
                                str += CRC.parseHexToString(cacheBytes[ll]) + " ";
                              }
                            }
                          }
                          if (phraseDatas.get(k).getZhuangtai().equals(1)) {
                              TroubleRecord trouble =
                                  new TroubleRecord(
                                      null,
                                      chanpinId,
                                      sonData.getId(),
                                      sonData.getName(),
                                      sonData.getData() + "",
                                      sonData.getTime(),
                                      str);
                              troubleRecordDao.insertTroubleRecord(trouble);
                           }
                          if (phraseDatas.get(k).getZhuangtai().equals(2)) {
                            BaojingRecord record =
                                new BaojingRecord(
                                    null,
                                    chanpinId,
                                    sonData.getId(),
                                    sonData.getName(),
                                    sonData.getData() + "",
                                    sonData.getTime(),
                                    str);
                            baojingRecordDao.insertBaojingRecord(record);
                            // count2++;
                          }
                      }
                  }
                }
              }
              else {//重新设置正确数据与过期时间
                timesCount("C",chanpinId,phraseDatas.get(k).getId());//统计更新正确数据时间
                if(redisUtil.set("CV:" + chanpinId+phraseDatas.get(k).getId(), phraseDatas.get(k).getData())){
                  Random random = new Random();
                  redisUtil.expire("CV:" + chanpinId+phraseDatas.get(k).getId(), random.nextInt(configValue.lastCorrectTime-configValue.earlyCorrectTime)+configValue.earlyCorrectTime);
                }
              }
            }
            sonDatas.add(sonData);
          }
        }
      }
      dataDisplay.setSons(sonDatas);
      dataDisplays.add(dataDisplay);
    }
    return dataDisplays;
  }


  @Override
  public List<Datameaning> getAllSonPoint(List<Datameaning> datameaningList, Integer pid) {
    for (Datameaning mu : datameaningList) {
      //遍历出父id等于参数的id，add进子节点集合
      if (mu.getPid() == pid) {
        //递归遍历下一级
        getAllSonPoint(datameaningList, mu.getId());
        childMenu.add(mu);
      }
    }
    return childMenu;
  }

  @Override
  public List<Datameaning> getAllInputRejister(Integer chanpinLeibieId) {
    // TODO Auto-generated method stub
    Chanpin leibie = chanpinDao.selectChanpinById(chanpinLeibieId);
    Datameaning dataleibie = datameaningDao.selectDatameaningById(leibie.getDatameaningId());
    List<Datameaning> sons = datameaningDao.getAllSons(dataleibie.getId());
    List<Datameaning> all = new ArrayList<>();
    for (Datameaning son : sons) {
      if (son.getType() == 1)
        all.add(son);
    }
    return all;
  }

  @Override
  public List<Datameaning> getAllHoldingRejister(Integer chanpinLeibieId) {
    Chanpin leibie = chanpinDao.selectChanpinById(chanpinLeibieId);
    Datameaning dataleibie = datameaningDao.selectDatameaningById(leibie.getDatameaningId());
    List<Datameaning> sons = datameaningDao.getAllSons(dataleibie.getId());
    List<Datameaning> all = new ArrayList<>();
    for (Datameaning son : sons) {
      if (son.getType() == 2)
        all.add(son);
    }
    return all;
  }

  @Override
  public List<Datameaning> getAllCoilRejister(Integer chanpinLeibieId) {
    Chanpin leibie = chanpinDao.selectChanpinById(chanpinLeibieId);
    Datameaning dataleibie = datameaningDao.selectDatameaningById(leibie.getDatameaningId());
    List<Datameaning> sons = datameaningDao.getAllSons(dataleibie.getId());
    List<Datameaning> all = new ArrayList<>();
    for (Datameaning son : sons) {
      if (son.getType() == 3)
        all.add(son);
    }
    return all;
  }

  @Override
  public Integer getAllSonsLength(Integer type, Integer leibieId) {
    // TODO Auto-generated method stub
    List<Datameaning> all = queryAll();
    Chanpin leibie = chanpinDao.selectChanpinById(leibieId);
    Datameaning a = datameaningDao.selectDatameaningById(leibie.getDatameaningId());
    List<Datameaning> all1 = getAllSonPoint(all, a.getId());
    //System.out.println("all1:"+all1);
    int i = 0;
    for (Datameaning al : all1) {
      if (al.getIsparent() == 0 && al.getType() == type) {
        //System.out.println(al);
        i++;
      }
    }
    return i;
  }

  @Override
  public CopyOnWriteArrayList<DataDisplay> getNullDisplayData(Integer chanpinId, Integer rejisterId) {
    CopyOnWriteArrayList<DataDisplay> dataDisplays = new CopyOnWriteArrayList<>();
    List<PhraseData> phraseDatas = new ArrayList<>();
    List<Datameaning> erji = datameaningDao.getAllSons(rejisterId);//获得所有的二级菜单
    List<Datameaning> fathers = new ArrayList<>();//获得所有的三级菜单
    List<Datameaning> datameanings = new ArrayList<>();//获得所有的四级菜单
    for (Datameaning one : erji) {
      if (one.getType() == 1) {
        List<Datameaning> all = datameaningDao.getAllSons(one.getId());
        for (Datameaning all1 : all) {
          fathers.add(all1);
        }
      }
    }
    for (Datameaning one : fathers) {
      List<Datameaning> all = datameaningDao.getAllSons(one.getId());
      for (Datameaning all1 : all) {
        datameanings.add(all1);
      }
    }

    for (int i = 0; i < datameanings.size(); i++) {
      Datameaning datameaning = datameanings.get(i);
      PhraseData phraseData = new PhraseData(null, null, null, null, null, null, null, null);
      phraseData.setId(datameaning.getId());
      phraseData.setName(datameaning.getName());
      phraseData.setData(" ");
      phraseData.setTime(" ");
      phraseData.setZhuangtai(0);
      Integer IsPaint = datameaning.getIsPaint();
      if (IsPaint == null || IsPaint == 1) {
        phraseData.setIsPaint(1);
      } else {
        phraseData.setIsPaint(0);
      }
      //phraseData.setIsPaint(0);
      phraseData.setTime(" ");
      if (datameaning.getIsShown() == null) {
        phraseData.setIsShown(1);
      } else {
        if (datameaning.getIsShown() == 0) {
          phraseData.setIsShown(0);
        } else {
          phraseData.setIsShown(1);
        }
      }
      phraseDatas.add(phraseData);

    }
    for (int i = 0; i < fathers.size(); i++) {//进行displayData的最终整合
      Datameaning father = fathers.get(i);
      DataDisplay dataDisplay = new DataDisplay(null, null);
      dataDisplay.setName(father.getName());
      List<Datameaning> son = datameaningDao.getAllSons(father.getId());
      CopyOnWriteArrayList<SonData> sonDatas = new CopyOnWriteArrayList<>();
      for (int j = 0; j < son.size(); j++) {
        SonData sonData = new SonData(null, null, null, null, null, null, null, null, null);
        for (int k = 0; k < phraseDatas.size(); k++) {
          if (son.get(j).getId().equals(phraseDatas.get(k).getId())) {
            sonData.setId(phraseDatas.get(k).getId());
            sonData.setName(phraseDatas.get(k).getName());
            sonData.setData(phraseDatas.get(k).getData());
            sonData.setTime(phraseDatas.get(k).getTime());
            sonData.setIsRecord(phraseDatas.get(k).getIsRecord());
            sonData.setIsPaint(phraseDatas.get(k).getIsPaint());
            sonData.setZhuangtai(phraseDatas.get(k).getZhuangtai());
            sonData.setIsShown(phraseDatas.get(k).getIsShown());
            sonDatas.add(sonData);
          }
        }
      }
      dataDisplay.setSons(sonDatas);
      dataDisplays.add(dataDisplay);
    }
    return dataDisplays;

  }

  @Override
  public CopyOnWriteArrayList<DataDisplayAdd> Init() {
    CopyOnWriteArrayList<DataDisplayAdd> DataDisplays = new CopyOnWriteArrayList<>();
    List<Chanpin> locations = chanpinDao.getAllSons(0);//获得到所有的区域位置信息
    for (Chanpin location : locations) {
      List<Chanpin> leibies = chanpinDao.getAllSons(location.getId());
      for (Chanpin leibie : leibies) {
        List<Chanpin> chanpins = chanpinDao.getAllSons(leibie.getId());
        for (Chanpin chanpin : chanpins) {
          //寻找输入寄存器
          Datameaning fatherDatameaing = findDatameaningById(leibie.getDatameaningId());
          List<Datameaning> rejisters = getDMAllSons(fatherDatameaing.getId());
          //遍历出输入寄存器并且进行记录入库的工作
          for (Datameaning rejister : rejisters) {
            if (rejister.getType() == 1) {
              CopyOnWriteArrayList<DataDisplay> datadisplays = getNullDisplayData(chanpin.getId(), leibie.getDatameaningId());
              DataDisplayAdd son = new DataDisplayAdd(chanpin.getId(), rejister.getId(), 0, datadisplays);
              //son.setChanpinId(chanpin.getId());
              //son.setRejisterId(rejister.getId());
              //son.setSon(datadisplays);
              DataDisplays.add(son);

            }//此处为判断输入寄存器部分

          }//记录入库的截至部分

        }//产品部分的结束

      }//产品类别结束部分

    }//位置信息的结束部分


    return DataDisplays;
  }

  @Override
  public Map<Integer, Byte> readData(Integer chanpinId, Integer leibieId)
          throws NumberFormatException, UnknownHostException, IOException, PortInUseException {
    Map<Integer, Byte> map = new HashMap<>();
    Datameaning leibie = findDatameaningById(leibieId);
    for (int o = 0; o < leibie.getByteNum() * 2; o++) {
      map.put(o, null);
    }
    //System.out.println("map:"+map);
    byte[] datas = getReturnData(chanpinId, leibieId);
    int k = 0;//循环获取的次数
    while (k < configValue.times && datas == null) {
      datas = getReturnData(chanpinId, leibieId);
      if (datas != null) {
        break;
      } else {
        k++;
      }
    }
    if (datas == null) {
      Object cache=redisUtil.get("Mc:" + chanpinId);
      if(cache!=null){
        //System.out.println("从cache中读取！");
        byte[] cacheBytes=((String)cache).getBytes(CharEncoding.ISO_8859_1);//Mc代表缓存在redis的数据
        datas=cacheBytes;
      }
      else{
        return map;
      }
    }
    else{
      timesCount("M",chanpinId,null);//进行更新缓存状态统计
      if(redisUtil.set("Mc:" + chanpinId, new String(datas,CharEncoding.ISO_8859_1))) {
        Random random = new Random();
        redisUtil.expire("Mc:" + chanpinId, random.nextInt(configValue.lastestTime-configValue.earlistTime)+configValue.earlistTime);
      }
    }
    for (int m = 3; m < datas.length - 2; m++) {
      map.put(m - 3, datas[m]);
    }
        /*if(chanpinId.equals(26)) {
            System.out.println("map: ("+chanpinId+")  " + map);
            for (Map.Entry<Integer, Byte> entry : map.entrySet()) {
                System.out.print(entry.getKey() + "=" + CRC.parseHexToString(entry.getValue()) + " ");
                System.out.println(" ");
                System.out.print(entry.getKey() + "=" + CRC.parseIntToStrings(entry.getValue()& 0xFF) + " ");
            }
            System.out.println(" ");
        }*/
    return map;

  }

  @Override
  public byte[] getReturnData(Integer chanpinId, Integer leibieId)
          throws NumberFormatException, UnknownHostException, IOException, PortInUseException {
    byte[] datas = null;
    Chanpin chanpin = chanpinDao.selectChanpinById(chanpinId);
    Datameaning leibie = findDatameaningById(leibieId);
    byte slaveId = CRC.parseIntToHex(chanpin.getSlaveId());
    byte rejisterNum = CRC.parseIntToHex(leibie.getByteNum());
    byte[] byte1 = {slaveId, 0x04, 0x00, 0x00, 0x00, rejisterNum};
    String crcs = CRC.ReadToCRC(byte1);
    String a1 = crcs.substring(0, 2);
    String a2 = crcs.substring(2);
    byte[] byte2 = {slaveId, 0x04, 0x00, 0x00, 0x00, rejisterNum, CRC.parseStringToHex(a1), CRC.parseStringToHex(a2)};
    int length = leibie.getByteNum() * 2 + 5;
    if (chanpin.getWay() == 1) {//串口读取
      SerialPort serialPort = SerialPortManager.openPort(chanpin.getPortName(), chanpin.getBoto());
      SerialPortManager.sendToPort(serialPort, byte2);
      datas = SerialPortManager.readFromPort(serialPort);
      if (serialPort == null || datas == null || datas.length < length) {
        SerialPortManager.closePort(serialPort);
        return null;//对无效数据进行过滤
      }
      SerialPortManager.closePort(serialPort);
    } else {//串口服务器读取
      Integer connectTime = chanpin.getConnectTime();
      Integer readTime = chanpin.getReadTime();
      if (connectTime == null) {
        connectTime = configValue.connectTime;
      }
      if (readTime == null) {
        readTime = configValue.readTime;
      }
      String ip = chanpin.getAddress();
      Integer port = chanpin.getPort();
      //if (TcpPortManager.getReturnData(CacheManage.getMapSockets().get("i"+ip+"p"+port),chanpin.getAddress(), chanpin.getPort(), byte2, connectTime, readTime) != null)
      Socket socket = getMapSockets().get("i" + ip + "p" + port);
      if (socket == null || socket.isClosed()) {
        try {
          socket = new Socket();
          //System.out.println("重新连接socket");
          socket.connect(new InetSocketAddress(ip, port), connectTime);
          socket.setSoTimeout(readTime);
          socket.setKeepAlive(true);
          getMapSockets().put("i" + ip + "p" + port, socket);
        } catch (UnknownHostException e) {
          socket = getMapSockets().get("i" + ip + "p" + port);
        } catch (SocketTimeoutException e) {
          socket = getMapSockets().get("i" + ip + "p" + port);
        } catch (SocketException e) {
          socket = getMapSockets().get("i" + ip + "p" + port);
        }
      }
      datas = TcpPortManager.getReturnData(socket, chanpin.getAddress(), chanpin.getPort(), byte2, connectTime, readTime);
      if (datas == null || datas.length < length || datas.length > length) {
        return null;
      }
    }
    //开始进行crc校验
		/*for(int i=0;i<datas.length;i++){
			System.out.print(CRC.parseHexToString(datas[i])+" ");
		}
		System.out.println();*/
    byte[] testCrc1 = new byte[datas.length - 2];
    for (int i = 0; i < datas.length - 2; i++) {
      testCrc1[i] = datas[i];
    }
    String crc1 = CRC.ReadToCRC(testCrc1);
    String crc2 = CRC.parseIntToStrings(datas[datas.length - 2] & 0xFF) + CRC.parseIntToStrings(datas[datas.length - 1] & 0xFF);
    if (crc1.equals(crc2)) {
      //开始进行前置数据位的校验
      if(datas[0]==slaveId&&datas[1]==0x04&&datas[2]==CRC.parseIntToHex(leibie.getByteNum()*2)){
        //开始针对部分协议进行优化（例如 ：必须让静电监测仪的温度前一位为0
        if(leibieId.equals(654)){//对静电监测仪进行优化
             if(datas[11]==0x00){
               return datas;
             }
             else{
               return null;
             }
        }
        return datas;
      }
      else{
        return null;
      }
    } else {
      return null;
    }
  }




  @Override
  public List<RecordData> getAllRecordData(Integer chanpinId, Integer datameaningId) {
    List<RecordData> all = new ArrayList<>();
    List<InfoRecord> infoRecords = infoRecordDao.getAll(chanpinId, datameaningId);
    List<TroubleRecord> troubleRecords = troubleRecordDao.getAll(chanpinId, datameaningId);
    List<BaojingRecord> baojingRecords = baojingRecordDao.getAll(chanpinId, datameaningId);
    //List<RecordData> all1=new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for (InfoRecord infoRecord : infoRecords) {
      RecordData recordData = new RecordData(null, null, null, null);
      recordData.setChanpinId(chanpinId);
      recordData.setDatameaningId(datameaningId);
      recordData.setValue(Double.valueOf(Split.getNumber(infoRecord.getValue())));
      try {
        Date date = sdf.parse(infoRecord.getTime());
        recordData.setTime(date);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      all.add(recordData);
    }

    for (TroubleRecord troubleRecord : troubleRecords) {
      RecordData recordData = new RecordData(null, null, null, null);
      recordData.setChanpinId(chanpinId);
      recordData.setDatameaningId(datameaningId);
      recordData.setValue(Double.valueOf(Split.getNumber(troubleRecord.getValue())));
      try {
        Date date = sdf.parse(troubleRecord.getTime());
        recordData.setTime(date);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      all.add(recordData);
    }

    for (BaojingRecord baojingRecord : baojingRecords) {
      RecordData recordData = new RecordData(null, null, null, null);
      recordData.setChanpinId(chanpinId);
      recordData.setDatameaningId(datameaningId);
      recordData.setValue(Double.valueOf(Split.getNumber(baojingRecord.getValue())));
      try {
        Date date = sdf.parse(baojingRecord.getTime());
        recordData.setTime(date);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      all.add(recordData);
    }

    Collections.sort(all, new Comparator<RecordData>() {
      public int compare(RecordData arg0, RecordData arg1) {
        Date hits0 = arg0.getTime();
        Date hits1 = arg1.getTime();
        return hits0.compareTo(hits1);
      }
    });

    return all;
  }


  @Override
  public ChanAndDataShai getShaiRecords(String type, Integer chanpinId, Integer datameaningId, String startDate, String endDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<InfoRecord> infoRecords = infoRecordDao.findAll(chanpinId, datameaningId, startDate, endDate);
    List<TroubleRecord> troubleRecords = troubleRecordDao.findAll(chanpinId, datameaningId, startDate, endDate);
    List<BaojingRecord> baojingRecords = baojingRecordDao.findAll(chanpinId, datameaningId, startDate, endDate);
    //System.out.println("infoRecords:"+infoRecords);
    //System.out.println("troubleRecords:"+troubleRecords);
    //System.out.println("baojingRecords:"+baojingRecords);
    ChanAndDataShai shai = new ChanAndDataShai(null, null, null);
    shai.setDatameaningId(datameaningId);
    shai.setChanpinId(chanpinId);
    List<ChanAndDataShaiMin> sonData = new ArrayList<>();
    List<RecordData> all = new ArrayList<>();//用于存放所有取得的数据
    for (InfoRecord infoRecord : infoRecords) {
      RecordData recordData = new RecordData(null, null, null, null);
      recordData.setChanpinId(chanpinId);
      recordData.setDatameaningId(datameaningId);
      recordData.setValue(Double.valueOf(Split.getNumber(infoRecord.getValue())));
      try {
        Date date = sdf.parse(infoRecord.getTime());
        recordData.setTime(date);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      all.add(recordData);
    }

    for (TroubleRecord troubleRecord : troubleRecords) {
      RecordData recordData = new RecordData(null, null, null, null);
      recordData.setChanpinId(chanpinId);
      recordData.setDatameaningId(datameaningId);
      recordData.setValue(Double.valueOf(Split.getNumber(troubleRecord.getValue())));
      try {
        Date date = sdf.parse(troubleRecord.getTime());
        recordData.setTime(date);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      all.add(recordData);
    }

    for (BaojingRecord baojingRecord : baojingRecords) {
      RecordData recordData = new RecordData(null, null, null, null);
      recordData.setChanpinId(chanpinId);
      recordData.setDatameaningId(datameaningId);
      recordData.setValue(Double.valueOf(Split.getNumber(baojingRecord.getValue())));
      try {
        Date date = sdf.parse(baojingRecord.getTime());
        recordData.setTime(date);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      all.add(recordData);
    }

    Collections.sort(all, new Comparator<RecordData>() {
      public int compare(RecordData arg0, RecordData arg1) {
        Date hits0 = arg0.getTime();
        Date hits1 = arg1.getTime();
        return hits0.compareTo(hits1);
      }
    });
    //System.out.println("all: "+all);
    if (startDate == null || startDate.equals("")) {
      startDate = sdf.format(all.get(0).getTime());
    }
    if (endDate == null || endDate.equals("")) {
      Integer size = all.size();
      endDate = sdf.format(all.get(size - 1).getTime());
    }
    if (all.size() > 0) {
      //开始进行时间的切割(年 月 日）

      switch (type) {
        case "1"://按照年来看
          List<String> li = DateUtil.cutDate("M", startDate, endDate);
          List<String> lis = new ArrayList<>();//存放所有的年份
          for (String l : li) {
            String ll = l.substring(0, 4);
            int a = 1;
            for (String lll : lis) {
              if (ll.equals(lll)) {
                a = 0;
                break;
              }
            }
            if (a == 1) {
              lis.add(ll);
            }
          }
          for (String time : lis) {
            List<Double> recordDatas = new ArrayList<>();
            ChanAndDataShaiMin min = new ChanAndDataShaiMin(null, null, null, null);
            for (RecordData record : all) {
              SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
              String recordTime = sdf1.format(record.getTime());


              if (recordTime.equals(time)) {
                recordDatas.add(record.getValue());
              }
            }
            Double minData = 0.0;
            Double maxData = 0.0;
            Double aveData = 0.0;
            if (recordDatas.size() > 0) {
              minData = Collections.min(recordDatas);
              maxData = Collections.max(recordDatas);
              Double sum = 0.0;
              for (Double re : recordDatas) {
                sum += re;
              }
              aveData = sum / recordDatas.size();
            }
            min.setTime(time);
            min.setAveData(aveData);
            min.setMaxData(maxData);
            min.setMinData(minData);
            sonData.add(min);
          }
          break;
        case "2"://按照月来看
          List<String> list1 = DateUtil.cutDate("M", startDate, endDate);
          List<String> list = new ArrayList<>();
          for (String t : list1) {
            boolean boolea = true;
            for (String lli : list) {
              if (t.equals(lli)) {
                boolea = false;
                break;
              }
            }
            if (boolea) {
              list.add(t);
            }
          }
          for (String time : list) {
            List<Double> recordDatas = new ArrayList<>();
            ChanAndDataShaiMin min = new ChanAndDataShaiMin(null, null, null, null);
            for (RecordData record : all) {
              SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
              String recordTime = sdf1.format(record.getTime());
              if (recordTime.equals(time)) {
                recordDatas.add(record.getValue());
              }
            }
            Double minData = 0.0;
            Double maxData = 0.0;
            Double aveData = 0.0;
            if (recordDatas.size() > 0) {
              minData = Collections.min(recordDatas);
              maxData = Collections.max(recordDatas);
              Double sum = 0.0;
              for (Double re : recordDatas) {
                sum += re;
              }
              aveData = sum / recordDatas.size();
            }
            min.setTime(time);
            min.setAveData(aveData);
            min.setMaxData(maxData);
            min.setMinData(minData);
            sonData.add(min);
          }
          break;
        case "3"://按照日来看
          List<String> listt = DateUtil.cutDate("D", startDate, endDate);
          //	System.out.println("listt:  "+listt);
          List<String> listtt = new ArrayList<>();
          for (String t : listt) {
            boolean boolea = true;
            for (String lli : listtt) {
              if (t.equals(lli)) {
                boolea = false;
                break;
              }
            }
            if (boolea) {
              listtt.add(t);
            }
          }
          //	System.out.println("listt:  "+listtt);
          for (String time : listtt) {
            List<Double> recordDatas = new ArrayList<>();
            ChanAndDataShaiMin min = new ChanAndDataShaiMin(null, null, null, null);
            for (RecordData record : all) {
              SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
              String recordTime = sdf1.format(record.getTime());
              if (recordTime.equals(time)) {
                recordDatas.add(record.getValue());
              }
            }
            Double minData = 0.0;
            Double maxData = 0.0;
            Double aveData = 0.0;
            if (recordDatas.size() > 0) {
              minData = Collections.min(recordDatas);
              maxData = Collections.max(recordDatas);
              Double sum = 0.0;
              for (Double re : recordDatas) {
                sum += re;
              }
              aveData = sum / recordDatas.size();
            }
            min.setTime(time);
            min.setAveData(aveData);
            min.setMaxData(maxData);
            min.setMinData(minData);
            sonData.add(min);
          }
          break;
      }

    }

    shai.setSonData(sonData);
    return shai;
  }

  @Override
  public void timesCount(String type,Integer chanpinId,Integer datameaningId){
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(type.equals("C")){//正确数据更新时间
      //有数据时候附加，无数据的时候直接贴上数据并且设置过期时间
      if(redisUtil.get("Tm:C:"+chanpinId+datameaningId)!=null){
        redisUtil.append("Tm:C:"+chanpinId+datameaningId,df.format(date)+" ");
        //System.out.println("过期时间为："+redisUtil.getExpire("Tm:C:"+chanpinId+datameaningId));
      }
      else{
        if(redisUtil.set("Tm:C:"+chanpinId+datameaningId,df.format(date)+" ")){
          Random random=new Random();
          redisUtil.expire("Tm:C:"+chanpinId+datameaningId,random.nextInt(configValue.dailyLast-configValue.dailyEarly)+configValue.dailyEarly);
        }
      }
    }
    else{//缓存数据更新时间
      if(redisUtil.get("Tm:M:"+chanpinId)!=null){
        redisUtil.append("Tm:M:"+chanpinId,df.format(date)+" ");
        // System.out.println("过期时间为："+redisUtil.getExpire("Tm:M:"+chanpinId));
      }
      else{
        if(redisUtil.set("Tm:M:"+chanpinId,df.format(date)+" ")){
          Random random=new Random();
          redisUtil.expire("Tm:M:"+chanpinId,random.nextInt(configValue.dailyLast-configValue.dailyEarly)+configValue.dailyEarly);
        }
      }
    }

  }

  /*	@Override
      public void mapTimes(Integer chanpinId,boolean boolea) {
          String str=(String) redisUtil.get("map:" + chanpinId);
          if(str==null||str.trim().length()<=0){
              str="0-";
              redisUtil.set("map:" + chanpinId, str);
          }
          else{
              //开始进行判断
              Integer times=Integer.parseInt(str.substring(str.length()-2,str.length()-1));
              if(boolea){//当前为空
                  times+=1;
                  String str1=str.substring(0,str.length()-2);
                  redisUtil.set("map:" + chanpinId, str1+times+"-");
              }
              else{//当前不为空
                  if(!times.equals(0)){
                      redisUtil.set("map:" + chanpinId, str+0+"-");
                  }
              }
          }
      }
  */
  @Override
  public PhraseData getPhraseData(byte[] bt,Integer chanpinId,Datameaning datameaning, PhraseData phraseData) {
    ErrorSet errorSet = errorSetService.findAllByChanpinAndDatameaningId(chanpinId, datameaning.getId());
    // System.out.println("errorSet: "+errorSet);
    if (errorSet != null && errorSet.getIsOpen().equals(1)) {//判断是否进行故障设置处理
      phraseData.setData(errorSet.getValue());
      phraseData.setZhuangtai(0);
      return phraseData;
    }
    //int pattern=datameaning.getPattern();
    //System.out.println("pattern);
    //switch(pattern){
    //进行按照字节解析
    if(datameaning.getPattern().equals(2)) {
      //case 2:
      Integer Bit = datameaning.getBit();//看保留小数点后几位
      DecimalFormat dfs = null;
      if (Bit == null) {
        dfs = new DecimalFormat("######0.00");
      } else if (Bit == 0) {
        dfs = new DecimalFormat("######0");
      } else {
        String ds = "######0.";
        for (int h = 0; h < Bit; h++) {
          ds += "0";
        }
        dfs = new DecimalFormat(ds);
      }
      if (datameaning.getByteNum().equals(1)) {//当只存在一个字节的时候
        Integer data = bt[0] & 0xFF;
        if (datameaning.getIsPositive() != null && datameaning.getIsPositive().equals(1)) {
          data = (int) bt[0];
        }
        Double data11 = 0.0;//存储实际显示量
        if (datameaning.getIsJiexi() != null && datameaning.getIsJiexi().equals(1)) {//需要解析的情况下
          String str = datameaning.getGongshi();
          if (datameaning.getIsHex() != null) {
            if (datameaning.getIsHex() == 1) {
              String data1 = CRC.parseIntToStrings(data);
              data11 = Double.parseDouble(data1);
            }
          }
          str = str.replaceAll("a", data + "");
          try {
            String data1 = Sum.getResult(str);
            data11 = Double.parseDouble(data1);
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {//不需要解析的情况下
          if (datameaning.getIsHex().equals(1)) {//16进制输出
            String data1 = CRC.parseIntToStrings(data);
            try {
              data11 = Double.parseDouble(data1);
            } catch (NumberFormatException e) {
              //System.err.println("无法转换为整型");
              //data11=0.0;
              phraseData.setData("解析出错！");
              phraseData.setZhuangtai(1);
              e.printStackTrace();
              return phraseData;
            }
            if (datameaning.getScale() != null && datameaning.getScale() != 0) {
              data11 = Double.parseDouble(data1) * datameaning.getScale();
            }

          } else {//非十六进制输出
            data11 = (double) data;
            //将16进制转成10进制的数据
            if (datameaning.getScale() != null && datameaning.getScale() != 0) {
              data11 = data11 * datameaning.getScale();
            }
          }
        }
        //data = data11.intValue();
        PanDuan panduan = panDuanDao.findByDataMeaningId(data, datameaning.getId());//先判断数据是否需要判断，此处判断为一个枚举类型
        if (panduan != null && datameaning.getIsPanduan().equals(1)) {//如果存在判断的条件
          String panduanName = panduan.getName();
          phraseData.setData(panduanName);
          if (panduan.getIsError() == 0) {
            phraseData.setZhuangtai(0);
          } else {
            phraseData.setZhuangtai(1);
          }
        } else {//不存在判断的条件,开始是否包含报警判断
          BaojingPanduan baojingPanduan = baojingPanduanDao.selectByChanpinAndDatameaning(chanpinId,datameaning.getId());
          if (baojingPanduan != null && datameaning.getIsBaojingPanduan().equals(1)) {//如果存在报警判断
            if(data11<baojingPanduan.getNormalData()){
              phraseData.setZhuangtai(1);
            }
            else if(data11 >=baojingPanduan.getNormalData() && data11 <= baojingPanduan.getYujingData()){
              phraseData.setZhuangtai(0);
            }
            else if(data11 > baojingPanduan.getYujingData() && data11 < baojingPanduan.getBaojingData()){
              phraseData.setZhuangtai(2);
            }
            else{
              phraseData.setZhuangtai(1);
            }
            if (datameaning.getUnit() != null && datameaning.getUnit() != "")//判断数据是否包含单位
              phraseData.setData(dfs.format(data11) + datameaning.getUnit());
            else
              phraseData.setData(dfs.format(data11));
          } else {//直接进行数据的输出
            if (datameaning.getUnit() != null && datameaning.getUnit() != "")//判断数据是否包含单位
              phraseData.setData(dfs.format(data11) + datameaning.getUnit());
            else
              phraseData.setData(dfs.format(data11));
            phraseData.setZhuangtai(0);
          }
        }
      } else {//需要解析的字节数据不止一个
        int readData = 0;//存储多个字节读取的数据
        if (datameaning.getIsJiexi() == null || datameaning.getIsJiexi().equals(0)) {//数据不需要解析
          String str = "";
          for (int j = 0; j < datameaning.getByteNum(); j++) {//将获得的所有数据从高位到低位进行拼接
            Integer data = bt[j] & 0xFF;
            if (data != null)
              str += CRC.parseIntToStrings(data);
          }
          if (datameaning.getIsPositive() != null && datameaning.getIsPositive().equals(1)) {//假设数据存在正负的判断
            String a = str.substring(0, 2);
            String pos = "";
            if (!a.equals("00")) {
              pos = "-";
            }
            String lefts = str.substring(2);
            Integer te = CRC.parseStringsToHex(lefts);
            readData = te;
            if (pos == "-") {
              readData = -te;
            }
          } else {//数据不存在正负判断
            //System.out.println("str: "+str);
            int data = CRC.parseStringsToHex(str);
            readData = data;
            //System.out.println("data："+data);
          }
        } else {//如果数据需要解析
          String str = datameaning.getGongshi();
          String temp = str;
          int[] a = new int[datameaning.getByteNum()];
          for (int j = 0; j < datameaning.getByteNum(); j++) {
            int data = bt[j] & 0xFF;
            if (datameaning.getIsHex() != null) {
              if (datameaning.getIsHex() == 1) {
                String data1 = CRC.parseIntToStrings(data);
                data = Integer.parseInt(data1);
              }
            }
            a[j - datameaning.getByteAddress()] = data;
            temp = temp.replaceAll("a", data + "");
            temp = temp.replaceAll("b", data + "");
            temp = temp.replaceAll("c", data + "");
            temp = temp.replaceAll("d", data + "");
            str = temp;
          }
          temp = str.replaceAll("a", a[0] + "");
          temp = str.replaceAll("b", a[1] + "");
          if (datameaning.getByteNum() > 2) {
            temp = str.replaceAll("c", a[2] + "");
            temp = str.replaceAll("d", a[3] + "");
          }
          str = temp;
          try {
            String data = Sum.getResult(str);
            readData = Integer.parseInt(data);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        Double data11 = (double) readData;
        if (datameaning.getScale() != null && !datameaning.getScale().equals(0)) {
          data11 = readData * datameaning.getScale();
        }
        //Integer te = data11.intValue();
        PanDuan panduan = panDuanDao.findByDataMeaningId(data11.intValue(), datameaning.getId());//先判断数据是否需要判断
        if (panduan != null && datameaning.getIsPanduan() == 1) {//如果存在判断的条件
          String panduanName = panduan.getName();
          phraseData.setData(panduanName);
          if (panduan.getIsError() == 0) {
            phraseData.setZhuangtai(0);
          } else {
            phraseData.setZhuangtai(1);
          }
        } else {//不存在判断的条件,开始是否包含报警判断
          BaojingPanduan baojingPanduan = baojingPanduanDao.selectByChanpinAndDatameaning(chanpinId,datameaning.getId());
          if (baojingPanduan != null && datameaning.getIsBaojingPanduan().equals(1)) {//如果存在报警判断
            if(data11<baojingPanduan.getNormalData()){
              phraseData.setZhuangtai(1);
            }
            else if(data11 >=baojingPanduan.getNormalData() && data11 <= baojingPanduan.getYujingData()){
              phraseData.setZhuangtai(0);
            }
            else if(data11 > baojingPanduan.getYujingData() && data11 < baojingPanduan.getBaojingData()){
              phraseData.setZhuangtai(2);
            }
            else{
              phraseData.setZhuangtai(1);
            }
            if (datameaning.getUnit() != null && datameaning.getUnit() != "")//判断数据是否包含单位
              phraseData.setData(dfs.format(data11) + datameaning.getUnit());
            else
              phraseData.setData(dfs.format(data11));
          } else {//直接进行数据的输出
            if (datameaning.getUnit() != null && datameaning.getUnit() != "")//判断数据是否包含单位
              phraseData.setData(dfs.format(data11) + datameaning.getUnit());
            else
              phraseData.setData(dfs.format(data11));
            phraseData.setZhuangtai(0);
          }
        }
      }
    }
    //进行位解析
    else{
      int data = bt[0]&0xFF;
      //System.out.println("daid:"+datameaning.getId()+" bit:"+datameaning.getBitAddress());
      int pos = datameaning.getBitAddress();
      String result = Integer.toBinaryString(data);
      if (result.length() == 1) {
        result += "000";
      }
      if (result.length() == 2) {
        result += "00";
      }
      if (result.length() == 3) {
        result += "0";
      }
      String dataShow = CRC.reverse(result);
      char is = dataShow.charAt(pos - 1);
      String dis = "";
      if (is == '0') {
        dis = datameaning.getZero();
      } else {
        dis = datameaning.getFirst();
      }
      phraseData.setData(dis);
      if (dis.indexOf("正常") != -1) {
        phraseData.setZhuangtai(0);

      } else {
        phraseData.setZhuangtai(1);

      }
    }
    return phraseData;
  }


}
