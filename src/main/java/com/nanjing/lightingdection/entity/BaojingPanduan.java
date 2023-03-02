package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaojingPanduan {
  private Integer id;
  private Integer chanpinId;
  private Integer datameaningId;
  private Double normalData;
  private Double yujingData;
  private Double baojingData;
}
