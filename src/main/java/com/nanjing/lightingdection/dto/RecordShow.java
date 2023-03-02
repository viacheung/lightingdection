package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordShow {
  private Integer chanpinId;
  private List<RecordShow2> sons;
}
