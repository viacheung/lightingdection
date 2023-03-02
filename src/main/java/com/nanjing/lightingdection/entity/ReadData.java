package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadData { // 批量读取数据信息
  private Integer id;
  private Integer datameaningId;
  private Integer startRejister;
  private Integer rejisterNum;
}
