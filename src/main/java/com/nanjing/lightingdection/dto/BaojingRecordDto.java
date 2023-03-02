package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaojingRecordDto {
  private Integer id;
  private String locationName;
  private String leibieName;
  private Integer chanpinId;
  private String chanpinName;
  private Integer datameaningId;
  // private String datameaningName;
  private String name;
  private String value;
  private String time;
}
