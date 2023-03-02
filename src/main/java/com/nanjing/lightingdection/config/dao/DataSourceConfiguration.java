package com.nanjing.lightingdection.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 数据库配置类 */
@Configuration
public class DataSourceConfiguration {

  @Value("${jdbc.driver}")
  private String jdbcDriver;

  @Value("${jdbc.url}")
  private String jdbcUrl;

  @Value("${jdbc.username}")
  private String jdbcUsername;

  @Value("${jdbc.password}")
  private String jdbcPassword;

  @Bean(name = "dataSouce")
  public ComboPooledDataSource createDataSouce() throws PropertyVetoException {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setDriverClass(jdbcDriver);
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.setUser(jdbcUsername);
    dataSource.setPassword(jdbcPassword);
    // 关闭连接后不自动commit
    dataSource.setAutoCommitOnClose(false);
    dataSource.setTestConnectionOnCheckin(true);
    dataSource.setTestConnectionOnCheckout(false);
    dataSource.setIdleConnectionTestPeriod(30);
    return dataSource;
  }
}
