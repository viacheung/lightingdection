package com.nanjing.lightingdection.controller;

import com.nanjing.lightingdection.dto.DataDisplay;
import com.nanjing.lightingdection.dto.SonData;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.service.ChanpinService;
import com.nanjing.lightingdection.service.DatameaningService;
import com.nanjing.lightingdection.tools.CRC;
import com.nanjing.lightingdection.utils.RedisUtil;
import gnu.io.PortInUseException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @Autowired private DatameaningService datameaningService;
  @Autowired private ChanpinService chanpinService;
  @Autowired private RedisUtil redisUtil;

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public Map<String, Object> showInfomation(
      @RequestParam(value = "id", required = false) Integer id,
      Model model,
      HttpServletRequest request)
      throws UnsupportedEncodingException {
    Map<String, Object> datas = new HashMap<>();
    Chanpin chanpin = chanpinService.findChanpinById(id);
    if (chanpin != null) {
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      if (leibie != null) {
        Map<Integer, Byte> map = new HashMap<>();
        byte[] datass = null;
        Object cache = redisUtil.get("Mc:" + id);
        if (cache != null) {
          byte[] cacheBytes = ((String) cache).getBytes(CharEncoding.ISO_8859_1); // Mc代表缓存在redis的数据
          datass = cacheBytes;
        }
        Datameaning datamean = datameaningService.findDatameaningById(leibie.getDatameaningId());
        for (int o = 0; o < datamean.getByteNum() * 2; o++) {
          map.put(o, null);
        }
        if (datass != null) {
          for (int m = 3; m < datass.length - 2; m++) {
            map.put(m - 3, datass[m]);
          }
        }
        try {
          CopyOnWriteArrayList<DataDisplay> list =
              datameaningService.getDisplayData(chanpin.getId(), chanpin.getDatameaningId(), map);
          Map<String, Object> dis = new HashMap<>();
          for (DataDisplay dataDisplay : list) {
            // System.out.println(dataDisplay.getName());
            // System.out.println(dataDisplay.getSons());
            for (SonData son : dataDisplay.getSons()) {
              // System.out.println(son.get);
              if (son.getIsShown() != null && son.getIsShown().equals(1)) {
                dis.put(son.getName(), "对应值: " + son.getData() + " 状态: " + son.getZhuangtai());
              }
            }
          }
          String str = "";
          if (datass != null) {
            for (int ll = 0; ll < datass.length; ll++) {
              str += CRC.parseHexToString(datass[ll]) + " ";
            }
          }
          datas.put("success", true);
          datas.put("shownData", dis);
          datas.put("iniDatas", str);
          // return datas;
          model.addAttribute("datas", datas);
        } catch (NumberFormatException | IOException | PortInUseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          datas.put("success", false);
          datas.put("errorMsg", e.toString());
          model.addAttribute("datas", datas);
        }
      } else {
        datas.put("success", false);
        datas.put("errorMsg", "产品编号有误，请重新输入！");
        model.addAttribute("datas", datas);
      }
    } else {
      datas.put("success", false);
      datas.put("errorMsg", "产品编号有误，请重新输入！");
      model.addAttribute("datas", datas);
    }

    return datas;
  }
}
