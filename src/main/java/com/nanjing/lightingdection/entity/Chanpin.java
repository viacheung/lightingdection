package com.nanjing.lightingdection.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Chanpin implements Serializable {
  private Integer id;
  private String name;
  private String location;
  private Integer zhuangtai;
  private String model; // 产品型号
  private String installation; // 安装位置
  private Integer way;
  private String address;
  private Integer port;
  private String portName;
  private Integer boto;
  private String boxId;
  private String comment;
  private Integer slaveId;
  private Integer datameaningId;
  private Integer connectTime;
  private Integer readTime;
  private Integer pid;
  private Integer isparent;

  public Chanpin() {}
}
