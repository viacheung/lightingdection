package com.nanjing.lightingdection.tools;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class SolveData {

  public static Map<String, Object> solve(Map<String, Object> iniDatas, String danwei, int weishu) {
    int cou = 0;
    for (Map.Entry<String, Object> entry : iniDatas.entrySet()) { // 判断数组中是否含有0
      cou++;
      if (Double.parseDouble(Split.getNumber((String) entry.getValue())) == 0) {
        System.out.println("解方程出现0");
        return iniDatas;
      }
    }
    DecimalFormat dfs = null;
    if (weishu == 0) {
      dfs = new DecimalFormat("######0");
    } else {
      String ds = "######0.";
      for (int h = 0; h < weishu; h++) {
        ds += "0";
      }
      dfs = new DecimalFormat(ds);
    }
    Map<String, Object> map = new HashMap<>();
    double[] nums = new double[cou]; // 存放迭代过程中的变量
    double[] iniNums = new double[cou]; // 存放初始化的值
    int l = 0;
    for (Map.Entry<String, Object> entry : iniDatas.entrySet()) { // 数组元素不含有零，开始进行解方程
      nums[l] = Double.parseDouble(Split.getNumber((String) entry.getValue())) / 1.3; // 进行第一次迭代
      iniNums[l] = Double.parseDouble(Split.getNumber((String) entry.getValue()));
      l++;
    }
    for (int i = 0; i < 40; i++) {
      for (int j = 0; j < nums.length; j++) {
        // 存储分子
        Double fenzi = 0.0;
        for (int k = 0; k < nums.length; k++) {
          if (k != j) fenzi += 1 / nums[k];
        }
        // 存储分母
        Double fenmu = 0.0;
        for (int k = 0; k < nums.length; k++) {
          fenmu += 1 / nums[k];
        }
        nums[j] = (iniNums[j] * fenzi) / fenmu;
      }
    }
    /*for(int i=0;i<nums.length;i++)
    System.out.println(nums[i]);*/
    boolean isPositive = true;
    for (int i = 0; i < nums.length; i++) {
      if (Double.parseDouble(dfs.format(nums[i])) <= 0) {
        isPositive = false;
        break;
      }
    }
    if (isPositive) {
      int i = 0;
      for (Map.Entry<String, Object> entry : iniDatas.entrySet()) {
        map.put(entry.getKey(), dfs.format(nums[i]) + danwei);
        i++;
      }
      System.out.println("解方程有解");
      return map;
    } else {

      for (Map.Entry<String, Object> entry : iniDatas.entrySet()) {
        Double dou = Double.parseDouble(Split.getNumber((String) entry.getValue()));
        map.put(entry.getKey(), dfs.format(dou * 0.92) + danwei);
      }
      System.out.println("解方程无解");
      return map;
    }
  }
}
