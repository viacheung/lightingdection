package com.nanjing.lightingdection.timer;

import com.nanjing.lightingdection.cache.CacheManage;
import com.nanjing.lightingdection.config.ConfigValue;
import com.nanjing.lightingdection.dto.DataDisplay;
import com.nanjing.lightingdection.dto.DataDisplayAdd;
import com.nanjing.lightingdection.dto.SonData;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.service.BaojingRecordService;
import com.nanjing.lightingdection.service.ChanpinService;
import com.nanjing.lightingdection.service.DatameaningService;
import com.nanjing.lightingdection.service.TroubleRecordService;
import com.nanjing.lightingdection.tools.SolveData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;

/*
 *
 * 从串口读取数据的线程*/
public class ReadTimer {
  private static ScheduledExecutorService service;
  private DatameaningService datameaningService;
  private ChanpinService chanpinService;
  private BaojingRecordService baojingRecordService;
  private TroubleRecordService troubleRecordService;
  private ConfigValue configValue;

  public ReadTimer(
      DatameaningService datameaningService,
      ChanpinService chanpinService,
      BaojingRecordService baojingRecordService,
      TroubleRecordService troubleRecordService,
      ConfigValue configValue) {
    this.chanpinService = chanpinService;
    this.datameaningService = datameaningService;
    this.baojingRecordService = baojingRecordService;
    this.configValue = configValue;
    this.troubleRecordService = troubleRecordService;
  }

