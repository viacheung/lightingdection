package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChanpinDisplay {
  private Integer id;
  private String model;
  private List<ChanpinDisplaySon> son;
}
