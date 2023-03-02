package com.nanjing.lightingdection.tools;

import java.util.HashMap;
import java.util.Map;

public class DataResolution {
  public static Map<String, Object> phraseData(int startRegister, int registerNum, byte[] getData) {
    Map<String, Object> a = new HashMap<String, Object>();
    if (getData.length <= 4) {
      a.put("error", "数据返回错误");
    } else {
      byte[] newList = new byte[getData.length - 1];
      for (int i = 0; i < newList.length; i++) {
        newList[i] = getData[i];
      }

      for (int i = startRegister, j = 3; i < startRegister + registerNum; i++, j += 2) {
        int a1 = 2 * i - 2;
        int a2 = 2 * i - 1;
        a.put(i + "" + a1, getData[j] & 0xFF);
        a.put(i + "" + a2, getData[j + 1] & 0xFF);
      }
    }
    return a;
  }
}
