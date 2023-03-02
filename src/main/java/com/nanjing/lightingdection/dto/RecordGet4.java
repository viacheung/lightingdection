package com.nanjing.lightingdection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordGet4 {
  private Integer id;
  private String name;

  @Override
  public String toString() {
    return "RecordGet4 [id=" + id + ", name=" + name + "]";
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
