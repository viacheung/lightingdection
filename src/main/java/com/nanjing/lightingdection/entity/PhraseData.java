package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhraseData {
  private Integer id;
  private String name;
  private Object Data;
  private Integer zhuangtai;
  private Integer isShown;
  private Integer isRecord;
  private Integer isPaint;
  private String time;
}
