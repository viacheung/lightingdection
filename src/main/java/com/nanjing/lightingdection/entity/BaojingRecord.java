package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaojingRecord {
  private Integer id;
  private Integer chanpinId;
  private Integer datameaningId;
  private String name;
  private String value;
  private String time;
  private String primData; // 记录原始数据
}