  public void begin() {
    Runnable runnable =
        new Runnable() {
          @SneakyThrows
          @Override
          public void run() {
            // this.setName();
            System.out.println("执行解析和读取线程！");
            try {
              List<Chanpin> locations = chanpinService.getAllSons(0); // 获得到所有的区域位置信息
              // CacheManage cache=CacheManage.getInstance();
              for (Chanpin location : locations) {
                List<Chanpin> leibies = chanpinService.getAllSons(location.getId());
                Map<String, Object> inputSolve = new HashMap<>();
                for (Chanpin leibie : leibies) { // 解方程针对了区域内的一种类型的产品
                  // 对产品类别进行是否需要解方程进行判断
                  if (leibie.getWay() == null
                      || (leibie.getWay() != null && !leibie.getWay().equals(1))) {
                    List<Chanpin> chanpins = chanpinService.getAllSons(leibie.getId());
                    int count1 = 0;
                    int count2 = 0;
                    for (Chanpin chanpin : chanpins) {
                      // 寻找输入寄存器
                      Datameaning fatherDatameaing =
                          datameaningService.findDatameaningById(leibie.getDatameaningId());
                      List<Datameaning> rejisters =
                          datameaningService.getDMAllSons(fatherDatameaing.getId());
                      // 遍历出输入寄存器并且进行记录入库的工作
                      for (Datameaning rejister : rejisters) {
                        if (rejister.getType() == 1) {
                          CopyOnWriteArrayList<DataDisplay> datadisplays = null;
                          Map<Integer, Byte> map =
                              datameaningService.readData(
                                  chanpin.getId(), leibie.getDatameaningId());
                          int isConnect = 0;
                          for (Map.Entry<Integer, Byte> entry : map.entrySet()) {
                            if (entry.getValue() != null) {
                              isConnect = 1;
                              break;
                            }
                          }
                          // System.out.println(map);
                          datadisplays =
                              datameaningService.getDisplayData(
                                  chanpin.getId(), leibie.getDatameaningId(), map);
                          if (datadisplays != null) {
                            // System.out.println("解析出来的数据：
                            // "+datadisplays.get(0).getSons().get(0).getData());
                            DataDisplayAdd son =
                                new DataDisplayAdd(
                                    chanpin.getId(), rejister.getId(), 0, datadisplays);
                            for (int m = 0; m < CacheManage.getDataDisplays().size(); m++) {
                              if (CacheManage.getDataDisplays()
                                      .get(m)
                                      .getChanpinId()
                                      .equals(son.getChanpinId())
                                  && CacheManage.getDataDisplays()
                                      .get(m)
                                      .getRejisterId()
                                      .equals(son.getRejisterId())) {
                                DataDisplayAdd add =
                                    new DataDisplayAdd(
                                        son.getChanpinId(),
                                        son.getRejisterId(),
                                        isConnect,
                                        son.getSon());
                                CacheManage.getDataDisplays().set(m, add);
                              }
                            }
                            for (DataDisplay datadisplay : datadisplays) { // 将预警以及故障记录移动到解析部分
                              List<SonData> sonDatas = datadisplay.getSons();
                              // 遍历解析出来的数据
                              for (SonData sonData : sonDatas) {
                                if (sonData.getZhuangtai() != 0) {
                                  if (sonData.getZhuangtai() == 1 && sonData.getIsShown() != 0) {
                                    count1++;
                                  }
                                  if (sonData.getZhuangtai() == 2 && sonData.getIsShown() != 0) {
                                    count2++;
                                  }
                                }
                              }
                            }
                          }
                        }
                      } // 记录入库的截至部分
                      if (count1 > 0) {
                        chanpin.setZhuangtai(1);
                        chanpinService.updateChanpinById(chanpin);
                      } else if (count1 == 0 && count2 > 0) {
                        chanpin.setZhuangtai(2);
                        chanpinService.updateChanpinById(chanpin);
                      } else {
                        chanpin.setZhuangtai(0);
                        chanpinService.updateChanpinById(chanpin);
                      }
                    } // 产品部分的结束
                  } else { // 该产品类别需要解方程
                    // 开始

                    List<Chanpin> chanpins = chanpinService.getAllSons(leibie.getId());
                    Map<String, Object> iniDatas = new HashMap<>(); // 方程的输入
                    String danwei = "";
                    int weishu = 0;
                    // 整合一个产品读取数据的合集
                    Map<Integer, Map<Integer, Byte>> readMaps = new HashMap<>();
                    for (Chanpin chanpin : chanpins) {
                      // 寻找输入寄存器
                      Datameaning fatherDatameaing =
                          datameaningService.findDatameaningById(leibie.getDatameaningId());
                      List<Datameaning> rejisters =
                          datameaningService.getDMAllSons(fatherDatameaing.getId());
                      // 遍历出输入寄存器并且进行记录入库的工作
                      for (Datameaning rejister : rejisters) {
                        if (rejister.getType() == 1) {
                          Map<Integer, Byte> map =
                              datameaningService.readData(
                                  chanpin.getId(), leibie.getDatameaningId());

                          CopyOnWriteArrayList<DataDisplay> datadisplays = null;
                          readMaps.put(chanpin.getId(), map);
                          datadisplays =
                              datameaningService.getDisplayData(
                                  chanpin.getId(), leibie.getDatameaningId(), map);
                          if (datadisplays != null) {
                            for (DataDisplay datadisplay : datadisplays) { // 将预警以及故障记录移动到解析部分
                              List<SonData> sonDatas = datadisplay.getSons();
                              // 遍历解析出来的数据
                              for (SonData sonData : sonDatas) {
                                Datameaning dataSon =
                                    datameaningService.findDatameaningById(sonData.getId());
                                if (dataSon.getIsSlove() != null
                                    && dataSon.getIsSlove().equals(1)) {
                                  // System.out.println("datason:"+dataSon);
                                  danwei = dataSon.getUnit();
                                  weishu = dataSon.getBit();
                                  iniDatas.put(
                                      chanpin.getId() + "" + sonData.getId(),
                                      sonData.getData() + "");
                                }
                              }
                            }
                          }
                        }
                      } // 记录入库的截至部分
                    } // 产品部分的结束,之后开始解方程
                    Map<String, Object> solveDatas = SolveData.solve(iniDatas, danwei, weishu);
                    // 开始将解方程得出的结果安置到结果里面去
                    for (Chanpin chanpin : chanpins) {
                      // 寻找输入寄存器
                      Datameaning fatherDatameaing =
                          datameaningService.findDatameaningById(leibie.getDatameaningId());
                      List<Datameaning> rejisters =
                          datameaningService.getDMAllSons(fatherDatameaing.getId());
                      int count1 = 0;
                      int count2 = 0;
                      // 遍历出输入寄存器并且进行记录入库的工作
                      for (Datameaning rejister : rejisters) {
                        if (rejister.getType() == 1) {
                          CopyOnWriteArrayList<DataDisplay> datadisplays = null;
                          Map<Integer, Byte> map = null;
                          for (Map.Entry<Integer, Map<Integer, Byte>> entry : readMaps.entrySet()) {
                            if (entry.getKey().equals(chanpin.getId())) {
                              map = entry.getValue();
                            }
                          }
                          int isConnect = 0;
                          for (Map.Entry<Integer, Byte> entry : map.entrySet()) {
                            if (entry.getValue() != null) {
                              isConnect = 1;
                              break;
                            }
                          }
                          datadisplays =
                              datameaningService.getDisplayData(
                                  chanpin.getId(), leibie.getDatameaningId(), map);
                          if (datadisplays != null) {
                            // 开始设置解方程出来的数据以及预警报警相关
                            for (DataDisplay datadisplay : datadisplays) { // 将预警以及故障记录移动到解析部分
                              // System.out.println("改变前的datadisplay:"+datadisplay);
                              CopyOnWriteArrayList<SonData> sonDatas = datadisplay.getSons();
                              // 遍历解析出来的数据
                              for (SonData sonData : sonDatas) {
                                Datameaning dataSon =
                                    datameaningService.findDatameaningById(sonData.getId());
                                if (sonData.getZhuangtai() != 0) {
                                  if (sonData.getZhuangtai() == 1 && sonData.getIsShown() != 0) {
                                    count1++;
                                  }
                                  if (sonData.getZhuangtai() == 2 && sonData.getIsShown() != 0) {
                                    count2++;
                                  }
                                }
                                if (dataSon.getIsSlove() != null
                                    && dataSon.getIsSlove().equals(1)) {
                                  for (Map.Entry<String, Object> entry : solveDatas.entrySet()) {
                                    if (entry
                                        .getKey()
                                        .equals(chanpin.getId() + "" + sonData.getId())) {
                                      // System.out.println("改变前的solveData")
                                      sonData.setSolveData(entry.getValue());
                                      break;
                                    }
                                  }
                                  // danwei=dataSon.getUnit();
                                  // weishu=dataSon.getBit();
                                  // iniDatas.put(chanpin.getId()+""+sonData.getId(),sonData.getData());
                                }
                              }
                              // 更新
                              datadisplay.setSons(sonDatas);
                              // System.out.println("改变后的datadisplay:"+datadisplay);
                            }
                            DataDisplayAdd son =
                                new DataDisplayAdd(
                                    chanpin.getId(), rejister.getId(), 0, datadisplays);
                            for (int m = 0; m < CacheManage.getDataDisplays().size(); m++) {
                              if (CacheManage.getDataDisplays()
                                      .get(m)
                                      .getChanpinId()
                                      .equals(son.getChanpinId())
                                  && CacheManage.getDataDisplays()
                                      .get(m)
                                      .getRejisterId()
                                      .equals(son.getRejisterId())) {
                                DataDisplayAdd add =
                                    new DataDisplayAdd(
                                        son.getChanpinId(),
                                        son.getRejisterId(),
                                        isConnect,
                                        son.getSon());
                                CacheManage.getDataDisplays().set(m, add);
                              }
                            }
                          }
                        }
                      } // 记录入库的截至部分

                      if (count1 > 0) {
                        chanpin.setZhuangtai(1);
                        chanpinService.updateChanpinById(chanpin);
                      } else if (count1 == 0 && count2 > 0) {
                        chanpin.setZhuangtai(2);
                        chanpinService.updateChanpinById(chanpin);
                      } else {
                        chanpin.setZhuangtai(0);
                        chanpinService.updateChanpinById(chanpin);
                      }
                    }
                  }
                } // 产品类别结束部分
              } // 位置信息的结束部分
            } catch (Exception e) {
              System.out.println("线程的异常报错为: " + e);
              e.printStackTrace();
            }
          } // run函数的结束
        };
    service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(runnable, 0, configValue.threadPoolTime, TimeUnit.SECONDS);
  }

  public void end() {
    service.shutdown();
    try {
      if (!service.awaitTermination(120, TimeUnit.SECONDS)) {
        // 超时的时候向线程池中所有的线程发出中断(interrupted)。
        service.shutdownNow();
      }
      System.out.println("AwaitTermination Finished");
    } catch (InterruptedException ignore) {
      service.shutdownNow();
    }
  }
}
