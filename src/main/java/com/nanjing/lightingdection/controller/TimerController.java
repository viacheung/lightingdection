package com.nanjing.lightingdection.controller;

import com.nanjing.lightingdection.cache.CacheManage;
import com.nanjing.lightingdection.dto.DataDisplay;
import com.nanjing.lightingdection.dto.DataDisplayAdd;
import com.nanjing.lightingdection.dto.SonData;
import com.nanjing.lightingdection.entity.ActualResistance;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.entity.InfoRecord;
import com.nanjing.lightingdection.service.ActualResistanceService;
import com.nanjing.lightingdection.service.ChanpinService;
import com.nanjing.lightingdection.service.DatameaningService;
import com.nanjing.lightingdection.service.InfoRecordService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TimerController {
  @Autowired private ChanpinService chanpinService;
  @Autowired private DatameaningService datameaningService;
  @Autowired private InfoRecordService infoRecordService;
  @Autowired private ActualResistanceService actualResistanceService;

  @Scheduled(cron = "0 0 * * *  ?") // 设定定时时间,每小时一次
  public void startInfomatonRecord() {
    // System.out.println("执行一次读取操作！");
    List<DataDisplayAdd> all = null; // 全部所有的信息
    //	CacheManage cache=CacheManage.getInstance();
    try {
      all = CacheManage.getDataDisplays();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // System.out.println("执行定时操作！");
    List<Chanpin> locations = chanpinService.getAllSons(0); // 获得到所有的区域位置信息
    for (Chanpin location : locations) {
      List<Chanpin> leibies = chanpinService.getAllSons(location.getId()); // 获得所有的类别
      for (Chanpin leibie : leibies) {
        List<Chanpin> chanpins = chanpinService.getAllSons(leibie.getId());
        for (Chanpin chanpin : chanpins) { // 获得所有的产品
          // 寻找输入寄存器
          Datameaning fatherDatameaing =
              datameaningService.findDatameaningById(leibie.getDatameaningId());
          List<Datameaning> rejisters = datameaningService.getDMAllSons(fatherDatameaing.getId());
          // 遍历出输入寄存器并且进行记录入库的工作
          for (Datameaning rejister : rejisters) {
            if (rejister.getType() == 1) { // 获取到所有的需要记录进数据库的相关解析信息
              // System.out.println(rejister.getId());
              List<DataDisplay> list = null;
              for (DataDisplayAdd data : all) {
                if (data.getChanpinId().equals(chanpin.getId())
                    && data.getRejisterId().equals(rejister.getId())) {
                  list = data.getSon();
                  break;
                }
              }
              for (DataDisplay dis : list) {
                List<SonData> son = dis.getSons();
                for (SonData data : son) {
                  // System.out.println(data.getId());
                  if (data.getIsRecord() != null
                      && data.getIsRecord() == 1
                      && !data.getData().equals("未读取到数据!")) {
                    // System.out.println(data.getId()+" "+data.getName()+" "+data.getData());
                    // 未读取到数据!
                    // System.out.println("执行一次！");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date a = new Date();
                    InfoRecord infoRecord =
                        new InfoRecord(
                            null,
                            chanpin.getId(),
                            data.getId(),
                            data.getName(),
                            data.getData() + "",
                            sdf.format(a));
                    infoRecordService.insertInfoRecord(infoRecord);
                    if (data.getId().equals(641)) {
                      ActualResistance actualResistance =
                          new ActualResistance(null, null, null, null);
                      actualResistance.setChanpinId(chanpin.getId());
                      actualResistance.setValue(data.getSolveData() + "");
                      actualResistance.setTime(sdf.format(a));
                      actualResistanceService.insertActualResistance(actualResistance);
                    }
                  }
                  /* if(data.getId().equals(641) && data.getIsRecord() != null && data.getIsRecord() == 1 && !data.getData().equals("未读取到数据!")) {
                  //System.out.println(data.getId()+" "+data.getName()+" "+data.getData());
                  //未读取到数据!
                  //System.out.println("执行一次！");
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  Date a = new Date();
                  InfoRecord infoRecord = new InfoRecord(null, null, null, null, null, null);
                  infoRecord.setChanpinId(chanpin.getId());
                  infoRecord.setDatameaningId(data.getId());
                  infoRecord.setName(data.getName());
                  infoRecord.setValue(data.getData() + "");
                  infoRecord.setTime(sdf.format(a));
                  infoRecordService.insertInfoRecord(infoRecord);*/

                  // 真实阻值记录（看需求再开启）
                  /*  ActualResistance actualResistance = new ActualResistance(null, null, null, null);
                  for (Map.Entry<Integer, Double> entry : CacheManage.getSolveData().entrySet()) {
                      if (entry.getKey().equals(chanpin.getId())) {
                          DecimalFormat dff = new DecimalFormat("######0.00");
                          String str = dff.format(entry.getValue());
                          actualResistance.setValue(Double.parseDouble(str) + "Ω");
                          break;
                      }
                  }
                  actualResistance.setChanpinId(chanpin.getId());
                  //actualResistance.setValue(data.getSolveData()+"Ω");
                  actualResistance.setTime(sdf.format(a));
                  actualResistanceService.insertActualResistance(actualResistance);*/
                  // }
                }
              }
            }
          }
        }
      }
    }

    /*	Datameaning info=datameaningService.findDatameaningById(sonData.getId());
     	if(info.getIsRecord()!=null&&info.getIsRecord()==1){
     		InfoRecord old=infoRecordService.findNewestRecord(chanpin.getId(), sonData.getId());
     		if(((old==null)||(old!=null&&!old.getValue().equals(sonData.getData()+"")))&&(!sonData.getData().equals("未读取到数据！"))) {
    	InfoRecord infoRecord=new InfoRecord();
    	infoRecord.setChanpinId(chanpin.getId());
    	infoRecord.setDatameaningId(sonData.getId());
    	infoRecord.setName(sonData.getName());
    	infoRecord.setValue(sonData.getData()+"");
    	infoRecord.setTime(sonData.getTime());
    	infoRecordService.insertInfoRecord(infoRecord);
     		}
    }

    System.out.println("执行定时操作！");*/
  }
}
