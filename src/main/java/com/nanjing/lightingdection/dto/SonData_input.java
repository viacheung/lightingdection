package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SonData_input {
  private String name;
  private Integer isInput; // 1代表有输入，0代表无
}
