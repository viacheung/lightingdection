package com.nanjing.lightingdection.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
  /**
   * 切割時間段
   *
   * @param dateType 交易類型 M/D/H/N -->每月/每天/每小時/每分鐘
   * @param start yyyy-MM-dd HH:mm:ss
   * @param end yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static List<String> cutDate(String dateType, String start, String end) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date dBegin = sdf.parse(start);
      Date dEnd = sdf.parse(end);
      return findDates(dateType, dBegin, dEnd);
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static List<String> findDates(String dateType, Date dBegin, Date dEnd) throws Exception {
    List<String> listDate = new ArrayList<>();
    Calendar calBegin = Calendar.getInstance();
    calBegin.setTime(dBegin);
    Calendar calEnd = Calendar.getInstance();
    calEnd.setTime(dEnd);
    while (calEnd.after(calBegin)) {
      switch (dateType) {
        case "M":
          calBegin.add(Calendar.MONTH, 1);
          break;
        case "D":
          calBegin.add(Calendar.DAY_OF_YEAR, 1);
          break;
      }
      if (calEnd.after(calBegin)) {
        if (dateType.equals("M")) {
          listDate.add(new SimpleDateFormat("yyyy-MM").format(calBegin.getTime()));
        } else {
          listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime()));
        }
      } else {
        if (dateType.equals("M")) {
          listDate.add(new SimpleDateFormat("yyyy-MM").format(calEnd.getTime()));
        } else {
          listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calEnd.getTime()));
        }
      }
    }
    return listDate;
  }
}
