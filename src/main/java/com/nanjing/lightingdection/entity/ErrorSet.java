package com.nanjing.lightingdection.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorSet implements Serializable {
  private Integer id;
  private Integer chanpinId;
  private Integer datameaningId;
  private Integer isOpen;
  private String value;
  private Double minValue;
  private Double maxValue;
  private String unit;
  public ErrorSet() {}
}
