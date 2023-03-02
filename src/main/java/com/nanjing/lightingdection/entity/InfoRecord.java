package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoRecord {
  private Integer id;
  private Integer chanpinId;
  private Integer datameaningId;
  private String name;
  private String value;
  private String time;
}
