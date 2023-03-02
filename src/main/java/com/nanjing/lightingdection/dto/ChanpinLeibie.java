package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChanpinLeibie {
  private Integer id;
  private String name;
  private String location;
  private String model; // 型号
  private Integer chanpinNum;
  private Integer errorNum;
}
