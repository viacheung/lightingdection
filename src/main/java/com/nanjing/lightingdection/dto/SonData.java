package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SonData {
  private Integer id;
  private String name;
  private Object data;
  private Integer zhuangtai;
  private String time;
  private Integer isShown;
  private Integer isRecord;
  private Integer isPaint;
  private Object solveData;
}
