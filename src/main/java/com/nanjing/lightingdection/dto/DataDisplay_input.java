package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataDisplay_input {
  private String name;
  private List<SonData_input> sons;
}
