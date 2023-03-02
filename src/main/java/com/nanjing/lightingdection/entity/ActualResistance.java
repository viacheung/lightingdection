package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActualResistance {
  private Integer id;
  private Integer chanpinId;
  private String value;
  private String time;
}
