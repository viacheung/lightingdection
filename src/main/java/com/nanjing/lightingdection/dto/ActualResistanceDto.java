package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActualResistanceDto {
  private Integer id;
  private Integer chanpinId;
  private String chanpinName;
  private String locationName;
  private String leibieName;
  private String value;
  private String time;
}
