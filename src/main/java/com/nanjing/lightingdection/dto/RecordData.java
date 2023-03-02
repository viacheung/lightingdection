package com.nanjing.lightingdection.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordData { // 所有记录数据的公共项
  private Integer chanpinId;
  private Integer datameaningId;
  private Double value;

  private Date time;
}
