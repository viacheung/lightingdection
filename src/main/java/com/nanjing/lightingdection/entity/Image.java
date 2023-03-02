package com.nanjing.lightingdection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image {
  private Integer id;
  private String name;
  private String url;
  private String dots;
  private Double dotsize;
}
