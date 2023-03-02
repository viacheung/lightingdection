package com.nanjing.lightingdection.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {

  private Integer user_id;
  private String user_code;

  private String user_name;

  private String user_pwd;

  private String user_company;
}
