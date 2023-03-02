package com.nanjing.lightingdection.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nanjing.lightingdection.cache.CacheManage;
import com.nanjing.lightingdection.dto.*;
import com.nanjing.lightingdection.entity.Image;
import com.nanjing.lightingdection.entity.*;
import com.nanjing.lightingdection.service.*;
import com.nanjing.lightingdection.tools.Split;
import gnu.io.PortInUseException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
  @Autowired private LoginService loginService;
  @Autowired private ChanpinService chanpinService;
  @Autowired private ImageService imageService;
  @Autowired private AppManageService appManageService;
  @Autowired private DatameaningService datameaningService;
  @Autowired private ChanpinPhotoService chanpinPhotoService;
  @Autowired private BaojingPanduanService baojingPanduanService;
  @Autowired private BaojingRecordService baojingRecordService;
  @Autowired private InfoRecordService infoRecordService;
  @Autowired private TroubleRecordService troubleRecordService;
  @Autowired private ActualResistanceService actualResistanceService;

  public static Font getPdfChineseFont() throws Exception {
    BaseFont bfChinese =
        BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
    return fontChinese;
  }

  @RequestMapping(value = "/user/normaluser", method = RequestMethod.GET)
  public ModelAndView normaluser(ModelAndView model, Model model1) {
    Login a = loginService.getAll();

    String companyname = a.getCompanyname();
    String name = a.getName();
    String address = a.getAddress();
    String internet = a.getInternet();
    String phone = a.getPhone();
    model1.addAttribute("companyname", companyname);
    model1.addAttribute("name", name);
    model1.addAttribute("address", address);
    model1.addAttribute("internet", internet);
    model1.addAttribute("phone", phone);
    // model1.addAttribute("appManages",appManages);
    model.setViewName("user/normaluser");
    return model;
  }

  @RequestMapping(value = "/user/showInfomation", method = RequestMethod.GET)
  public String showInfomation(Model model, HttpServletRequest request) {
    return "user/showInfomation";
  }

  @RequestMapping(value = "/user/showRight", method = RequestMethod.GET)
  public String showRight(Model model, HttpServletRequest request) {
    Image image = imageService.getAll().get(0);
    model.addAttribute("image", image);
    return "user/showRight";
  }

  @RequestMapping(value = "/user/showDetail", method = RequestMethod.GET)
  public String showDetail(Model model, HttpServletRequest request) {
    return "user/showDetail";
  }

  @RequestMapping(value = "/user/showDetail2", method = RequestMethod.GET)
  public String showDetail2(Model model, HttpServletRequest request) {
    return "user/showDetail2";
  }

  @RequestMapping(value = "/user/showDetail3", method = RequestMethod.GET)
  public String showDetail3(Model model, HttpServletRequest request) {
    return "user/showDetail3";
  }

  @RequestMapping(value = "/user/showDetail4", method = RequestMethod.GET)
  public String showDetail4(
      Model model, HttpServletRequest request, @RequestParam("id") String id) {
    // System.out.println("id: "+id);
    Integer Id = Integer.parseInt(id);
    model.addAttribute("locationId", Id);
    return "user/showDetail4";
  }

  @RequestMapping(value = "/user/getChanpinLeibiesByLocationId", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> getChanpinLeibiesByLocationId(String locationId) {
    Integer LocationId = Integer.parseInt(locationId);
    List<Chanpin> leibies = chanpinService.getAllSons(LocationId);
    return leibies;
  }

  @RequestMapping(value = "/user/getChanpinsByLeibieId", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> getChanpinsByLeibieId(String leibieId) {
    Integer LeibieId = Integer.parseInt(leibieId);
    List<Chanpin> chanpins = chanpinService.getAllSons(LeibieId);
    return chanpins;
  }

  @RequestMapping(value = "/user/OnlineMonitor", method = RequestMethod.GET)
  public String OnlineMonitor(Model model, HttpServletRequest request) {
    return "user/OnlineMonitor";
  }

  @RequestMapping(value = "/user/searchByBaojingRecord", method = RequestMethod.GET)
  public String searchByBaojingRecord(Model model, HttpServletRequest request) {
    return "user/searchByBaojingRecord";
  }

  @RequestMapping(value = "/user/searchByInfoRecord", method = RequestMethod.GET)
  public String searchByInfoRecord(Model model, HttpServletRequest request) {
    return "user/searchByInfoRecord";
  }

  @RequestMapping(value = "/user/searchByTroubleRecord", method = RequestMethod.GET)
  public String searchByTroubleRecord(Model model, HttpServletRequest request) {
    return "user/searchByTroubleRecord";
  }

  @RequestMapping(value = "/user/searchByResistance", method = RequestMethod.GET)
  public String searchByResistance(Model model, HttpServletRequest request) {
    return "user/searchByResistance";
  }

  @RequestMapping(value = "/user/parentList", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> ParentList() {
    List<Chanpin> list = chanpinService.queryAll();
    List<Chanpin> parent = new ArrayList<Chanpin>();
    for (Chanpin chanpin : list) {
      if (chanpin.getPid() == 0) {
        parent.add(chanpin);
      }
    }
    // System.out.println(parent);
    for (Chanpin chanpin : parent) {
      chanpin.setZhuangtai(0);
      int count1 = 0;
      int count2 = 0;
      for (Chanpin chanpin2 : list) {
        if (chanpin2.getIsparent() == 0 && chanpin2.getLocation().equals(chanpin.getLocation())) {
          if (chanpin2.getZhuangtai() > 0) {
            if (chanpin2.getZhuangtai() == 1) {
              count1++;
            } else {
              count2++;
            }
          }
        }
      }
      if (count1 > 0) chanpin.setZhuangtai(1);
      else {
        if (count2 > 0) chanpin.setZhuangtai(2);
      }
    }
    // System.out.println("得到的parentList: "+parent);
    return parent;
  }

  @RequestMapping("/user/showPhotoByPath")
  public void showPhotoByPath(
      HttpServletResponse response, HttpServletRequest request, @RequestParam("path") String path)
      throws IOException {
    // System.out.println("path:"+path);
    String realPath = request.getSession().getServletContext().getRealPath("/");
    File file = new File(realPath + path);
    FileInputStream in = new FileInputStream(file);
    try {
      BufferedImage image = ImageIO.read(in);
      ServletOutputStream outputStream = response.getOutputStream();
      ImageIO.write(image, "PNG", outputStream);
      in.close();
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/user/playerSound")
  public void video(HttpServletResponse response, HttpServletRequest request) throws Exception {
    ApplicationHome h = new ApplicationHome(getClass());
    File jarF = h.getSource();
    String realPath = jarF.getParentFile().toString() + "/classes/static/";
    File file = new File(realPath + "sound/报警.mp3");
    FileInputStream in = new FileInputStream(file);
    ServletOutputStream out = response.getOutputStream();
    byte[] b = null;
    while (in.available() > 0) {
      if (in.available() > 10240) {
        b = new byte[10240];
      } else {
        b = new byte[in.available()];
      }
      in.read(b, 0, b.length);
      out.write(b, 0, b.length);
    }
    in.close();
    out.flush();
    out.close();
  }

  @RequestMapping(value = "/user/toDouble", method = RequestMethod.POST)
  @ResponseBody
  public List<Pair<Double, Double>> toDouble(String dots) {
    List<Pair<Double, Double>> result = new ArrayList<Pair<Double, Double>>();
    String[] Dot = dots.split("-");
    for (String dot : Dot) {
      String[] dotsplit = dot.split(",");
      double x = Double.parseDouble(dotsplit[0].substring(1));
      double y = Double.parseDouble(dotsplit[1].substring(0, dotsplit[1].length() - 1));
      // System.out.println(x+","+y);
      result.add(new Pair<Double, Double>(x, y));
    }
    return result;
  }

  @RequestMapping(value = "/user/getAllErrorChanpin", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> getAllErrorChanpin() {
    // System.out.println("执行一次数量读取");
    List<Chanpin> all = chanpinService.queryAll();
    List<Chanpin> data = new ArrayList<>();
    for (Chanpin chanpin : all) {
      if (chanpin.getIsparent() == 0 && chanpin.getZhuangtai() == 1) {
        data.add(chanpin);
      }
    }
    return data;
  }

  @RequestMapping(value = "/user/getChanpinByLocationId", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> getChanpinByLocationId(String locationId) {
    // System.out.println("执行一次数量读取");
    List<Chanpin> leibies = chanpinService.getAllSons(Integer.parseInt(locationId));
    List<Chanpin> data = new ArrayList<>();
    for (Chanpin leibie : leibies) {
      List<Chanpin> chanpins = chanpinService.getAllSons(leibie.getId());
      for (Chanpin chanpin : chanpins) {
        data.add(chanpin);
      }
    }
    return data;
  }

  @RequestMapping(value = "/user/appManages", method = RequestMethod.POST)
  @ResponseBody
  public List<AppManage> appManages() {
    List<AppManage> appManages = appManageService.queryAll();
    return appManages;
  }

  @RequestMapping(value = "/user/openFile", method = RequestMethod.GET)
  public String openFile(
      HttpServletResponse response,
      HttpServletRequest request,
      @RequestParam("path") String paths) {

    String realPath = request.getSession().getServletContext().getRealPath("/");
    String path = realPath + paths; // 这里不用转义
    // System.out.println(path);
    Desktop desktop = Desktop.getDesktop();
    try {
      desktop.open(new File(path));
      //  return 1;
    } catch (IOException ex) {
      ex.printStackTrace();
      //   return 0;
    }
    return "redirect:/user/normaluser";
  }

  @RequestMapping(value = "/user/getChanpinByParentId", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> getChanpinByParentId(String id) {
    // System.out.println("i: "+idsi);
    if (id.equals("")) id = "0";
    List<Chanpin> list = chanpinService.queryAll();
    List<Chanpin> result = new ArrayList<Chanpin>();
    for (Chanpin chanpin : list) {
      if (chanpin.getPid() == Integer.parseInt(id)) {
        result.add(chanpin);
      }
    }
    return result;
  }

  @RequestMapping(value = "/user/getChanpinBySlaveId", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> getChanpinBySlaveId(String slaveId) {
    // System.out.println("i: "+idsi);
    if (slaveId.equals("") || slaveId == null) return null;
    else {
      return chanpinService.findBySlaveId(Integer.parseInt(slaveId));
    }
  }

  @RequestMapping(value = "/user/getChanpinLeibie", method = RequestMethod.POST)
  @ResponseBody
  public ChanpinLeibie getChanpinLeibie(String chanpinId)
      throws NumberFormatException, UnknownHostException, IOException, PortInUseException {
    // String chanpinId="";
    // System.out.println("chanpinId:"+chanpinId);
    if (chanpinId == null || chanpinId.equals("") || chanpinId.equals("null")) return null;
    else {
      Chanpin chan = chanpinService.findChanpinById(Integer.parseInt(chanpinId));
      if (chan.getPid() == 0) return null;
      // else {chanpinId=ParentListoid;}
      // return datameaningservice.getAllChanpinLeibie(Integer.parseInt(chanpinId));
      List<DataDisplayAdd> all = null;
      // CacheManage cache=CacheManage.getInstance();
      try {
        all = CacheManage.getDataDisplays();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      Chanpin leibei = chanpinService.findChanpinById(Integer.parseInt(chanpinId));
      ChanpinLeibie chanpinLeibie = new ChanpinLeibie(null, null, null, null, null, null);
      chanpinLeibie.setId(leibei.getId());
      chanpinLeibie.setName(leibei.getName());
      chanpinLeibie.setLocation(leibei.getLocation());
      chanpinLeibie.setModel(leibei.getModel());
      List<Chanpin> sons = chanpinService.getAllSons(leibei.getId());
      // System.out.println("得到的产品类别:"+sons);
      chanpinLeibie.setChanpinNum(sons.size());
      // 判断哪些产品出现了问题
      int l = 0;
      int num = 0;
      for (Chanpin chanpin : sons) {
        Integer datameaningId = leibei.getDatameaningId();
        Datameaning father = datameaningService.findDatameaningById(datameaningId);
        // System.out.println("father: "+father);
        Integer fataherId = father.getId();
        List<Datameaning> datasons = datameaningService.getDMAllSons(fataherId);
        // System.out.println("datasons    :"+datasons);
        List<Datameaning> datas = new ArrayList<>();
        for (Datameaning a : datasons) {
          if (a.getType() == 1) {
            datas.add(a);
          }
        }
        // System.out.println("datas    :"+datas);
        for (Datameaning son : datas) {
          // List<DataDisplay> displays=getDisplayData(chanpin.getId(),son.getId());
          List<DataDisplay> displays = null;
          for (DataDisplayAdd data : all) {
            int a = data.getRejisterId();
            int b = son.getId();
            // System.out.println("a==b"+(a==b));
            // System.out.println("chanpin.getId():"+data.getChanpinId());
            // System.out.println("rejister.getId():"+data.getRejisterId());
            // System.out.println("data.getChanpinId():"+chanpin.getId());
            //	System.out.println("data.getRejisterId():"+son.getId());
            // System.out.println("data.getChanpinId()==chanpin.getId():"+(data.getChanpinId()==chanpin.getId()));
            // System.out.println("data.getRejisterId()==son.getId():"+(data.getRejisterId()==son.getId()));

            // System.out.println("data.getChanpinId()==chanpin.getId()&&data.getRejisterId()==son.getId():"+(data.getChanpinId()==chanpin.getId()&&data.getRejisterId()==son.getId()));
            int c = data.getChanpinId();
            int d = chanpin.getId();
            if (c == d && a == b) {
              displays = data.getSon();
            }
          }
          // System.out.println("displays :"+displays);
          for (DataDisplay dataDisplay : displays) {
            List<SonData> sonDatas = dataDisplay.getSons();
            for (int i = 0; i < sonDatas.size(); i++) {
              if ((sonDatas.get(i).getZhuangtai() == 1 || sonDatas.get(i).getZhuangtai() == 2)
                  && sonDatas.get(i).getIsShown() == 1) {
                l++;
              }
            }
          }
        }
        if (l > 0) num++;
      }
      chanpinLeibie.setErrorNum(num);
      return chanpinLeibie;
    }
  }

  @RequestMapping(value = "/user/findCPbyChanpinId", method = RequestMethod.POST)
  @ResponseBody
  public ChanpinPhoto findCPbyChanpinId(String chanpinId) {
    // String id=ParentListoid;
    ChanpinPhoto cp = chanpinPhotoService.getByChanpinId(Integer.parseInt(chanpinId));
    return cp;
  }

  @RequestMapping(value = "/user/getErrorChanpinDisplay", method = RequestMethod.POST)
  @ResponseBody
  public ChanpinDisplay getErrorChanpinDisplay(String chanpinId)
      throws NumberFormatException, UnknownHostException, IOException, PortInUseException {
    if (chanpinId == "") return null;
    else if (chanpinId.equals("guzhang")) return null;
    // return datameaningservice.getAllErrorChanpinDisplay(Integer.parseInt(chanpinId));
    else {
      // System.out.println("chanpinId"+chanpinId);
      List<DataDisplayAdd> al = null;
      //	CacheManage cache=CacheManage.getInstance();
      try {
        al = CacheManage.getDataDisplays();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      ChanpinDisplay all = new ChanpinDisplay(null, null, null);
      Chanpin leibie = chanpinService.findChanpinById(Integer.parseInt(chanpinId));
      List<ChanpinDisplaySon> sons = new ArrayList<>();
      List<Chanpin> chanpins = chanpinService.getAllSons(Integer.parseInt(chanpinId));
      // System.out.println("chanpins:"+chanpins);
      for (Chanpin chanpin : chanpins) {
        ChanpinDisplaySon son = new ChanpinDisplaySon(null, null, null, null, null);
        int i = 0; // 用于记录异常部分出现的情况
        int j = 0; // 用于记录预警出现的情况
        Datameaning fatherdata = datameaningService.findDatameaningById(leibie.getDatameaningId());
        // System.out.println("fatherdata:"+fatherdata);
        List<Datameaning> datas = datameaningService.getDMAllSons(fatherdata.getId());
        for (Datameaning data : datas) {
          if (data.getType() == 1) {
            // List<DataDisplay> dataDisplays=getDisplayData(chanpin.getId(),data.getId());
            List<DataDisplay> dataDisplays = null;
            for (DataDisplayAdd data1 : al) {
              int a = data1.getRejisterId();
              int b = data.getId();
              if (data1.getChanpinId() == chanpin.getId() && a == b) {
                dataDisplays = data1.getSon();
              }
            }
            for (DataDisplay dataDisplay : dataDisplays) {
              List<SonData> sonDatas = dataDisplay.getSons();
              for (SonData sonData : sonDatas) {
                if (sonData.getZhuangtai() == 1 && sonData.getIsShown() == 1) i++;
                if (sonData.getZhuangtai() == 2 && sonData.getIsShown() == 1) j++;
              }
            }
            // System.out.println("i:"+i);
            // System.out.println("j:"+j);
          }
        }
        son.setId(chanpin.getId());
        son.setInstallation(chanpin.getInstallation());
        son.setSlaveId(chanpin.getSlaveId());
        if (i > 0) {
          son.setZhuangtai(1);
          Date date = new Date();
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          son.setTime(df.format(date));
        } else if (i == 0 && j > 0) {
          son.setZhuangtai(2);
          Date date = new Date();
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          son.setTime(df.format(date));
        } else {
          son.setZhuangtai(0);
          son.setTime(" ");
        }
        if (son.getZhuangtai() == 1 || son.getZhuangtai() == 2) {
          sons.add(son);
        }
      }
      if (sons.size() > 0) {
        all.setId(Integer.parseInt(chanpinId));
        all.setModel(leibie.getModel());
        all.setSon(sons);
        return all;
      } else return null;
    }
  }

  @RequestMapping(value = "/user/getChanpinDisplay", method = RequestMethod.POST)
  @ResponseBody
  public ChanpinDisplay getChanpinDisplay(String chanpinId)
      throws NumberFormatException, UnknownHostException, IOException, PortInUseException {
    if (chanpinId == "") chanpinId = "0";
    List<DataDisplayAdd> al = null;
    // CacheManage cache=CacheManage.getInstance();
    try {
      al = CacheManage.getDataDisplays();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ChanpinDisplay all = new ChanpinDisplay(null, null, null);
    Chanpin leibie = chanpinService.findChanpinById(Integer.parseInt(chanpinId));
    all.setId(Integer.parseInt(chanpinId));
    all.setModel(leibie.getModel());
    List<ChanpinDisplaySon> sons = new ArrayList<>();
    List<Chanpin> chanpins = chanpinService.getAllSons(Integer.parseInt(chanpinId));
    // System.out.println("chanpins:"+chanpins);
    for (Chanpin chanpin : chanpins) {
      ChanpinDisplaySon son = new ChanpinDisplaySon(null, null, null, null, null);
      int i = 0; // 用于记录异常部分出现的情况
      int j = 0; // 用于记录预警出现的情况
      Datameaning fatherdata = datameaningService.findDatameaningById(leibie.getDatameaningId());
      // System.out.println("fatherdata:"+fatherdata);
      List<Datameaning> datas = datameaningService.getDMAllSons(fatherdata.getId());
      for (Datameaning data : datas) {
        if (data.getType() == 1) {
          // List<DataDisplay> dataDisplays=getDisplayData(chanpin.getId(),data.getId());
          List<DataDisplay> dataDisplays = null;
          for (DataDisplayAdd data1 : al) {
            int a = data1.getRejisterId();
            int b = data.getId();
            int c = data1.getChanpinId();
            int d = chanpin.getId();
            if (c == d && a == b) {
              dataDisplays = data1.getSon();
            }
          }

          for (DataDisplay dataDisplay : dataDisplays) {
            List<SonData> sonDatas = dataDisplay.getSons();
            for (SonData sonData : sonDatas) {
              if (sonData.getZhuangtai() == 1 && sonData.getIsShown() == 1) i++;
              if (sonData.getZhuangtai() == 2 && sonData.getIsShown() == 1) j++;
            }
          }
        }
      }
      son.setId(chanpin.getId());
      son.setInstallation(chanpin.getInstallation());
      son.setSlaveId(chanpin.getSlaveId());
      if (i > 0) {
        son.setZhuangtai(1);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        son.setTime(df.format(date));
      } else if (i == 0 && j > 0) {
        son.setZhuangtai(2);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        son.setTime(df.format(date));
      } else {
        son.setZhuangtai(0);
        son.setTime(" ");
      }
      sons.add(son);
    }
    all.setSon(sons);
    return all;
  }

  @RequestMapping(value = "/user/getChanpinById", method = RequestMethod.POST)
  @ResponseBody
  public Chanpin getChanpinById(String id) {
    if (id.equals("") || id == null) return null;
    return chanpinService.findChanpinById(Integer.parseInt(id));
  }

  @RequestMapping(value = "/user/getAllInputRejister", method = RequestMethod.POST)
  @ResponseBody
  public List<Datameaning> getAllInputRejister(String chanpinLeibieId) {
    // System.out.println("chanpinLeibieId: "+chanpinLeibieId);
    return datameaningService.getAllInputRejister(Integer.parseInt(chanpinLeibieId));
  }

  @RequestMapping(value = "/user/getChanpinZhuangtai", method = RequestMethod.POST)
  @ResponseBody
  public Integer getChanpinZhuangtai(String chanpinId, String registerId) {
    if (chanpinId == null || registerId == null || chanpinId == "" || registerId == "") return 0;
    Integer zhuangtai = 0;
    List<DataDisplayAdd> all = null;
    // CacheManage cache=CacheManage.getInstance();
    try {
      all = CacheManage.getDataDisplays();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // List<DataDisplay> list=null;
    for (DataDisplayAdd data : all) {
      if (data.getChanpinId().equals(Integer.parseInt(chanpinId))
          && data.getRejisterId().equals(Integer.parseInt(registerId))) {
        // System.out.println("状态："+data.getIsConnect());
        // list=data.getSon();
        zhuangtai = data.getIsConnect();
        break;
      }
    }
    return zhuangtai;
  }

  @RequestMapping(value = "/user/getDisplayData", method = RequestMethod.POST)
  @ResponseBody
  public List<DataDisplay> getDisplayData(String chanpinId, String registerId) {
    // System.out.println("已调用一次数据显示！："+chanpinId+","+registerId);
    if (chanpinId == null || registerId == null || chanpinId == "" || registerId == "") return null;
    List<DataDisplayAdd> all = null;
    // CacheManage cache=CacheManage.getInstance();
    try {
      all = CacheManage.getDataDisplays();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    List<DataDisplay> list = null;
    for (DataDisplayAdd data : all) {
      if (data.getChanpinId() == Integer.parseInt(chanpinId)
          && data.getRejisterId() == Integer.parseInt(registerId)) {
        // System.out.println("状态："+data.getIsConnect());
        list = data.getSon();
        break;
      }
    }
    // System.out.println("界面显示的list数据:"+list);
    for (DataDisplay dis : list) {
      CopyOnWriteArrayList<SonData> son = dis.getSons();
      for (int i = 0; i < son.size(); i++) {
        if (son.get(i).getName().contains("年")) {
          SonData sonData = null;
          // System.out.println("son.get(i+1).getData().toString().trim()
          // "+son.get(i+1).getData().toString().trim());
          // System.out.println("是否相等：  "+son.get(i+1).getData().toString().trim().equals("00"));
          if (son.get(i).getData() != " "
              && !son.get(i + 1).getData().toString().trim().equals("00")) {
            sonData =
                new SonData(
                    son.get(i).getId(),
                    dis.getName() + "时间",
                    "20"
                        + son.get(i).getData()
                        + "年"
                        + son.get(i + 1).getData()
                        + "月"
                        + son.get(i + 2).getData()
                        + "日 "
                        + son.get(i + 3).getData()
                        + "时"
                        + son.get(i + 4).getData()
                        + "分"
                        + son.get(i + 5).getData()
                        + "秒",
                    0,
                    " ",
                    son.get(i).getIsShown(),
                    son.get(i).getIsRecord(),
                    0,
                    null);
          } else if (son.get(i + 1).getData().toString().trim().equals("00")) {
            // System.out.println("son.get(i+1).getData().toString().trim()
            // "+son.get(i+1).getData().toString().trim());
            sonData =
                new SonData(
                    son.get(i).getId(),
                    dis.getName() + "时间",
                    " ",
                    0,
                    " ",
                    son.get(i).getIsShown(),
                    son.get(i).getIsRecord(),
                    0,
                    null);
          } else {
            sonData =
                new SonData(
                    son.get(i).getId(),
                    dis.getName() + "时间",
                    " ",
                    0,
                    " ",
                    son.get(i).getIsShown(),
                    son.get(i).getIsRecord(),
                    0,
                    null);
          }
          for (int j = 0; j < 6; j++) son.remove(i);
          son.add(sonData);
          dis.setSons(son);
        }
      }
    }
    //	System.out.println("显示的data:"+list);
    return list;
  }

  @RequestMapping(value = "/user/getDisplayDataLength", method = RequestMethod.POST)
  @ResponseBody
  public Integer getDisplayDataLength(String chanpinId, String registerId) {
    // System.out.println("已调用一次数据显示！："+chanpinId+","+registerId);
    List<DataDisplay> a = getDisplayData(chanpinId, registerId);
    int len = 0;
    for (int i = 0; i < a.size(); i++) {
      List<SonData> sons = a.get(i).getSons();
      for (int j = 0; j < sons.size(); j++) {
        if (sons.get(j).getIsShown() == 1) len++;
        if (sons.get(j).getId().equals(641)) len++;
      }
    }
    return len;
  }

  @RequestMapping(value = "/user/getRecordShow", method = RequestMethod.POST)
  @ResponseBody
  public RecordShow getRecordShow(String chanpinId) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    RecordShow recordShow = new RecordShow(null, null);
    // 根据产品id，进而获得所有需要展示出来的解析表的id
    Chanpin chanpin = chanpinService.findChanpinById(Integer.parseInt(chanpinId));
    Datameaning datameaning = datameaningService.findDatameaningById(chanpin.getDatameaningId());
    List<Datameaning> erji = datameaningService.getDMAllSons(datameaning.getId()); // 获得所有的二级菜单
    List<Datameaning> fathers = new ArrayList<>(); // 获得所有的三级菜单
    List<Datameaning> datameanings = new ArrayList<>(); // 获得所有的四级菜单
    for (Datameaning one : erji) {
      if (one.getType() == 1) {
        List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
        for (Datameaning all1 : all) {
          fathers.add(all1);
        }
      }
    }
    for (Datameaning one : fathers) {
      List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
      for (Datameaning all1 : all) {
        Integer isShown = all1.getIsShown();
        if (isShown == null || isShown == 1) {
          datameanings.add(all1);
        }
      }
    }
    recordShow.setChanpinId(Integer.parseInt(chanpinId));
    List<RecordShow2> son1 = new ArrayList<>();
    for (Datameaning a : datameanings) {
      BaojingPanduan baojingPanduan =
          baojingPanduanService.selectById(Integer.parseInt(chanpinId), a.getId());
      ;
      // System.out.println("baojingPanduan:"+baojingPanduan);
      List<RecordData> recordDatas =
          datameaningService.getAllRecordData(Integer.parseInt(chanpinId), a.getId());
      RecordShow2 recordShow2 = new RecordShow2(null, null, null, null);
      recordShow2.setDatameaningId(a.getId());
      if (baojingPanduan != null) {
        recordShow2.setIsExistBaojing(1);
        recordShow2.setIsExistYujing(1);
      } else {
        recordShow2.setIsExistBaojing(0);
        recordShow2.setIsExistYujing(0);
      }
      List<RecordShow3> son2 = new ArrayList<>();
      for (RecordData recordData : recordDatas) {
        RecordShow3 recordShow3 = new RecordShow3(null, null, null, null, null);
        recordShow3.setData(recordData.getValue());
        if (recordShow2.getIsExistBaojing() == 1) {
          recordShow3.setBaojingData(baojingPanduan.getBaojingData());
        }
        if (recordShow2.getIsExistYujing() == 1) {
          recordShow3.setYujingData(baojingPanduan.getYujingData());
        }
        String time = sdf.format(recordData.getTime());
        recordShow3.setTime(time);
        son2.add(recordShow3);
      }
      recordShow2.setSons(son2);
      son1.add(recordShow2);
    }
    recordShow.setSons(son1);
    // System.out.println("显示的图标参数有："+recordShow);
    return recordShow;
  }

  @RequestMapping(value = "/user/getRecordShow1", method = RequestMethod.POST)
  @ResponseBody
  public RecordShow getRecordShow1(String chanpinId, String startDate, String endDate)
      throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    RecordShow recordShow = new RecordShow(null, null);
    // 根据产品id，进而获得所有需要展示出来的解析表的id
    Chanpin chanpin = chanpinService.findChanpinById(Integer.parseInt(chanpinId));
    Datameaning datameaning = datameaningService.findDatameaningById(chanpin.getDatameaningId());
    List<Datameaning> erji = datameaningService.getDMAllSons(datameaning.getId()); // 获得所有的二级菜单
    List<Datameaning> fathers = new ArrayList<>(); // 获得所有的三级菜单
    List<Datameaning> datameanings = new ArrayList<>(); // 获得所有的四级菜单
    for (Datameaning one : erji) {
      if (one.getType() == 1) {
        List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
        for (Datameaning all1 : all) {
          fathers.add(all1);
        }
      }
    }
    for (Datameaning one : fathers) {
      List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
      for (Datameaning all1 : all) {
        Integer isShown = all1.getIsShown();
        if (isShown == null || isShown == 1) {
          datameanings.add(all1);
        }
      }
    }
    recordShow.setChanpinId(Integer.parseInt(chanpinId));
    List<RecordShow2> son1 = new ArrayList<>();
    for (Datameaning a : datameanings) {
      BaojingPanduan baojingPanduan =
          baojingPanduanService.selectById(Integer.parseInt(chanpinId), a.getId());
      List<RecordData> recordDatas =
          datameaningService.getAllRecordData(Integer.parseInt(chanpinId), a.getId());
      RecordShow2 recordShow2 = new RecordShow2(null, null, null, null);
      recordShow2.setDatameaningId(a.getId());
      if (baojingPanduan != null) {
        recordShow2.setIsExistBaojing(1);
        recordShow2.setIsExistYujing(1);
      } else {
        recordShow2.setIsExistBaojing(0);
        recordShow2.setIsExistYujing(0);
      }
      List<RecordShow3> son2 = new ArrayList<>();
      for (RecordData recordData : recordDatas) {
        int compareTo1 = 1;
        int compareTo2 = -1;
        Date datetime = recordData.getTime();
        if (startDate != null && !startDate.equals("") && !startDate.equals("null")) {
          Date date1 = sdf1.parse(startDate);
          // Date date2=sdf1.parse(endDate);
          String datee = sdf1.format(datetime);
          Date date3 = sdf1.parse(datee);
          compareTo1 = date3.compareTo(date1);
          // compareTo2 = date3.compareTo(date2);
        }
        if (endDate != null && !endDate.equals("") && !endDate.equals("null")) {
          // Date date1=sdf1.parse(startDate);
          Date date2 = sdf1.parse(endDate);
          String datee = sdf1.format(datetime);
          Date date3 = sdf1.parse(datee);
          // compareTo1 = date3.compareTo(date1);
          compareTo2 = date3.compareTo(date2);
        }
        if (compareTo1 >= 0 && compareTo2 <= 0) {
          RecordShow3 recordShow3 = new RecordShow3(null, null, null, null, null);
          recordShow3.setData(recordData.getValue());
          if (recordShow2.getIsExistBaojing() == 1) {
            recordShow3.setBaojingData(baojingPanduan.getBaojingData());
          }
          if (recordShow2.getIsExistYujing() == 1) {
            recordShow3.setYujingData(baojingPanduan.getYujingData());
          }
          String time = sdf.format(recordData.getTime());
          recordShow3.setTime(time);
          son2.add(recordShow3);
        }
      }
      recordShow2.setSons(son2);
      son1.add(recordShow2);
    }
    recordShow.setSons(son1);
    // System.out.println("显示的图标参数有："+recordShow);
    return recordShow;
  }

  @RequestMapping(value = "/user/getChanAndDataShai", method = RequestMethod.POST)
  @ResponseBody
  public ChanAndDataShai getChanAndDataShai(
      String type, String chanpinId, String datameaningId, String startDate, String endDate) {
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    ChanAndDataShai data =
        datameaningService.getShaiRecords(type, ChanpinId, DatameaningId, startDate, endDate);
    // System.out.println("ChanAndDataShai: "+data);
    return data;
  }

  @RequestMapping(value = "/user/getDatameaningById", method = RequestMethod.POST)
  @ResponseBody
  public Datameaning getDatameaningById(String id) {
    // System.out.println("id: "+id);
    if (id.equals("") || id == null) return null;
    return datameaningService.findDatameaningById(Integer.parseInt(id));
  }

  @RequestMapping(value = "/user/recordShowInit", method = RequestMethod.POST)
  @ResponseBody
  public List<RecordShow> recordShowInit(String aids) {
    String[] strs = aids.split("-");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    Date atime = new Date();
    String time = sdf.format(atime);
    List<RecordShow> recordShows = new ArrayList<>();
    for (int i = 0; i < strs.length; i++) {
      RecordShow recordShow = new RecordShow(null, null);
      Integer chanpinId = Integer.parseInt(strs[i]);
      Chanpin chanpin = chanpinService.findChanpinById(chanpinId);
      Datameaning datameaning = datameaningService.findDatameaningById(chanpin.getDatameaningId());
      List<Datameaning> erji = datameaningService.getDMAllSons(datameaning.getId()); // 获得所有的二级菜单
      List<Datameaning> fathers = new ArrayList<>(); // 获得所有的三级菜单
      List<Datameaning> datameanings = new ArrayList<>(); // 获得所有的四级菜单
      for (Datameaning one : erji) {
        if (one.getType() == 1) {
          List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
          for (Datameaning all1 : all) {
            fathers.add(all1);
          }
        }
      }
      for (Datameaning one : fathers) {
        List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
        for (Datameaning all1 : all) {
          Integer isShown = all1.getIsShown();
          Integer isPaint = all1.getIsPaint();
          if ((isShown == null || isShown == 1) && (isPaint == null || isPaint == 1)) {
            datameanings.add(all1);
          }
        }
      }

      recordShow.setChanpinId(chanpinId);
      List<RecordShow2> son1 = new ArrayList<>();
      List<DataDisplayAdd> all = null;
      // CacheManage cache=CacheManage.getInstance();
      try {
        all = CacheManage.getDataDisplays();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      List<Datameaning> dameans = datameaningService.getAllInputRejister(leibie.getId());
      List<DataDisplay> list = null;
      Integer dameanid = dameans.get(0).getId();
      for (DataDisplayAdd data : all) {
        int a1 = data.getChanpinId();
        int a2 = data.getRejisterId();
        if (a1 == chanpinId && a2 == dameanid) {
          list = data.getSon();
        }
      }

      //  System.out.println(list);

      for (Datameaning a : datameanings) {
        // 开始获得要展示的首次数据
        Integer aId = a.getId();
        RecordShow2 recordShow2 = new RecordShow2(null, null, null, null);
        recordShow2.setDatameaningId(a.getId());
        BaojingPanduan baojingPanduan = baojingPanduanService.selectById(chanpinId, a.getId());
        if (baojingPanduan != null) {
          recordShow2.setIsExistBaojing(1);
          recordShow2.setIsExistYujing(1);
        } else {
          recordShow2.setIsExistBaojing(0);
          recordShow2.setIsExistYujing(0);
        }
        List<RecordShow3> son2 = new ArrayList<>();

        for (DataDisplay datadis : list) {
          List<SonData> sons = datadis.getSons();
          for (SonData son : sons) {
            Integer sonId = son.getId();
            // System.out.println("aid:"+aId);
            // System.out.println("sonId:"+sonId);
            if (sonId.equals(aId)) {
              RecordShow3 recordShow3 = new RecordShow3(null, null, null, null, null);
              if (son.getData() instanceof String) {
                String data = (String) son.getData();
                if (!data.equals("未读取到数据!") && (!data.equals(""))) {
                  String ph = Split.getNumber(data).trim();
                  Double dou = 0.0;
                  if (!ph.equals("")) {
                    dou = Double.parseDouble(ph);
                  }

                  recordShow3.setData(dou);
                } else {
                  Double doub = 0.0;
                  recordShow3.setData(doub);
                }
              } else if (son.getData() instanceof Integer) {
                Integer ddd = (Integer) son.getData();
                Double dd = ddd.doubleValue();
                recordShow3.setData(dd);
              } else {
                Double dd = (Double) son.getData();
                recordShow3.setData(dd);
              }
              // System.out.println("data"+data);

              recordShow3.setTime(time);
              if (recordShow2.getIsExistBaojing() == 1) {
                recordShow3.setBaojingData(baojingPanduan.getBaojingData());
              }
              if (recordShow2.getIsExistYujing() == 1) {
                recordShow3.setYujingData(baojingPanduan.getYujingData());
              }
              son2.add(recordShow3);
            }
          }
        }
        recordShow2.setSons(son2);
        son1.add(recordShow2);
      }
      recordShow.setSons(son1);
      recordShows.add(recordShow);
    }
    // System.out.println(recordShows);
    return recordShows;
  }

  @RequestMapping(value = "/user/getAllChanpinLeibies", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Chanpin> getAllChanpinLeibies() { // 获得所有位置区域的下拉列表框
    List<Chanpin> a = chanpinService.getAllSons(0);
    List<Chanpin> list = new ArrayList<>();
    for (Chanpin location : a) {
      List<Chanpin> losons = chanpinService.getAllSons(location.getId());
      for (Chanpin son : losons) {
        boolean boo = true;
        for (Chanpin li : list) {
          if (li.getName().equals(son.getName())) {
            boo = false;
            break;
          }
        }
        if (boo) {
          list.add(son);
        }
      }
    }
    return list;
  }

  @RequestMapping(value = "/user/getAllChanpins", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Chanpin> getAllChanpins(String Protype, String select2) { // 获得所有的类别信息
    // System.out.println("dataTYPE:"+datatype);
    // System.out.println("Protype:"+Protype);
    // System.out.println("select2:"+select2);
    if (select2.equals("0")
        || select2 == null
        || select2.equals("null")
        || select2 == null
        || select2.equals("null")) {
      List<Chanpin> all = chanpinService.queryAll();
      List<Chanpin> all1 = new ArrayList<>();
      for (Chanpin chanpin : all) {
        if (chanpin.getIsparent() == 0) {
          all1.add(chanpin);
        }
      }
      // System.out.println(all1);
      return all1;
    } else {
      int select = Integer.parseInt(select2);

      if (Protype.equals("区域")) {
        List<Chanpin> leibies = chanpinService.getAllSons(select);
        List<Chanpin> chanpins = new ArrayList<>();
        for (Chanpin leibie : leibies) {
          List<Chanpin> sons = chanpinService.getAllSons(leibie.getId());
          for (Chanpin son : sons) {
            chanpins.add(son);
          }
        }
        // System.out.println(chanpins);
        return chanpins;
      } else {
        List<Chanpin> chanpins = new ArrayList<>();
        List<Chanpin> sons = chanpinService.getAllSons(select);
        for (Chanpin son : sons) {
          chanpins.add(son);
        }
        // System.out.println(chanpins);
        return chanpins;
      }
    }
    // return null;
  }

  @RequestMapping(value = "/user/getAllLocations", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Chanpin> getAllLocations() { // 获得所有位置区域的下拉列表框
    return chanpinService.getAllSons(0);
  }

  @RequestMapping(value = "/user/getAllLeibies", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Chanpin> getAllLeibies(String locationId) { // 获得所有的类别信息
    if (locationId == "" || locationId == null) {
      return null;
    } else {
      List<Chanpin> a = chanpinService.getAllSons(Integer.parseInt(locationId));
      // System.out.println("类别："+a);
      return a;
    }
  }

  @RequestMapping(value = "/user/getAllChanpin", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Chanpin> getAllChanpin(String locationId, String leibieId) { // 获得所有的产品信息
    if (locationId == "" || locationId == null || leibieId == "" || leibieId == null) {
      return null;
    } else {
      return chanpinService.getAllSons(Integer.parseInt(leibieId));
    }
  }

  @RequestMapping(value = "/user/getAllCanshu", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Datameaning> getAllCanshu(
      String locationId, String leibieId, String chanpinId) { // 获得所有的解析表中需要被记录的协议的信息
    // System.out.println("locationId:"+locationId+" leibieId:"+leibieId+" chanpinId:"+chanpinId);
    if (locationId == ""
        || locationId == null
        || leibieId == ""
        || leibieId == null
        || chanpinId == ""
        || chanpinId == null) {
      return null;
    } else {
      Chanpin leibie = chanpinService.findChanpinById(Integer.parseInt(leibieId));
      Datameaning par = datameaningService.findDatameaningById(leibie.getDatameaningId());
      List<Datameaning> erji = datameaningService.getDMAllSons(par.getId()); // 获得所有的二级菜单
      List<Datameaning> fathers = new ArrayList<>(); // 获得所有的三级菜单
      List<Datameaning> datameanings = new ArrayList<>(); // 获得所有的四级菜单
      for (Datameaning one : erji) {
        if (one.getType() == 1) {
          List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
          for (Datameaning all1 : all) {
            fathers.add(all1);
          }
        }
      }
      for (Datameaning one : fathers) {
        List<Datameaning> all = datameaningService.getDMAllSons(one.getId());
        for (Datameaning all1 : all) {
          datameanings.add(all1);
        }
      }
      List<Datameaning> a = new ArrayList<>();
      // System.out.println("all:  "+all1);
      for (Datameaning datameaning : datameanings) {
        Integer isShown = datameaning.getIsShown();
        if (isShown == null) {
          isShown = 1;
        }
        if (datameaning.getIsparent() == 0 && (isShown != 0)) {
          a.add(datameaning);
        }
      }
      // System.out.println("最终得到的被记录的： "+a);
      return a;
    }
  }

  @RequestMapping(value = "/user/showResistanceRecord", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> getAllResistanceRecord(int page, int limit) {
    List<ActualResistance> all = actualResistanceService.getAll();
    List<ActualResistanceDto> all1 = new ArrayList<>();
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(all.size(), page * limit);
    List<ActualResistance> alll = all.subList(fromIndex, toIndex);
    Integer size = all.size();
    for (ActualResistance actualResistance : alll) {
      ActualResistanceDto a = new ActualResistanceDto(null, null, null, null, null, null, null);
      a.setId(actualResistance.getId());
      a.setChanpinId(actualResistance.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(actualResistance.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      // a.setDatameaningId(baojingRecord.getDatameaningId());
      // a.setChanpinName(chanpinName);
      // a.setName(baojingRecord.getName());
      a.setTime(actualResistance.getTime());
      a.setValue(actualResistance.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      all1.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", all1);
    return map;
  }

  @RequestMapping(value = "/user/findResistance", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> findResistance(
      String locationId,
      String leibieId,
      String chanpinId,
      String startDate,
      String endDate,
      int page,
      int limit,
      HttpServletRequest request) {
    Integer LocationId = null;
    Integer LeibieId = null;
    Integer ChanpinId = null;

    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }

    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    List<ActualResistance> all =
        actualResistanceService.findAll(LocationId, LeibieId, ChanpinId, startDate, endDate);
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(all.size(), page * limit);
    List<ActualResistance> all1 = all.subList(fromIndex, toIndex);
    if (toIndex.equals(0)) {
      fromIndex = 0;
    }
    Integer size = all.size();
    List<ActualResistanceDto> alll = new ArrayList<>();
    for (ActualResistance actualResistance : all1) {
      ActualResistanceDto a = new ActualResistanceDto(null, null, null, null, null, null, null);
      a.setId(actualResistance.getId());
      a.setChanpinId(actualResistance.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(actualResistance.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      // a.setDatameaningId(baojingRecord.getDatameaningId());
      // a.setChanpinName(chanpinName);
      // a.setName(baojingRecord.getName());
      a.setTime(actualResistance.getTime());
      a.setValue(actualResistance.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      alll.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", alll);
    return map;
  }

  @RequestMapping(value = "/user/showBaojingRecord", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> getAllBaojingRecord(int page, int limit) {
    List<BaojingRecord> all = baojingRecordService.findAll(null, null, null, null, null, null);
    List<BaojingRecordDto> all1 = new ArrayList<>();
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(all.size(), page * limit);
    List<BaojingRecord> alll = all.subList(fromIndex, toIndex);
    Integer size = all.size();
    for (BaojingRecord baojingRecord : alll) {
      BaojingRecordDto a =
          new BaojingRecordDto(null, null, null, null, null, null, null, null, null);
      a.setId(baojingRecord.getId());
      a.setChanpinId(baojingRecord.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(baojingRecord.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      a.setDatameaningId(baojingRecord.getDatameaningId());
      a.setName(baojingRecord.getName());
      a.setTime(baojingRecord.getTime());
      a.setValue(baojingRecord.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      all1.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", all1);
    return map;
  }

  @RequestMapping(value = "/user/findBaojingRecord", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> findBaojingRecord(
      String locationId,
      String leibieId,
      String chanpinId,
      String datameaningId,
      String startDate,
      String endDate,
      int page,
      int limit,
      HttpServletRequest request) {
    Integer LocationId = null;
    Integer LeibieId = null;
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    List<BaojingRecord> all =
        baojingRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(all.size(), page * limit);
    List<BaojingRecord> all1 = all.subList(fromIndex, toIndex);
    Integer size = all.size();
    List<BaojingRecordDto> alll = new ArrayList<>();
    for (BaojingRecord baojingRecord : all1) {
      BaojingRecordDto a =
          new BaojingRecordDto(null, null, null, null, null, null, null, null, null);
      a.setId(baojingRecord.getId());
      a.setChanpinId(baojingRecord.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(baojingRecord.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      a.setDatameaningId(baojingRecord.getDatameaningId());
      // Datameaning
      // datamean=datameaningService.findDatameaningById(baojingRecord.getDatameaningId());
      // a.setDatameaningName(datamean.getName());
      a.setName(baojingRecord.getName());
      a.setTime(baojingRecord.getTime());
      a.setValue(baojingRecord.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      alll.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", alll);
    return map;
  }

  @RequestMapping(value = "/user/showInfoRecord", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> getAllInfoRecord(int page, int limit) {
    List<InfoRecord> all = infoRecordService.findAll(null, null, null, null, null, null);
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(all.size(), page * limit);
    List<InfoRecord> alll = all.subList(fromIndex, toIndex);
    List<InfoRecordDto> all1 = new ArrayList<>();
    Integer size = all.size();
    for (InfoRecord infoRecord : alll) {
      InfoRecordDto a = new InfoRecordDto(null, null, null, null, null, null, null, null, null);
      a.setId(infoRecord.getId());
      a.setChanpinId(infoRecord.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(infoRecord.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      a.setDatameaningId(infoRecord.getDatameaningId());
      a.setName(infoRecord.getName());
      a.setTime(infoRecord.getTime());
      a.setValue(infoRecord.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      all1.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", all1);
    return map;
  }

  @RequestMapping(value = "/user/findInfoRecord", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> findInfoRecord(
      String locationId,
      String leibieId,
      String chanpinId,
      String datameaningId,
      String startDate,
      String endDate,
      int page,
      int limit,
      HttpServletRequest request) {
    Integer LocationId = null;
    Integer LeibieId = null;
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    List<InfoRecord> alll =
        infoRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(alll.size(), page * limit);
    List<InfoRecord> all = alll.subList(fromIndex, toIndex);
    Integer size = alll.size();
    List<InfoRecordDto> all1 = new ArrayList<>();
    for (InfoRecord infoRecord : all) {
      InfoRecordDto a = new InfoRecordDto(null, null, null, null, null, null, null, null, null);
      a.setId(infoRecord.getId());
      a.setChanpinId(infoRecord.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(infoRecord.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      a.setDatameaningId(infoRecord.getDatameaningId());
      a.setName(infoRecord.getName());
      a.setTime(infoRecord.getTime());
      a.setValue(infoRecord.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      all1.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", all1);
    return map;
  }

  @RequestMapping(value = "/user/showTroubleRecord", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> getAllTroubleRecord(int page, int limit) {
    List<TroubleRecord> all = troubleRecordService.findAll(null, null, null, null, null, null);
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(all.size(), page * limit);
    List<TroubleRecord> alll = all.subList(fromIndex, toIndex);
    List<TroubleRecordDto> all1 = new ArrayList<>();
    Integer size = all.size();
    for (TroubleRecord troubleRecord : alll) {
      TroubleRecordDto a =
          new TroubleRecordDto(null, null, null, null, null, null, null, null, null);
      a.setId(troubleRecord.getId());
      a.setChanpinId(troubleRecord.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(troubleRecord.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      a.setDatameaningId(troubleRecord.getDatameaningId());
      a.setName(troubleRecord.getName());
      a.setTime(troubleRecord.getTime());
      a.setValue(troubleRecord.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      all1.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", all1);
    return map;
  }

  @RequestMapping(value = "/user/findTroubleRecord", produces = "application/json;charset=utf-8")
  @ResponseBody
  public Map<String, Object> findTroubleRecord(
      String locationId,
      String leibieId,
      String chanpinId,
      String datameaningId,
      String startDate,
      String endDate,
      int page,
      int limit,
      HttpServletRequest request) {
    Integer LocationId = null;
    Integer LeibieId = null;
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    List<TroubleRecord> alll =
        troubleRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    Integer fromIndex = (page - 1) * limit;
    Integer toIndex = Math.min(alll.size(), page * limit);
    List<TroubleRecord> all = alll.subList(fromIndex, toIndex);
    Integer size = alll.size();
    List<TroubleRecordDto> all1 = new ArrayList<>();
    for (TroubleRecord troubleRecord : all) {
      TroubleRecordDto a =
          new TroubleRecordDto(null, null, null, null, null, null, null, null, null);
      a.setId(troubleRecord.getId());
      a.setChanpinId(troubleRecord.getChanpinId());
      Chanpin chanpin = chanpinService.findChanpinById(troubleRecord.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Chanpin location = chanpinService.findChanpinById(leibie.getPid());
      a.setChanpinName(chanpin.getName());
      a.setDatameaningId(troubleRecord.getDatameaningId());
      a.setName(troubleRecord.getName());
      a.setTime(troubleRecord.getTime());
      a.setValue(troubleRecord.getValue());
      if (leibie != null) {
        a.setLeibieName(leibie.getName());
      }
      if (location != null) {
        a.setLocationName(location.getName());
      }
      all1.add(a);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("code", 0);
    map.put("msg", "");
    map.put("count", size);
    map.put("data", all1);
    return map;
  }

  @RequestMapping(value = "/user/exportExcel", method = RequestMethod.GET)
  public void exportExcel(HttpServletResponse response, HttpServletRequest request) {
    String filename = "报警信息.xlsx";
    String locationId = request.getParameter("locationId");
    String leibieId = request.getParameter("leibieId");
    String chanpinId = request.getParameter("chanpinId");
    String datameaningId = request.getParameter("datameaningId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    Integer LocationId = null;
    Integer LeibieId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    float[] widths = {100, 100, 100, 100, 100, 100, 100, 100};
    List<BaojingRecord> list =
        baojingRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("报警信息");
    String[] s = {"位置", "产品类别", "产品名", "安装位置", "地址码", "对应信息", "实际显示值", "报警时间"};
    XSSFRow row = sheet.createRow(0);
    sheet.setColumnWidth(9, 100 * 100);
    for (int j = 0; j < 8; j++) {
      row.createCell(j).setCellValue(s[j]);
    }

    for (int i = 0; i < list.size(); i++) {
      XSSFRow row1 = sheet.createRow(i + 1);
      BaojingRecord info = list.get(i);
      Chanpin chanpin = chanpinService.findChanpinById(info.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Datameaning datameaning = datameaningService.findDatameaningById(info.getDatameaningId());
      row1.createCell(0).setCellValue(chanpin.getLocation());
      row1.createCell(1).setCellValue(leibie.getName());
      row1.createCell(2).setCellValue(chanpin.getName());
      row1.createCell(3).setCellValue(chanpin.getInstallation());
      Integer slaveId = chanpin.getSlaveId();
      if (slaveId != null) {
        row1.createCell(4).setCellValue(slaveId);
      } else {
        row1.createCell(4).setCellValue("");
      }
      row1.createCell(5).setCellValue(datameaning.getName());
      row1.createCell(6).setCellValue(list.get(i).getValue());
      row1.createCell(7).setCellValue(list.get(i).getTime());
    }
    try {
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      wb.write(response.getOutputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      response.getOutputStream().close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/user/exportExcel1", method = RequestMethod.GET)
  public void exportExcel1(HttpServletResponse response, HttpServletRequest request) {
    String filename = "重要记录信息.xlsx";
    String locationId = request.getParameter("locationId");
    String leibieId = request.getParameter("leibieId");
    String chanpinId = request.getParameter("chanpinId");
    String datameaningId = request.getParameter("datameaningId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    Integer LocationId = null;
    Integer LeibieId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    float[] widths = {100, 100, 100, 100, 100, 100, 100, 100};
    List<InfoRecord> list =
        infoRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("重要信息");
    String[] s = {"位置", "产品类别", "产品名", "安装位置", "地址码", "对应信息", "实际显示值", "报警时间"};
    XSSFRow row = sheet.createRow(0);
    sheet.setColumnWidth(9, 100 * 100);
    for (int j = 0; j < 8; j++) {
      row.createCell(j).setCellValue(s[j]);
    }

    for (int i = 0; i < list.size(); i++) {
      XSSFRow row1 = sheet.createRow(i + 1);
      InfoRecord info = list.get(i);
      Chanpin chanpin = chanpinService.findChanpinById(info.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Datameaning datameaning = datameaningService.findDatameaningById(info.getDatameaningId());
      row1.createCell(0).setCellValue(chanpin.getLocation());
      row1.createCell(1).setCellValue(leibie.getName());
      row1.createCell(2).setCellValue(chanpin.getName());
      row1.createCell(3).setCellValue(chanpin.getInstallation());
      Integer slaveId = chanpin.getSlaveId();
      if (slaveId != null) {
        row1.createCell(4).setCellValue(slaveId);
      } else {
        row1.createCell(4).setCellValue("");
      }
      row1.createCell(5).setCellValue(datameaning.getName());
      row1.createCell(6).setCellValue(list.get(i).getValue());
      row1.createCell(7).setCellValue(list.get(i).getTime());
    }
    try {
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      wb.write(response.getOutputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      response.getOutputStream().close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/user/exportExcel2", method = RequestMethod.GET)
  public void exportExcel2(HttpServletResponse response, HttpServletRequest request) {
    String filename = "故障信息.xlsx";
    String locationId = request.getParameter("locationId");
    String leibieId = request.getParameter("leibieId");
    String chanpinId = request.getParameter("chanpinId");
    String datameaningId = request.getParameter("datameaningId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    Integer LocationId = null;
    Integer LeibieId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    float[] widths = {100, 100, 100, 100, 100, 100, 100, 100};
    List<TroubleRecord> list =
        troubleRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("故障信息");
    String[] s = {"位置", "产品类别", "产品名", "安装位置", "地址码", "对应信息", "实际显示值", "报警时间"};
    XSSFRow row = sheet.createRow(0);
    sheet.setColumnWidth(9, 100 * 100);
    for (int j = 0; j < 8; j++) {
      row.createCell(j).setCellValue(s[j]);
    }

    for (int i = 0; i < list.size(); i++) {
      XSSFRow row1 = sheet.createRow(i + 1);
      TroubleRecord info = list.get(i);
      Chanpin chanpin = chanpinService.findChanpinById(info.getChanpinId());
      Chanpin leibie = chanpinService.findChanpinById(chanpin.getPid());
      Datameaning datameaning = datameaningService.findDatameaningById(info.getDatameaningId());
      row1.createCell(0).setCellValue(chanpin.getLocation());
      row1.createCell(1).setCellValue(leibie.getName());
      row1.createCell(2).setCellValue(chanpin.getName());
      row1.createCell(3).setCellValue(chanpin.getInstallation());
      Integer slaveId = chanpin.getSlaveId();
      if (slaveId != null) {
        row1.createCell(4).setCellValue(slaveId);
      } else {
        row1.createCell(4).setCellValue("");
      }
      row1.createCell(5).setCellValue(datameaning.getName());
      row1.createCell(6).setCellValue(list.get(i).getValue());
      row1.createCell(7).setCellValue(list.get(i).getTime());
    }
    try {
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      wb.write(response.getOutputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      response.getOutputStream().close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/user/exportPdf", method = RequestMethod.GET)
  public void exportPdf(HttpServletResponse response, HttpServletRequest request) throws Exception {
    String locationId = request.getParameter("locationId");
    String leibieId = request.getParameter("leibieId");
    String chanpinId = request.getParameter("chanpinId");
    String datameaningId = request.getParameter("datameaningId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    Integer LocationId = null;
    Integer LeibieId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    float[] widths = {100, 100, 100, 100, 100, 100, 100, 100};
    List<BaojingRecord> list =
        baojingRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    ByteArrayOutputStream fos = new ByteArrayOutputStream();
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, fos);
    writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
    document.setPageSize(PageSize.A4); // 设置A4
    document.open();
    PdfPTable table = new PdfPTable(widths);
    table.setTotalWidth(458);
    table.setHorizontalAlignment(Element.ALIGN_LEFT);
    String[] s = {"位置", "产品类别", "产品名", "安装位置", "地址码", "对应信息", "实际显示值", "报警时间"};
    for (int j = 0; j < 8; j++) {
      PdfPCell pdfCell1 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph1 = new Paragraph(s[j], getPdfChineseFont());
      pdfCell1.setPhrase(paragraph1);
      table.addCell(pdfCell1);
    }
    for (int i = 0; i < list.size(); i++) {
      BaojingRecord info = list.get(i);
      PdfPCell pdfCell1 = new PdfPCell(); // 表格的单元格
      Chanpin chanpin = chanpinService.findChanpinById(info.getChanpinId());
      Paragraph paragraph1 = new Paragraph(chanpin.getLocation(), getPdfChineseFont());

      pdfCell1.setPhrase(paragraph1);

      table.addCell(pdfCell1);

      PdfPCell pdfCell2 = new PdfPCell(); // 表格的单元格
      Chanpin Leibie = chanpinService.findChanpinById(chanpin.getPid());
      Paragraph paragraph2 = new Paragraph(Leibie.getName(), getPdfChineseFont());

      pdfCell2.setPhrase(paragraph2);

      table.addCell(pdfCell2);

      PdfPCell pdfCell3 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph3 = new Paragraph(chanpin.getName(), getPdfChineseFont());

      pdfCell3.setPhrase(paragraph3);

      table.addCell(pdfCell3);

      PdfPCell pdfCell4 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph4 = new Paragraph(chanpin.getInstallation(), getPdfChineseFont());

      pdfCell4.setPhrase(paragraph4);

      table.addCell(pdfCell4);

      PdfPCell pdfCell5 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph5 = new Paragraph(chanpin.getSlaveId() + "", getPdfChineseFont());

      pdfCell5.setPhrase(paragraph5);

      table.addCell(pdfCell5);

      PdfPCell pdfCell6 = new PdfPCell(); // 表格的单元格

      Datameaning a = datameaningService.findDatameaningById(info.getDatameaningId());
      Paragraph paragraph6 = new Paragraph(a.getName(), getPdfChineseFont());

      pdfCell6.setPhrase(paragraph6);

      table.addCell(pdfCell6);

      PdfPCell pdfCell7 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph7 = new Paragraph(info.getValue() + "", getPdfChineseFont());

      pdfCell7.setPhrase(paragraph7);

      table.addCell(pdfCell7);

      PdfPCell pdfCell8 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph8 = new Paragraph(info.getTime(), getPdfChineseFont());

      pdfCell8.setPhrase(paragraph8);

      table.addCell(pdfCell8);
    }
    document.add(table);
    document.close();
    fos.close();
    OutputStream out = null;
    try {
      //设置请求返回类型
      response.setHeader("Content-Disposition", "attachment; filename=" + new String("报警信息.pdf".getBytes(), StandardCharsets.ISO_8859_1));
      response.setContentLength(fos.size());
      out = response.getOutputStream();
      fos.writeTo(out);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }


  }

  @RequestMapping(value = "/user/exportPdf1", method = RequestMethod.GET)
  public void exportPdf1(HttpServletResponse response, HttpServletRequest request)
      throws Exception {
    String locationId = request.getParameter("locationId");
    String leibieId = request.getParameter("leibieId");
    String chanpinId = request.getParameter("chanpinId");
    String datameaningId = request.getParameter("datameaningId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    Integer LocationId = null;
    Integer LeibieId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    float[] widths = {100, 100, 100, 100, 100, 100, 100, 100};
    List<InfoRecord> list =
        infoRecordService.findAll(
            LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    ByteArrayOutputStream fos = new ByteArrayOutputStream();
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, fos);
    writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
    document.setPageSize(PageSize.A4); // 设置A4
    document.open();
    PdfPTable table = new PdfPTable(widths);
    table.setTotalWidth(458);
    table.setHorizontalAlignment(Element.ALIGN_LEFT);
    String[] s = {"位置", "产品类别", "产品名", "安装位置", "地址码", "对应信息", "实际显示值", "报警时间"};
    for (int j = 0; j < 8; j++) {
      PdfPCell pdfCell1 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph1 = new Paragraph(s[j], getPdfChineseFont());
      pdfCell1.setPhrase(paragraph1);
      table.addCell(pdfCell1);
    }
    for (int i = 0; i < list.size(); i++) {
      InfoRecord info = list.get(i);
      PdfPCell pdfCell1 = new PdfPCell(); // 表格的单元格
      Chanpin chanpin = chanpinService.findChanpinById(info.getChanpinId());
      Paragraph paragraph1 = new Paragraph(chanpin.getLocation(), getPdfChineseFont());

      pdfCell1.setPhrase(paragraph1);

      table.addCell(pdfCell1);

      PdfPCell pdfCell2 = new PdfPCell(); // 表格的单元格
      Chanpin Leibie = chanpinService.findChanpinById(chanpin.getPid());
      Paragraph paragraph2 = new Paragraph(Leibie.getName(), getPdfChineseFont());

      pdfCell2.setPhrase(paragraph2);

      table.addCell(pdfCell2);

      PdfPCell pdfCell3 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph3 = new Paragraph(chanpin.getName(), getPdfChineseFont());

      pdfCell3.setPhrase(paragraph3);

      table.addCell(pdfCell3);

      PdfPCell pdfCell4 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph4 = new Paragraph(chanpin.getInstallation(), getPdfChineseFont());

      pdfCell4.setPhrase(paragraph4);

      table.addCell(pdfCell4);

      PdfPCell pdfCell5 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph5 = new Paragraph(chanpin.getSlaveId() + "", getPdfChineseFont());

      pdfCell5.setPhrase(paragraph5);

      table.addCell(pdfCell5);

      PdfPCell pdfCell6 = new PdfPCell(); // 表格的单元格

      Datameaning a = datameaningService.findDatameaningById(info.getDatameaningId());
      Paragraph paragraph6 = new Paragraph(a.getName(), getPdfChineseFont());

      pdfCell6.setPhrase(paragraph6);

      table.addCell(pdfCell6);

      PdfPCell pdfCell7 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph7 = new Paragraph(info.getValue() + "", getPdfChineseFont());

      pdfCell7.setPhrase(paragraph7);

      table.addCell(pdfCell7);

      PdfPCell pdfCell8 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph8 = new Paragraph(info.getTime(), getPdfChineseFont());

      pdfCell8.setPhrase(paragraph8);

      table.addCell(pdfCell8);
    }
    document.add(table);
    document.close();
    fos.close();
    OutputStream out = null;
    try {
      //设置请求返回类型
      response.setHeader("Content-Disposition", "attachment; filename=" + new String("重要记录信息.pdf".getBytes(), StandardCharsets.ISO_8859_1));
      response.setContentLength(fos.size());
      out = response.getOutputStream();
      fos.writeTo(out);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }

  @RequestMapping(value = "/user/exportPdf2", method = RequestMethod.GET)
  public void exportPdf2(HttpServletResponse response, HttpServletRequest request)
      throws Exception {
    String locationId = request.getParameter("locationId");
    String leibieId = request.getParameter("leibieId");
    String chanpinId = request.getParameter("chanpinId");
    String datameaningId = request.getParameter("datameaningId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    Integer ChanpinId = null;
    Integer DatameaningId = null;
    Integer LocationId = null;
    Integer LeibieId = null;
    if (locationId != null && !locationId.equals("")) {
      LocationId = Integer.parseInt(locationId);
    }
    if (leibieId != null && !leibieId.equals("")) {
      LeibieId = Integer.parseInt(leibieId);
    }
    if (chanpinId != null && !chanpinId.equals("")) {
      ChanpinId = Integer.parseInt(chanpinId);
    }
    if (datameaningId != null && !datameaningId.equals("")) {
      DatameaningId = Integer.parseInt(datameaningId);
    }
    if (startDate.equals("")) {
      startDate = null;
    }
    if (endDate.equals("")) {
      endDate = null;
    }
    float[] widths = {100, 100, 100, 100, 100, 100, 100, 100};
    List<TroubleRecord> list =
            troubleRecordService.findAll(
                    LocationId, LeibieId, ChanpinId, DatameaningId, startDate, endDate);
    ByteArrayOutputStream fos = new ByteArrayOutputStream();
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, fos);
    writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
    document.setPageSize(PageSize.A4); // 设置A4
    document.open();
    PdfPTable table = new PdfPTable(widths);
    table.setTotalWidth(458);
    table.setHorizontalAlignment(Element.ALIGN_LEFT);
    String[] s = {"位置", "产品类别", "产品名", "安装位置", "地址码", "对应信息", "实际显示值", "报警时间"};
    for (int j = 0; j < 8; j++) {
      PdfPCell pdfCell1 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph1 = new Paragraph(s[j], getPdfChineseFont());
      pdfCell1.setPhrase(paragraph1);
      table.addCell(pdfCell1);
    }
    for (int i = 0; i < list.size(); i++) {
      TroubleRecord info = list.get(i);
      PdfPCell pdfCell1 = new PdfPCell(); // 表格的单元格
      Chanpin chanpin = chanpinService.findChanpinById(info.getChanpinId());
      Paragraph paragraph1 = new Paragraph(chanpin.getLocation(), getPdfChineseFont());

      pdfCell1.setPhrase(paragraph1);

      table.addCell(pdfCell1);

      PdfPCell pdfCell2 = new PdfPCell(); // 表格的单元格
      Chanpin Leibie = chanpinService.findChanpinById(chanpin.getPid());
      Paragraph paragraph2 = new Paragraph(Leibie.getName(), getPdfChineseFont());

      pdfCell2.setPhrase(paragraph2);

      table.addCell(pdfCell2);

      PdfPCell pdfCell3 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph3 = new Paragraph(chanpin.getName(), getPdfChineseFont());

      pdfCell3.setPhrase(paragraph3);

      table.addCell(pdfCell3);

      PdfPCell pdfCell4 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph4 = new Paragraph(chanpin.getInstallation(), getPdfChineseFont());

      pdfCell4.setPhrase(paragraph4);

      table.addCell(pdfCell4);

      PdfPCell pdfCell5 = new PdfPCell(); // 表格的单元格
      Paragraph paragraph5 = new Paragraph(chanpin.getSlaveId() + "", getPdfChineseFont());

      pdfCell5.setPhrase(paragraph5);

      table.addCell(pdfCell5);

      PdfPCell pdfCell6 = new PdfPCell(); // 表格的单元格

      Datameaning a = datameaningService.findDatameaningById(info.getDatameaningId());
      Paragraph paragraph6 = new Paragraph(a.getName(), getPdfChineseFont());

      pdfCell6.setPhrase(paragraph6);

      table.addCell(pdfCell6);

      PdfPCell pdfCell7 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph7 = new Paragraph(info.getValue() + "", getPdfChineseFont());

      pdfCell7.setPhrase(paragraph7);

      table.addCell(pdfCell7);

      PdfPCell pdfCell8 = new PdfPCell(); // 表格的单元格

      Paragraph paragraph8 = new Paragraph(info.getTime(), getPdfChineseFont());

      pdfCell8.setPhrase(paragraph8);

      table.addCell(pdfCell8);
    }
    document.add(table);
    document.close();
    fos.close();
    OutputStream out = null;
    try {
      //设置请求返回类型
      response.setHeader("Content-Disposition", "attachment; filename=" + new String("故障信息.pdf".getBytes(), StandardCharsets.ISO_8859_1));
      response.setContentLength(fos.size());
      out = response.getOutputStream();
      fos.writeTo(out);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }

  @RequestMapping(value = "/user/getAirDeviseData", method = RequestMethod.POST)
  @ResponseBody
  public com.alibaba.fastjson.JSONArray getAirDeviseData() {
    String airDevise = "[{\"sensorId\":593,\"mesureTime\":\"2022-09-11 00:00:00\",\"maxValue\":140,\"avgValue\":140,\"alarmLevel\":0,\"lightningCount\":0,\"onLine\":true},{\"sensorId\":594,\"mesureTime\":\"2022-09-11 00:00:00\",\"maxValue\":140,\"avgValue\":140,\"alarmLevel\":0,\"lightningCount\":0,\"onLine\":true},{\"sensorId\":595,\"mesureTime\":\"2022-09-11 00:00:00\",\"maxValue\":140,\"avgValue\":140,\"alarmLevel\":0,\"lightningCount\":0,\"onLine\":true}]";
    com.alibaba.fastjson.JSONArray jsonArray = JSONArray.parseArray((airDevise), Feature.OrderedField);
    return jsonArray;
  }

  @RequestMapping(value = "/user/getAirDeviseDataByPost", method = RequestMethod.POST)
  @ResponseBody
  public JSONArray getAirDeviseDataByPost() throws IOException {
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

    return  jsonObjectData;
  }

}
