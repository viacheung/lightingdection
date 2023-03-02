package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChanAndDataShai { // 用于按年月日筛选的一级
  private Integer chanpinId;
  private Integer datameaningId;
  private List<ChanAndDataShaiMin> sonData;
}
