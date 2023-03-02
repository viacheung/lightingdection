package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {
  private Integer id;
  private String companyname;
  private String jiekou;
  private String xieyi;
  private String name;
  private String address;
  private String internet;
  private String phone;
  private String url;
}
