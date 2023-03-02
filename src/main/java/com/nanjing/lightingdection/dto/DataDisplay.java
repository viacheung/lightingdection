package com.nanjing.lightingdection.dto;

import java.util.concurrent.CopyOnWriteArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataDisplay {
  private String name;
  private CopyOnWriteArrayList<SonData> sons;
}
