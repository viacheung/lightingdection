package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChanpinFile {
  private Integer id;
  private String chanpinId;
  private String chanpinName;
  private String url;
}
