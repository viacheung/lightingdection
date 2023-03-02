package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordGet3 {
  private Integer id;
  private String name;
  private List<RecordGet4> datameanings;
}
