package com.nanjing.lightingdection.tools;

/**
 * 数组工具
 *
 * @author yangle
 */
public class ArrayUtils {

  /**
   * 合并数组
   *
   * @param bytes2 第一个数组
   * @param secondArray 第二个数组
   * @return 合并后的数组
   */
  public static byte[] concat(byte[] bytes2, byte[] secondArray) {
    // System.out.println("bytes2: "+bytes2.length);
    // System.out.println("secondArray: "+secondArray.length);
    if (bytes2 == null || secondArray == null) {
      return null;
    }
    byte[] bytes = new byte[bytes2.length + secondArray.length];
    System.arraycopy(bytes2, 0, bytes, 0, bytes2.length);
    System.arraycopy(secondArray, 0, bytes, bytes2.length, secondArray.length);
    return bytes;
  }
}
