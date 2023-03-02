package com.nanjing.lightingdection.tools;

import java.math.BigInteger;

public class CRC {
  /*
   *
   * 将读取数据的指令获得16进制crc码*/
  public static String ReadToCRC(byte[] bytes) {
    int CRC = 0x0000ffff;
    int POLYNOMIAL = 0x0000a001;
    int i, j;
    for (i = 0; i < bytes.length; i++) {
      CRC ^= ((int) bytes[i] & 0x000000ff);
      for (j = 0; j < 8; j++) {
        if ((CRC & 0x00000001) != 0) {
          CRC >>= 1;
          CRC ^= POLYNOMIAL;
        } else {
          CRC >>= 1;
        }
      }
    }
    String a = Integer.toHexString(CRC);
    String str = "";
    if (a.length() < 4) {
      int b = a.length();
      for (int k = 0; k <(4 - b); k++) {
        str += "0" ;
      }
    }
    str+=a;
    String str1 = str.substring(0, 2);
    String str2 = str.substring(2);
    String str3 = str2 + str1;
    return str3;
  }

  /*
   *
   * 将16进制Byte类型数据转换成int类型数据*/
  public static BigInteger parseHexToInt(Byte hexStr) {
    BigInteger data = BigInteger.valueOf(hexStr);
    return data;
  }

  /*
   *
   * 将int类型数据转换成16进制类型数据*/
  public static byte parseIntToHex(int a) {
    String a1 = Integer.toHexString(a);
    return parseStringToHex(a1);
  }

  /*
   *
   * 将16进制Byte类型数据转换成String类型数据*/
  public static String parseHexToString(Byte hexStr) {
    // String
    BigInteger data = BigInteger.valueOf(hexStr);
    int temp = data.intValue(); // 把16进制转为10进制
    String str = Integer.toHexString(temp);
    if (str.length() > 2) {
      return str.substring(str.length() - 2, str.length());
    } else if (str.length() == 1) {
      return "0" + str;
    } else return str;
    // return data.toString(16);
  }

  /*
   *
   * 将String类型数据转换成16进制的数据*/
  public static Byte parseStringToHex(String a) {
    a = "0x" + a;
    int temp = Integer.decode(a);
    return (byte) temp;
  }

  public static Integer parseStringsToHex(String a) {
    a = "0x" + a;
    return Integer.decode(a);
  }

  /*
   * 把int类型(可大于128)转化为16进制的String
   */
  public static String parseIntToStrings(int a) {
    if (a >= 16) return Integer.toHexString(a);
    else if (a >= 10 && a < 16) return 0 + Integer.toHexString(a);
    else return "0" + a;
  }

  public static String reverse(String s) {
    char[] array = s.toCharArray();
    String reserve = "";
    for (int i = array.length - 1; i >= 0; i--) {
      reserve += array[i];
    }
    // System.out.print(""+reserve);
    return reserve;
  }
}
