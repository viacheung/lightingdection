package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PanDuan {
  private int data;
  private String name;
  private Integer datameaningId;
  private Integer isError;
}
