package com.nanjing.lightingdection.dao;

import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.service.*;
import com.nanjing.lightingdection.tools.CRC;
import com.nanjing.lightingdection.utils.RedisUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
  @Autowired private UserDao userDao;
  @Autowired private DatameaningDao datameaningDao;
  @Autowired private DatameaningService datameaningService;
  @Autowired private ChanpinService chanpinService;
  @Autowired private BaojingRecordService baojingRecordService;
  @Autowired private InfoRecordService infoRecordService;
  @Autowired private TroubleRecordService troubleRecordService;
  @Autowired private RedisUtil redisUtil;

  @Test
  public void Test() {

    byte[] bb = {0x4b, 0x04, 0x00, 0x00, 0x00, 0x05};
    String aa = CRC.ReadToCRC(bb);
    System.out.println(aa);
    // byte[] cc={0x0D,0x04,0x0C,0x02,(byte) 0xD0,0x00,0x00,0x00,0x0D,0x00, (byte)
    // 0xc8,0x00,0x00,0x00,0x00};
    // String crc1=CRC.ReadToCRC(cc);
    // System.out.println(crc1);
    // byte[] aa={(byte) 0xf5,(byte)0xa6};
    // Byte aaaa=0xf5;
    // String a=CRC.parseHexToString(aa[0]);
    // String b=CRC.parseHexToString(aa[1]);
    // System.out.println(a+"    "+b);
    /*byte[] byte2={0x02,0x04,0x00,0x00,0x00,0x05,0x61, (byte) 0xFA};
            try {
                byte[] a= TcpPortManager.getReturnData("192.168.1.200",4196,byte2,1000,3000);
                System.out.println(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
    */
    /*  try {
        Map<Integer,Byte> map=datameaningService.readData(28,635);
        System.out.println(map);
        List<DataDisplay> test=datameaningService.getDisplayData(28,635, map);
        System.out.println(test);

    } catch (IOException e) {
        e.printStackTrace();
    } catch (PortInUseException e) {
        e.printStackTrace();
    }*/
    /*   byte[] bytee={0x0D,0x04,0x00,0x00,0x00,0x05,0x30, (byte) 0xC5};
    byte[] bytes= new byte[0];
    try {
        bytes = TcpPortManager.getReturnData("192.168.1.200",4196,bytee,2000,2000);
        System.out.println(bytes);
    } catch (IOException e) {
        e.printStackTrace();
    }*/

  }

  @Test
  public void Test1() {

    /*byte[]	bb= {0x01,0x04,0x00,0x01,0x00,0x05};
     String aa=CRC.ReadToCRC(bb);
     System.out.println(aa);
    byte[] byte2={0x02,0x04,0x00,0x00,0x00,0x05,0x61, (byte) 0xFA};
     try {
         byte[] a= TcpPortManager.getReturnData("192.168.1.200",4196,byte2,1000,3000);
         System.out.println(a);
     } catch (IOException e) {
         e.printStackTrace();
     }*/
    // long startTime = System.currentTimeMillis(); //获取开始时间

    for (int i = 0; i < 1000; i++) {
      new Thread(
              new Runnable() {
                @Override
                public void run() {
                  long startTime = System.currentTimeMillis(); // 获取开始时间
                  Datameaning datameaning = datameaningService.findDatameaningById(13);
                  long endTime = System.currentTimeMillis(); // 获取结束时间
                  System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
                }
              })
          .start();
    }

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void Test2() {

    /*byte[]	bb= {0x01,0x04,0x00,0x01,0x00,0x05};
     String aa=CRC.ReadToCRC(bb);
     System.out.println(aa);
    byte[] byte2={0x02,0x04,0x00,0x00,0x00,0x05,0x61, (byte) 0xFA};
     try {
         byte[] a= TcpPortManager.getReturnData("192.168.1.200",4196,byte2,1000,3000);
         System.out.println(a);
     } catch (IOException e) {
         e.printStackTrace();
     }*/

    long startTime = System.currentTimeMillis(); // 获取开始时间
    redisUtil.get("dm:" + 13);
    long endTime = System.currentTimeMillis(); // 获取结束时间
    System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
  }

  @Test
  public void Test3() {
    long startTime = System.currentTimeMillis(); // 获取开始时间
    // redisUtil.get("dm:"+13);
    // long endTime = System.currentTimeMillis(); //获取结束时间
    List<Datameaning> list = datameaningDao.getAllSons(66);
    System.out.println(list.size() + " " + list);
    long endTime = System.currentTimeMillis(); // 获取结束时间
    System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
  }

  @Test
  public void Test4() {
    long startTime = System.currentTimeMillis(); // 获取开始时间
    // redisUtil.get("dm:"+13);
    // long endTime = System.currentTimeMillis(); //获取结束时间

    List<Datameaning> list = datameaningService.getDMAllSons(66);
    System.out.println(list.size() + " " + list);
    long endTime = System.currentTimeMillis(); // 获取结束时间
    System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
  }

  @Test
  public void Test5() {
    // Jedis jedis=new Jedis("127.0.0.1",6379);
    // System.out.println(jedis.get("data"));
  }
}
