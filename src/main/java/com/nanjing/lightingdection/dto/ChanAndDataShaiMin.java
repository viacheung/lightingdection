package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChanAndDataShaiMin { // 用于按年月日筛选的二级
  private Double minData;
  private Double aveData;
  private Double maxData;
  private String time;
}
