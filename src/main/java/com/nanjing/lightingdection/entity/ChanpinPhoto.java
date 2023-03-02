package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChanpinPhoto {
  private Integer id;
  private String location;
  private String leibie;
  private Integer chanpinId;
  private String name;
  private String url;
}
