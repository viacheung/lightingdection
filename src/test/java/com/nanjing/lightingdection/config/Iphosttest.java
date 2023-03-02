package com.nanjing.lightingdection.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Iphosttest {
  @Autowired private ConfigValue configValue;

  @Test
  public void test1() {

    // <Iphost> list=configValue.list;
    System.out.println(configValue.times);
  }
}
