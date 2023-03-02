package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.TroubleRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TroubleRecordTest {

  @Autowired private TroubleRecordDao troubleRecordDao;

  @Test
  public void Test() {
    TroubleRecord troubleRecord = new TroubleRecord(null, 1, 1, "121", "12", "12", "09 08 09");
    int a = troubleRecordDao.insertTroubleRecord(troubleRecord);
  }
}
