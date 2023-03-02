package com.nanjing.lightingdection.listener;

import com.nanjing.lightingdection.cache.CacheManage;
import com.nanjing.lightingdection.config.ConfigValue;
import com.nanjing.lightingdection.entity.ErrorSet;
import com.nanjing.lightingdection.service.*;
import com.nanjing.lightingdection.timer.ReadTimer;
import com.nanjing.lightingdection.utils.RedisUtil;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebListener
public class ThreadStartListener implements ServletContextListener {
  // private DatameaningService datameaningService;

  private ReadTimer readTimer;
  private DatameaningService datameaningService;
  private ChanpinService chanpinService;
  private BaojingRecordService baojingRecordService;
  private TroubleRecordService troubleRecordService;
  private ErrorSetService errorSetService;
  private ConfigValue configValue;
  private RedisUtil redisUtil;

  public ThreadStartListener() {}

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    MatlabProxy proxy = (MatlabProxy) sce.getServletContext().getAttribute("proxy");
    try {
      proxy.exit();
    } catch (MatlabInvocationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("matlab销毁");
  }

  /** @see ServletContextListener#contextInitialized(ServletContextEvent) */
  @Override
  public void contextInitialized(ServletContextEvent arg0) {
    redisUtil =
        WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext())
            .getBean(RedisUtil.class);
    long a = redisUtil.flushDb();
    System.out.println(a + " 个redis缓存已删除！");
    datameaningService =
        WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext())
            .getBean(DatameaningService.class);
    chanpinService =
        WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext())
            .getBean(ChanpinService.class);
    baojingRecordService =
        WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext())
            .getBean(BaojingRecordService.class);
    errorSetService =
        WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext())
            .getBean(ErrorSetService.class);
    troubleRecordService =
        WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext())
            .getBean(TroubleRecordService.class);
    configValue =
        WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext())
            .getBean(ConfigValue.class);
    readTimer =
        new ReadTimer(
            datameaningService,
            chanpinService,
            baojingRecordService,
            troubleRecordService,
            configValue);
    List<ErrorSet> errors = errorSetService.getAll();
    for (int i = 0; i < errors.size(); i++) {
      if (errors.get(i).getIsOpen().equals(1)) {
        ErrorSet error =
            new ErrorSet(
                errors.get(i).getId(),
                errors.get(i).getChanpinId(),
                errors.get(i).getDatameaningId(),
                errors.get(i).getIsOpen(),
                errors.get(i).getValue(),
                    errors.get(i).getMinValue(),
                    errors.get(i).getMaxValue(),
                    errors.get(i).getUnit())
               ;
        redisUtil.set(
            "Es:" + errors.get(i).getChanpinId() + errors.get(i).getDatameaningId(), error);
      }
    }
    Map<String, Socket> MapSockets = new HashMap<>();
    CacheManage.setDataDisplays(datameaningService.Init());
    CacheManage.setMapSockets(MapSockets);
    readTimer.begin();
  }
}
