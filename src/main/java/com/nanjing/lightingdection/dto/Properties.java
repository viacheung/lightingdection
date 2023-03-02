package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Properties {
  private Integer id;
  private String name;
  private Integer value;
  private Integer minValue;
  private Integer maxValue;
  private Integer state;
}
