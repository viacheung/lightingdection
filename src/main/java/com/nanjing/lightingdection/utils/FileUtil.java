package com.nanjing.lightingdection.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
  public static boolean copy(MultipartFile src, File dest) {
    BufferedInputStream bufferedInputStream = null;
    BufferedOutputStream bufferedOutputStream = null;
    try {
      bufferedInputStream = new BufferedInputStream(src.getInputStream());
      bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dest));
      byte[] bts = new byte[1024];
      int len = -1;
      while ((len = bufferedInputStream.read(bts)) != -1) {
        bufferedOutputStream.write(bts, 0, len);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      if (bufferedInputStream != null) {
        try {
          bufferedInputStream.close();
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
      if (bufferedOutputStream != null) {
        try {
          bufferedOutputStream.close();
        } catch (Exception e3) {
          e3.printStackTrace();
        }
      }
    }
  }
}
