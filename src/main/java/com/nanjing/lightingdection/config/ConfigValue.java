package com.nanjing.lightingdection.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigValue {
  @Value("${demo.times}")
  public Integer times;

  @Value("${demo.threadPoolTime}")
  public Integer threadPoolTime;

  @Value("${demo.earlistTime}")
  public Integer earlistTime;

  @Value("${demo.lastestTime}")
  public Integer lastestTime;

  @Value("${demo.connectTime}")
  public Integer connectTime;

  @Value("${demo.readTime}")
  public Integer readTime;

  @Value("${demo.dailyEarly}")
  public Integer dailyEarly;
  @Value("${demo.dailyLast}")
  public Integer dailyLast;

  @Value("${demo.earlyCorrectTime}")
  public Integer earlyCorrectTime;

  @Value("${demo.lastCorrectTime}")
  public Integer lastCorrectTime;
}
