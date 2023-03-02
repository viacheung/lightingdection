package com.nanjing.lightingdection.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordShow2 {
  private Integer datameaningId;
  private Integer isExistYujing;
  private Integer isExistBaojing;
  private List<RecordShow3> sons;
}
