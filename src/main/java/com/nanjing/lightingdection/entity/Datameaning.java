package com.nanjing.lightingdection.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Datameaning implements Serializable {
  private Integer id;
  private String name;
  private Integer type;
  private Integer functionId;
  private Integer byteNum;
  private Integer byteAddress;
  private Integer bitAddress;
  private Integer isInput;
  private String functionCode;
  private Integer isHex;
  private Double scale;
  private Integer bit;
  private String unit;
  private Integer isPositive;
  private Integer isJiexi;
  private String gongshi;
  private Integer isPanduan;
  private Integer isSlove;
  private Integer isBaojingPanduan;
  private Integer isShown;
  private Integer isRecord;
  private Integer pattern;
  private String zero;
  private String first;
  private Integer isPaint;
  private Integer pid;
  private Integer isparent;

  public Datameaning() {}
}
