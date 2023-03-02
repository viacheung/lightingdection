package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordGet1 {
  private Integer id;
  private String name;
  private List<RecordGet2> leibies;
}
