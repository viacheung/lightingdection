package com.nanjing.lightingdection.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.nanjing.lightingdection.dao.BaojingRecordDao;
import com.nanjing.lightingdection.entity.AirDevise;
import com.nanjing.lightingdection.entity.BaojingRecord;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.tools.CRC;
import com.nanjing.lightingdection.tools.SolveData;
import com.nanjing.lightingdection.utils.RedisUtil;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import net.sf.json.JSON;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@SpringBootTest
public class DatameaningServiceTest {
  @Autowired private DatameaningService datameaningService;
  @Autowired private BaojingRecordDao baojingRecordDao;
  @Autowired private ChanpinService chanpinService;
  @Autowired private RedisUtil redisUtil;

  @Test
  public void test1() {
    long startTime = System.currentTimeMillis(); // 获取开始时间
    Datameaning datameaning = datameaningService.findDatameaningById(13);
    long endTime = System.currentTimeMillis(); // 获取结束时间
    System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
  }

  @Test
  public void test2() {
    Socket sck = new Socket();
    try {
      sck.connect(new InetSocketAddress("192.168.1.200", 4196), 200);
      sck.setSoTimeout(200);

      // System.out.println(TcpPortManager.isServerClose(sck));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test3() {
     byte[] by = {0x01, 0x04, 0x03, 0x00, (byte) 0x00, 0x05};
//    byte[] by1 = {0x01, 0x04, 0x0c, (byte)0x80, (byte)0xd3, 0x00, 0x01, 0x00,0x01,0x00, (byte)0xc8, 0x00, 0x00, 0x00, 0x00};
    System.out.println(CRC.ReadToCRC(by));
  }

  @Test
  public void test4() {
    // byte[]
    // by={0x0d,0x04,0x0c,0x02,(byte)0xe4,0x00,0x00,0x00,0x0d,0x00,(byte)0xc8,0x00,0x00,0x00,0x00};
    // byte[]
    // by1={0x0d,0x04,0x0c,0x03,(byte)0x02,0x00,0x00,0x00,0x0d,0x00,(byte)0xc8,0x00,0x00,0x00,0x00};
    byte a = (byte) 0x30;
    System.out.println(CRC.parseHexToString(a));
    BaojingRecord record = new BaojingRecord(null, 1, 1, "1", 1 + "", "1", "1");
    baojingRecordDao.insertBaojingRecord(record);
  }

  @Test
  public void test5() {
    int i;
    double R1, R2, R3, R4;
    double A, B, C, D;
    A = 1;
    B = 2;
    C = 3;
    D = 4;
    // scanf("%lf%lf%lf%lf",&A,&B,&C,&D);//A,B,C,D 必须大于1
    R1 = A / 1.3;
    R2 = B / 1.3;
    R3 = C / 1.3;
    R4 = D / 1.3;
    for (i = 1; i < 40; i++) {
      R1 = A * (1 / R2 + 1 / R3 + 1 / R4) / (1 / R1 + 1 / R2 + 1 / R3 + 1 / R4);
      R2 = B * (1 / R1 + 1 / R3 + 1 / R4) / (1 / R1 + 1 / R2 + 1 / R3 + 1 / R4);
      R3 = C * (1 / R1 + 1 / R2 + 1 / R4) / (1 / R1 + 1 / R2 + 1 / R3 + 1 / R4);
      R4 = D * (1 / R1 + 1 / R2 + 1 / R3) / (1 / R1 + 1 / R2 + 1 / R3 + 1 / R4);
      System.out.println("R1:" + R1 + " R2:" + R2 + " R3:" + R3 + " R4:" + R4);
    }
  }

  @Test
  public void test6() {
    List<Chanpin> list = chanpinService.queryAll();
    for (int i = 0; i < list.size(); i++) {
      redisUtil.set("cp:" + list.get(i).getId(), list.get(i));
    }
  }

  @Test
  public void test7() {
    Map<String, Object> map = new HashMap<>();
    map.put("01", "0#");
    map.put("02", "2#");
    map.put("03", "3#");
    map.put("04", "4#");
    // map.put("05","9#");

    Map<String, Object> map1 = SolveData.solve(map, "#", 2);
    System.out.println(map);
    System.out.println(map1);
  }

  @Test
  public void getAirDeviseData() {
    String airDevise = "[{\"sensorId\":593,\"mesureTime\":\"1663641839000\",\"maxValue\":140,\"avgValue\":140,\"alarmLevel\":0,\"lightningCount\":0,\"onLine\":true},{\"sensorId\":594,\"mesureTime\":\"1663641839000\",\"maxValue\":140,\"avgValue\":140,\"alarmLevel\":0,\"lightningCount\":0,\"onLine\":true},{\"sensorId\":595,\"mesureTime\":\"1663641839000\",\"maxValue\":140,\"avgValue\":140,\"alarmLevel\":0,\"lightningCount\":0,\"onLine\":true}]";
    JSONArray jsonArray = JSONArray.parseArray((airDevise), Feature.OrderedField);
    if(jsonArray.size()>0)
    {
      for (int i = 0; i < jsonArray.size();i++)
      {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String time = (String) jsonObject.get("mesureTime");
        long a = (long)(Double.parseDouble(time));
        Date date = new Date();
        date.setTime(a);
        jsonObject.put("mesureTime",new SimpleDateFormat().format(date));
      }
    }

    System.out.println(jsonArray);
  }

  @Test
  public void postAirDeviseData() throws IOException {
    HttpClient httpClient = new DefaultHttpClient();
    String name = "zyzmdyk";
//    String Password = "Zyzmdyk123456";
    String httpUrl = "http://120.55.160.161:6820/DIMS";
    HttpPost httpPost = new HttpPost(httpUrl+"/loginInterfaceNew");
    List formparams = new ArrayList();
    formparams.add(new BasicNameValuePair("username",name));
    String password = "VD8cjCO+U4uxk9hd0tWr87qB4Km7uG7quq5NtApJiBCUIiMc31jH3IfKJWaAIYnW/vrcLvTeiSOMT9zbs4SWeakIzOuoJnhPpNCBk36V1WWKWlq9q6oNZ+q++V+41S1Lj6FB/wgO0qV2D5YKvZ59lT4Cym2MTo/+wMssss5Y8Sk=";
    password = password.replaceAll("\\+","%2B");
    formparams.add(new BasicNameValuePair("password", password));
    UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
    httpPost.setEntity(uefEntity);
    CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost);
    HttpEntity entity = response.getEntity();
    com.alibaba.fastjson.JSONObject jsonObjectLogin = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(entity,"UTF-8"),Feature.OrderedField);
    System.out.println(jsonObjectLogin);

    httpPost.setURI(URI.create("http://120.55.160.161:6820/DIMS/getData"));
    response = (CloseableHttpResponse) httpClient.execute(httpPost);
    entity = response.getEntity();
    JSONArray jsonObjectData = JSONObject.parseArray(EntityUtils.toString(entity,"UTF-8"),Feature.OrderedField);
    System.out.println(jsonObjectData);

    if(jsonObjectData.size()>0)
    {
      for (int i = 0; i < jsonObjectData.size();i++)
      {
        JSONObject jsonObj = jsonObjectData.getJSONObject(i);
        long time = (long) jsonObj.get("mesureTime");
        Date date = new Date();
        date.setTime(time);
        jsonObj.put("mesureTime",new SimpleDateFormat().format(date));
      }
    }
    System.out.println(jsonObjectData);
  }

}

