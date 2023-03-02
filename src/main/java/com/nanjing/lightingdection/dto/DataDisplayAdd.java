package com.nanjing.lightingdection.dto;

import java.util.concurrent.CopyOnWriteArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataDisplayAdd {
  private Integer chanpinId;
  private Integer rejisterId;
  private Integer isConnect; // 判断设备的通讯状态
  private CopyOnWriteArrayList<DataDisplay> son;
}
