package com.nanjing.lightingdection.cache;

import com.nanjing.lightingdection.dto.DataDisplayAdd;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class CacheManage {
  /** 直接在内存中定义两个个全局变量 DataDisplays:展示产品相关 MapSockets:存储已连接的socket,避免不必要的断开重连等操作 */
  static CopyOnWriteArrayList<DataDisplayAdd> DataDisplays = null;

  static Map<String, Socket> MapSockets = null;

  public static CopyOnWriteArrayList<DataDisplayAdd> getDataDisplays() {
    return DataDisplays;
  }

  public static void setDataDisplays(CopyOnWriteArrayList<DataDisplayAdd> dataDisplays) {
    DataDisplays = dataDisplays;
  }

  public static Map<String, Socket> getMapSockets() {
    return MapSockets;
  }

  public static void setMapSockets(Map<String, Socket> mapSockets) {
    MapSockets = mapSockets;
  }
}
