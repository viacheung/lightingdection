package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordGet2 {
  private Integer id;
  private String name;
  private List<RecordGet3> chanpins;
}
