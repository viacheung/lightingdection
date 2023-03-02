package com.nanjing.lightingdection.tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TcpPortManager {

  public static byte[] getReturnData(
      Socket sck, String ip, Integer port, byte[] bytes, Integer connectTime, Integer readTime)
      throws IOException {
    if (sck == null || sck.isClosed()) return null;
    try {
      OutputStream os = sck.getOutputStream(); // 输出流
      os.write(bytes);
      BufferedInputStream is = new BufferedInputStream(sck.getInputStream());
      byte[] buffer = new byte[20480];
      int length = is.read(buffer, 0, 20480);
      if (length <= 0) { // 获取数据失效，重新创建连接
        return null;
      }
      byte[] buffer1 = new byte[length];
      for (int i = 0; i < buffer1.length; i++) {
        buffer1[i] = buffer[i];
      }
      os.close();
      is.close();
      return buffer1;
    } catch (UnknownHostException e) {
      System.out.println(e);
      return null;
    } catch (SocketTimeoutException e) {
      System.out.println(e);
      return null;
    } catch (SocketException e) {
      System.out.println(e);
      return null;
    }
  }

  public static byte[] getReturnData(Socket sck, byte[] bytes) throws IOException {
    OutputStream os = sck.getOutputStream(); // 输出流
    os.write(bytes);
    BufferedInputStream is = new BufferedInputStream(sck.getInputStream());
    byte[] buffer = new byte[20480];
    int length = is.read(buffer, 0, 20480);
    if (length <= 0) {
      length = 0;
    }
    byte[] buffer1 = new byte[length];
    for (int i = 0; i < buffer1.length; i++) {
      buffer1[i] = buffer[i];
    }
    os.close();
    is.close();
    return buffer1;
  }

  public static void sendData(String ip, Integer port, byte[] bytes)
      throws NumberFormatException, UnknownHostException, IOException {

    Socket sck = new Socket(ip, port);
    // System.out.println(sck.isConnected());
    // 2.传输内容
    // String content="这是一个java模拟客户端";
    // byte[] bstream={0x01,0x04,0x00,0x00,0x00,0x05,0x30,0x09};  //转化为字节流
    OutputStream os = sck.getOutputStream(); // 输出流
    os.write(bytes);
    sck.close();
  }
}
