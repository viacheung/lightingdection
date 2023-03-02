package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TempData {
  private Integer id;
  private Integer chanpinId;
  private Integer datameaningId;
  private Object value;
  private String time;
  private Integer count;
}
