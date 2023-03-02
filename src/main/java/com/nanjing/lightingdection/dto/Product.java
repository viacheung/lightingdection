package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
  private Integer id;
  private String name;
  private Integer state;
  private Integer way;
  private String ipAddress;
  private Integer ipPort;
  private String portName;
  private Integer slaveId;
  private List<Properties> propertity;
}
