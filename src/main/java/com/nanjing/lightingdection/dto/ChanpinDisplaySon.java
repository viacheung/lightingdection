package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChanpinDisplaySon {
  private Integer id;
  private String installation;
  private Integer slaveId;
  private Integer zhuangtai;
  private String time;
}
