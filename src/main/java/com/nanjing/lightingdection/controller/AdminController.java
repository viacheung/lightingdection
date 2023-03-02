package com.nanjing.lightingdection.controller;

import com.nanjing.lightingdection.dto.InputDisplay;
import com.nanjing.lightingdection.entity.*;
import com.nanjing.lightingdection.service.*;
import com.nanjing.lightingdection.utils.*;
import gnu.io.PortInUseException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {

  @Autowired
  private ChanpinPhotoService chanpinPhotoService;
  @Autowired
  private BaojingPanduanService baojingPanduanService;
  @Autowired
  private DatameaningService datameaningService;

  @Autowired
  private UserService userservice;

  @Autowired
  private ChanpinService chanpinservice;
  @Autowired
  private PanDuanService panDuanService;

  @Autowired
  private ImageService imageservice;
  @Autowired
  private ErrorSetService errorSetService;
  @Autowired
  private ReadDataService readDataService;
  @Autowired
  private HoldingRejisterService holdingRejisterService;
  @Autowired
  private CoilService coilService;
  @Autowired
  private LoginService loginService;

  @RequestMapping(value = "/admin/adminMenu", method = RequestMethod.GET)
  public String adminMenu() {

    return "admin/adminMenu";

  }


  @RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
  public String loginOut(HttpServletRequest request) {
    request.getSession().setAttribute("user", null);
    return "redirect:/system/login";
  }

  @RequestMapping(value = "/admin/appManage", method = RequestMethod.GET)
  public ModelAndView appManage(ModelAndView model, Model model1) {

    model.setViewName("admin/appManage");
    return model;
  }

  @RequestMapping(value = "/admin/baojing", method = RequestMethod.GET)
  public ModelAndView baojing(ModelAndView model, Model model1) {

    model.setViewName("admin/baojing");
    return model;
  }

  @RequestMapping(value = "/admin/baojingPanduan", method = RequestMethod.GET)
  public ModelAndView baojingPanduan(ModelAndView model, Model model1) {

    model.setViewName("admin/baojingPanduan");
    return model;
  }

  @RequestMapping(value = "/admin/chanpin", method = RequestMethod.GET)
  public ModelAndView chanpin(ModelAndView model, Model model1) {

    model.setViewName("admin/chanpin");
    return model;
  }

  @RequestMapping(value = "/admin/chanpinFile", method = RequestMethod.GET)
  public ModelAndView chanpinFile(ModelAndView model, Model model1) {

    model.setViewName("admin/chanpinFile");
    return model;
  }

  @RequestMapping(value = "/admin/ChanpinPhoto", method = RequestMethod.GET)
  public ModelAndView ChanpinPhoto(ModelAndView model, Model model1) {

    model.setViewName("admin/ChanpinPhoto");
    return model;
  }

  @RequestMapping(value = "/admin/chanpinSet", method = RequestMethod.GET)
  public ModelAndView chanpinSet(ModelAndView model, Model model1) {

    model.setViewName("admin/chanpinSet");
    return model;
  }

  @RequestMapping(value = "/admin/datameaning", method = RequestMethod.GET)
  public ModelAndView datameaning(ModelAndView model, Model model1) {

    model.setViewName("admin/datameaning");
    return model;
  }

  @RequestMapping(value = "/admin/systemImage", method = RequestMethod.GET)
  public ModelAndView systemImage(ModelAndView model, Model model1) {

    model.setViewName("admin/systemImage");
    return model;
  }

  @RequestMapping(value = "/admin/errorSet", method = RequestMethod.GET)
  public ModelAndView errorSet(ModelAndView model, Model model1) {

    model.setViewName("admin/errorSet");
    return model;
  }

  @RequestMapping(value = "/admin/loginPage", method = RequestMethod.GET)
  public ModelAndView loginPage(ModelAndView model, Model model1) {

    model.setViewName("admin/loginPage");
    return model;
  }

  @RequestMapping(value = "/admin/PanDuan", method = RequestMethod.GET)
  public ModelAndView PanDuan(ModelAndView model, Model model1) {

    model.setViewName("admin/PanDuan");
    return model;
  }

  @RequestMapping(value = "/admin/readData", method = RequestMethod.GET)
  public ModelAndView readData(ModelAndView model, Model model1) {

    model.setViewName("admin/readData");
    return model;
  }

  @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
  public ModelAndView user(ModelAndView model, Model model1) {

    model.setViewName("admin/user");
    return model;
  }

  @RequestMapping(value = "/admin/userImage", method = RequestMethod.GET)
  public ModelAndView userImage(ModelAndView model, Model model1) {

    model.setViewName("admin/userImage");
    return model;
  }

  @RequestMapping(value = "/admin/Import", produces = "application/json;charset=utf-8")
  @ResponseBody
  public GlobalResult Import(@RequestParam("import_file") MultipartFile import_file, String dots, String size) {
    try {
      System.out.println("dotsize:" + size);
      Double Size = Double.parseDouble(size);
      imageservice.doImport(import_file, dots, Size);
      return new GlobalResult(200, "添加成功", null);
    } catch (IOException e) {
      e.printStackTrace();
      return new GlobalResult(400, "添加失败", null);
    }

  }

  @RequestMapping(value = "/admin/getAllLocations", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Chanpin> getAllLocations() {    //获得所有位置区域的下拉列表框
    return chanpinservice.getAllSons(0);
  }

  @RequestMapping(value = "/admin/getAllLeibies", produces = "application/json;charset=utf-8")
  @ResponseBody
  public List<Chanpin> getAllLeibies(String locationId) {//获得所有的类别信息
    if (locationId == "" || locationId == null) {
      return null;
    } else {
      List<Chanpin> a = chanpinservice.getAllSons(Integer.parseInt(locationId));
      //System.out.println("类别："+a);
      return a;
    }
  }


  @RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult addUser(User user) {
    return userservice.insertUser(user);
  }

  @RequestMapping(value = "/admin/updateUser", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult updateUser(User user) {
    return userservice.updateUser(user);
  }

  @RequestMapping(value = "/admin/UserListByPage", method = RequestMethod.POST)
  @ResponseBody
  public DataGridResult UserListByPage(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                       @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
    return userservice.queryAll(page, rows);
  }

  @RequestMapping(value = "/admin/deleteUser", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult deleteUser(User user) {
    System.out.println(user);
    return userservice.deleteUserById(user.getUser_id());
  }

  @RequestMapping(value = "/admin/ChanpinImageListByPage", method = RequestMethod.POST)
  @ResponseBody
  public DataGridResult ChanpinImageListByPage(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                               @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
    return chanpinPhotoService.findAll(page, rows);
  }

  @RequestMapping(value = "/admin/SystemImageListByPage", method = RequestMethod.POST)
  @ResponseBody
  public DataGridResult SystemImageListByPage(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
    return loginService.queryAll(page, rows);
  }

  @RequestMapping(value = "/admin/allSystemImageList", method = RequestMethod.POST)
  @ResponseBody
  public Login allSystemImageList() {
    System.out.println("login:" + loginService.getAll());
    return loginService.getAll();
  }

  @RequestMapping(value = "/admin/SystemImageEdit", produces = "application/json;charset=utf-8")
  @ResponseBody
  public GlobalResult SystemImageEdit(@RequestParam("import_file") MultipartFile import_file, String update_companyname, String update_jiekou, String update_xieyi, String update_name, String update_address, String update_internet, String update_phone, String update_url) {
    try {
      System.out.println("import_file:" + import_file);
      Login login = loginService.getAll();
      login.setCompanyname(update_companyname);
      login.setAddress(update_address);
      login.setInternet(update_internet);
      login.setJiekou(update_jiekou);
      login.setName(update_name);
      login.setPhone(update_phone);
      login.setXieyi(update_xieyi);
      int a = loginService.updateLogin(import_file, login);
      if (a > 0)
        return new GlobalResult(200, "修改成功", null);
      else
        return new GlobalResult(400, "修改失败", null);
    } catch (Exception e) {
      e.printStackTrace();
      return new GlobalResult(400, "修改失败", null);
    }

  }

  @RequestMapping(value = "/admin/SystemImageImport", produces = "application/json;charset=utf-8")
  @ResponseBody
  public GlobalResult SystemImageImport(@RequestParam("import_file") MultipartFile import_file, String import_companyname, String import_jiekou, String import_xieyi, String import_name, String import_address, String import_internet, String import_phone) {
    try {
      System.out.println("import_file:" + import_file);
      Login login = new Login(null, null, null, null, null, null, null, null, null);
      login.setCompanyname(import_companyname);
      login.setAddress(import_address);
      login.setInternet(import_internet);
      login.setJiekou(import_jiekou);
      login.setName(import_name);
      login.setPhone(import_phone);
      login.setXieyi(import_xieyi);
      int a = loginService.InsertLogin(import_file, login);
      if (a > 0)
        return new GlobalResult(200, "增加成功", null);
      else
        return new GlobalResult(400, "增加失败", null);
    } catch (Exception e) {
      e.printStackTrace();
      return new GlobalResult(400, "增加失败", null);
    }

  }


  @RequestMapping(value = "/admin/deleteSystemImage", produces = "application/json;charset=utf-8")
  @ResponseBody
  public GlobalResult deleteSystemImage(String id) {
    try {

      int a = loginService.deleteLoginById(Integer.parseInt(id));
      if (a > 0)
        return new GlobalResult(200, "删除成功", null);
      else
        return new GlobalResult(400, "删除失败", null);
    } catch (Exception e) {
      e.printStackTrace();
      return new GlobalResult(400, "删除失败", null);
    }

  }

  @RequestMapping(value = "/admin/addBaojingPanduan", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult addBaojingPanduan(String chanpinId, String datameaningId, String normalData, String yujingData, String baojingData) {
    Integer DatameaningId = null;
    Integer ChanpinId = null;
    Double NormalData = null, YujingData = null, BaojingData = null;
    if (datameaningId != null && !datameaningId.equals(""))
      DatameaningId = Integer.parseInt(datameaningId);
    if (chanpinId != null && !chanpinId.equals(""))
      ChanpinId = Integer.parseInt(chanpinId);
    if (normalData != null && !normalData.equals(""))
      NormalData = Double.parseDouble(normalData);
    if (yujingData != null && !yujingData.equals(""))
      YujingData = Double.parseDouble(yujingData);
    if (baojingData != null && !baojingData.equals(""))
      BaojingData = Double.parseDouble(baojingData);
    if (datameaningService.findDatameaningById(DatameaningId) == null)
      return new GlobalResult(400, "寄存器编号不存在", null);

    BaojingPanduan temp = new BaojingPanduan(null, ChanpinId, DatameaningId, NormalData, YujingData, BaojingData);
    if (baojingPanduanService.insertBaojingPanduan(temp) > 0)
      return new GlobalResult(200, "添加成功", null);
    else
      return new GlobalResult(400, "添加失败", null);
  }

  @RequestMapping(value = "/admin/updateBaojingPanduan", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult updateBaojingPanduan(String id, String chanpinId, String datameaningId, String normalData, String yujingData,
                                           String baojingData) {
    if (id == null)
      return new GlobalResult(400, "修改失败", null);
    Integer ChanpinId = null, DatameaningId = null;
    Double NormalData = null, YujingData = null, BaojingData = null;
    if (datameaningId != null && !datameaningId.equals(""))
      DatameaningId = Integer.parseInt(datameaningId);
    if (chanpinId != null && !chanpinId.equals(""))
      ChanpinId = Integer.parseInt(chanpinId);
    if (normalData != null && !normalData.equals(""))
      NormalData = Double.parseDouble(normalData);
    if (yujingData != null && !yujingData.equals(""))
      YujingData = Double.parseDouble(yujingData);
    if (baojingData != null && !baojingData.equals(""))
      BaojingData = Double.parseDouble(baojingData);
    if (datameaningService.findDatameaningById(DatameaningId) == null)
      return new GlobalResult(400, "寄存器编号不存在", null);
    BaojingPanduan temp = new BaojingPanduan(Integer.parseInt(id), ChanpinId, DatameaningId, NormalData, YujingData,
            BaojingData);
    if (baojingPanduanService.updateBaojingPanduan(temp) > 0)
      return new GlobalResult(200, "修改成功", null);
    else
      return new GlobalResult(400, "修改失败", null);

  }

  @RequestMapping(value = "/admin/BaojingPanduanListByPage", method = RequestMethod.POST)
  @ResponseBody
  public DataGridResult BaojingPanduanListByPage(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
    return baojingPanduanService.queryAll(page, rows);
  }


  @RequestMapping(value = "/admin/deleteBaojingPanduan", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult deleteBaojingPanduan(String id) {
    if (id == null)
      return new GlobalResult(400, "删除失败", null);
    if (baojingPanduanService.deleteBaojingPanduanById(Integer.parseInt(id)) > 0)
      return new GlobalResult(200, "删除成功", null);
    else
      return new GlobalResult(400, "删除失败", null);
  }

  @RequestMapping(value = "/admin/getAllSons", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> getAllSons(String pid) {
    List<Chanpin> list = chanpinservice.getAllSons(Integer.parseInt(pid));
    System.out.println("pid:" + pid);
    System.out.println("list:" + list);
    return list;
  }

  @RequestMapping(value = "/admin/findChanpinById", method = RequestMethod.POST)
  @ResponseBody
  public Chanpin findChanpinById(String id) {
    // List<ChanpinTree> list=chanpinservice.findChanpinList();
    // System.out.println(list);
    Chanpin chanpin = chanpinservice.findChanpinById(Integer.parseInt(id));
    return chanpin;
  }

  @RequestMapping(value = "/admin/findChanpinListById", method = RequestMethod.POST)
  @ResponseBody
  public List<Chanpin> findChanpinListById(String id) {
    // System.out.println(id);
    // List<Chanpin> all=chanpinservice.queryAll();
    List<Chanpin> lists = new ArrayList<>();
    List<Chanpin> list = chanpinservice.findChanpinListById(Integer.parseInt(id));
    // List<Chanpin> sons=chanpinservice.getAllSons(Integer.parseInt(id));
    for (Chanpin chanpin : list) {
      if (chanpin.getId() != 0) {
        lists.add(chanpin);
      }
    }
    /*
     * for(Chanpin chanpin:sons) { if(chanpin.getId()!=0) { lists.add(chanpin); } }
     */
    System.out.println(lists);
    return lists;
  }

  @RequestMapping(value = "/admin/addChanpin", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult addChanpin(String name, String location, String zhuangtai, String model, String installation,
                                 String way, String address, String port, String portName, String boto, String boxId, String comment,
                                 String slaveId, String datameaningId, String connectTime, String readTime, String pid, String isparent) {
    //System.out.println("已调用产品增加！");
    Integer Boto = null, Way = null, Port = null, Zhuangtai = null, SlaveId = null, Isparent = null, Pid = 0,
            DatameaningId = null, ConnectTime = null, ReadTime = null;
    if (zhuangtai != null && !zhuangtai.equals("") && !zhuangtai.equals("null"))
      Zhuangtai = Integer.parseInt(zhuangtai);
    System.out.println("zhuangtai:" + Zhuangtai);
    if (isparent != null && !isparent.equals("") && !isparent.equals("null"))
      Isparent = Integer.parseInt(isparent);
    System.out.println("isparent:" + Isparent);
    if (pid != null && !pid.equals("") && !pid.equals("null"))
      Pid = Integer.parseInt(pid);
    System.out.println("pid:" + Pid);
    System.out.println("way:" + way);
    if (way != null && !way.equals("") && !way.equals("null") && !way.equals("undefined"))
      Way = Integer.parseInt(way);
    System.out.println("way:" + Way);
    if (port != null && !port.equals("") && !port.equals("null"))
      Port = Integer.parseInt(port);
    System.out.println("port:" + Port);
    if (connectTime != null && !connectTime.equals("") && !connectTime.equals("null"))
      ConnectTime = Integer.parseInt(connectTime);
    if (readTime != null && !readTime.equals("") && !readTime.equals("null"))
      ReadTime = Integer.parseInt(readTime);
    if (slaveId != null && !slaveId.equals("") && !slaveId.equals("null"))
      SlaveId = Integer.parseInt(slaveId);
    if (datameaningId != null && !datameaningId.equals("") && !datameaningId.equals("null"))
      DatameaningId = Integer.parseInt(datameaningId);
    System.out.println("datameaningId:" + datameaningId);
    if (boto != null && !boto.equals("") && !boto.equals("null"))
      Boto = Integer.parseInt(boto);
    System.out.println("boto:" + Boto);
    Chanpin tempchanpin = new Chanpin(null, name, location, Zhuangtai, model, installation, Way, address, Port,
            portName, Boto, boxId, comment, SlaveId, DatameaningId, ConnectTime, ReadTime, Pid, Isparent);
    //System.out.println("tempchanpin:" + tempchanpin);
    Integer a = chanpinservice.insertChanpin(tempchanpin);
    //System.out.println("a:" + a);

    //System.out.println("a:" + a);

    if (a > 0)
      return new GlobalResult(200, "产品添加成功", null);
    else
      return new GlobalResult(400, "产品添加失败", null);

  }

  @RequestMapping(value = "/admin/deleteChanpin", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult deleteChanpin(String id) {
    System.out.println("id:" + id);
    if (id == null || id.equals(""))
      return new GlobalResult(400, "删除失败", null);
    Chanpin chanpin = chanpinservice.findChanpinById(Integer.parseInt(id));
    List<Chanpin> all = chanpinservice.queryAll();
    for (int i = 0; i < all.size(); i++) {
      System.out.println(all.get(i));
    }
    System.out.println("  ");
    List<Chanpin> list = chanpinservice.getAllSonPoint(all, chanpin.getId());
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
    list.add(chanpin);
    int num = 0;
    for (int i = 0; i < list.size(); i++) {
      int b = chanpinservice.deleteChanpinById(list.get(i).getId());
      if (b == 1)
        num++;
    }
    //System.out.println("list.size(): " + list.size());
    //System.out.println("num: " + num);

    if (num == list.size() || num > 0) {
      return new GlobalResult(200, "删除成功", null);
    } else {
      return new GlobalResult(400, "删除失败", null);
    }

  }

  @RequestMapping(value = "/admin/batchAddChanpin", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult batchAddChanpin(String num, String location, String datameaningId, String pid, String isparent) {

    //System.out.println("执行一次产品批量添加！");
    Integer Num = null, Pid = null, Isparent = null, DatameaningId = null;
    if (num != null && !num.equals("") && !num.equals("null"))
      Num = Integer.parseInt(num);
    if (pid != null && !pid.equals("") && !pid.equals("null"))
      Pid = Integer.parseInt(pid);
    if (isparent != null && !isparent.equals("") && !isparent.equals("null"))
      Isparent = Integer.parseInt(isparent);
    if (datameaningId != null && !datameaningId.equals("") && !datameaningId.equals("null")
            && !datameaningId.equals("-1"))
      DatameaningId = Integer.parseInt(datameaningId);

    int j = 0;
    for (int i = 0; i < Num; i++) {
      Chanpin chanpin = new Chanpin(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
      chanpin.setName("测试");
      chanpin.setPid(Pid);
      chanpin.setDatameaningId(DatameaningId);
      chanpin.setIsparent(Isparent);
      chanpin.setLocation(location);
      int a = chanpinservice.insertChanpin(chanpin);
      j += a;
    }
    if (j > 0 || j == Num) {
      return new GlobalResult(200, "批量添加成功", null);
    } else {
      return new GlobalResult(400, "批量添加失败", null);
    }
  }

  @RequestMapping(value = "/admin/updateChanpin", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult updateChanpin(String id, String name, String location, String zhuangtai, String model,
                                    String installation, String way, String address, String port, String portName, String boto, String boxId,
                                    String comment, String slaveId, String datameaningId, String connectTime, String readTime, String pid,
                                    String isparent) {
    //System.out.println("调用一次产品修改！");
    if (id == null || id.equals(""))
      return new GlobalResult(400, "产品修改失败", null);
    Integer Boto = null, Id = null, Way = null, Port = null, Zhuangtai = null, SlaveId = null,
            Isparent = null, Pid = null, DatameaningId = null, ConnectTime = null, ReadTime = null;
    if (id != null && !id.equals(""))
      Id = Integer.parseInt(id);
    if (zhuangtai != null && !zhuangtai.equals(""))
      Zhuangtai = Integer.parseInt(zhuangtai);
    if (isparent != null && !isparent.equals(""))
      Isparent = Integer.parseInt(isparent);
    if (pid != null && !pid.equals(""))
      Pid = Integer.parseInt(pid);
    if (way != null && !way.equals(""))
      Way = Integer.parseInt(way);
    if (port != null && !port.equals(""))
      Port = Integer.parseInt(port);
    if (connectTime != null && !connectTime.equals("") && !connectTime.equals("null"))
      ConnectTime = Integer.parseInt(connectTime);
    if (readTime != null && !readTime.equals("") && !readTime.equals("null"))
      ReadTime = Integer.parseInt(readTime);
    if (slaveId != null && !slaveId.equals(""))
      SlaveId = Integer.parseInt(slaveId);
    if (datameaningId != null && !datameaningId.equals("") && !datameaningId.equals("null"))
      DatameaningId = Integer.parseInt(datameaningId);
    if (boto != null && !boto.equals(""))
      Boto = Integer.parseInt(boto);
    // if(functionId!=null&&!functionId.equals(""))FunctionId=Integer.parseInt(pid);
    System.out.println("datameaningId:  " + datameaningId);
    Chanpin tempchanpin = new Chanpin(Id, name, location, Zhuangtai, model, installation, Way, address, Port,
            portName, Boto, boxId, comment, SlaveId, DatameaningId, ConnectTime, ReadTime, Pid, Isparent);
    int a = chanpinservice.updateChanpinById(tempchanpin);

    if (a > 0)
      return new GlobalResult(200, "产品修改成功", null);
    else
      return new GlobalResult(400, "产品修改失败", null);
  }

  @RequestMapping(value = "/admin/addDatameaning", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult addDatameaning(String name, String type, String functionId, String byteAddress, String byteNum,
                                     String bitAddress, String isInput, String functionCode, String isHex, String scale, String unit,
                                     String isPositive, String isJiexi, String gongshi, String isPanduan, String isBaojing,
                                     String isBaojingPanduan, String isShown, String isRecord, String pattern, String zero, String first, String isPaint,
                                     String pid, String isparent, String bit) {
    System.out.println("已调用一次解析增加！");
    Integer BitAddress = null, Pattern = null, Pid = null, IsInput = null, LocationId = null, ChanpinId = null,
            Type = null, FunctionId = null, ByteNum = null, ByteAddress = null, IsHex = null, IsPositive = null,
            IsJiexi = null, IsPanduan = null, IsBaojing = null, IsBaojingPanduan = null, IsShown = null, Bit = null,
            Isparent = null, IsRecord = null, IsPaint = null;
    Double Scale = null;
    if (bit != null && !bit.equals("") && !bit.equals("null"))
      Bit = Integer.parseInt(bit);
    //System.out.println("Bit:" + Bit);
    if (isInput != null && !isInput.equals(""))
      IsInput = Integer.parseInt(isInput);
    //System.out.println("isInput:" + isInput);
    if (pid != null && !pid.equals(""))
      Pid = Integer.parseInt(pid);
    //System.out.println("Pid:" + Pid);
    // System.out.println("locationId:"+locationId);
    // if(locationId!=null&&!locationId.equals("")&&!locationId.equals("null"))LocationId=Integer.parseInt(locationId);
    // System.out.println("LocationId:"+LocationId);
    // if(chanpinId!=null&&!chanpinId.equals("")&&!chanpinId.equals("null"))ChanpinId=Integer.parseInt(chanpinId);
    // System.out.println("ChanpinId:"+ChanpinId);
    if (type != null && !type.equals("") && !type.equals("null"))
      Type = Integer.parseInt(type);
    //System.out.println("Type:" + Type);
    if (functionId != null && !functionId.equals("") && !functionId.equals("null"))
      FunctionId = Integer.parseInt(functionId);
    //System.out.println("FunctionId:" + FunctionId);
    if (byteNum != null && !byteNum.equals("") && !byteNum.equals("null"))
      ByteNum = Integer.parseInt(byteNum);
    //System.out.println("ByteNum:" + ByteNum);
    if (byteAddress != null && !byteAddress.equals(""))
      ByteAddress = Integer.parseInt(byteAddress);
    //System.out.println("ByteAddress:" + ByteAddress);
    if (isHex != null && !isHex.equals("") && !isHex.equals("undefined"))
      IsHex = Integer.parseInt(isHex);
    //System.out.println("IsHex:" + IsHex);
    if (isPositive != null && !isPositive.equals("") && !isPositive.equals("undefined"))
      IsPositive = Integer.parseInt(isPositive);
    //System.out.println("IsPositive:" + IsPositive);
    if (isJiexi != null && !isJiexi.equals("") && !isJiexi.equals("undefined"))
      IsJiexi = Integer.parseInt(isJiexi);
    //System.out.println("IsJiexi:" + IsJiexi);
    if (isPanduan != null && !isPanduan.equals("") && !isPanduan.equals("undefined"))
      IsPanduan = Integer.parseInt(isPanduan);
    //System.out.println("IsPanduan:" + IsPanduan);
    if (isBaojing != null && !isBaojing.equals("") && !isBaojing.equals("undefined"))
      IsBaojing = Integer.parseInt(isBaojing);
    //System.out.println("IsBaojing:" + IsBaojing);
    // if(isRecord!=null&&!isRecord.equals(""))IsRecord=Integer.parseInt(isRecord);
    // System.out.println("IsRecord:"+IsRecord);
    if (isparent != null && !isparent.equals(""))
      Isparent = Integer.parseInt(isparent);
    //System.out.println("Isparent:" + Isparent);
    if (pattern != null && !pattern.equals("") && !pattern.equals("null") && !pattern.equals("undefined"))
      Pattern = Integer.parseInt(pattern);
    //System.out.println("Pattern:" + Pattern);
    if (bitAddress != null && !bitAddress.equals("") && !bitAddress.equals("null"))
      BitAddress = Integer.parseInt(bitAddress);
    if (scale != null && !scale.equals(""))
      Scale = Double.parseDouble(scale);
    //System.out.println("Scale:" + Scale);
    if (isBaojingPanduan != null && !isBaojingPanduan.equals("") && !isBaojingPanduan.equals("null")
            && !isBaojingPanduan.equals("undefined"))
      IsBaojingPanduan = Integer.parseInt(isBaojingPanduan);
    // System.out.println("Pattern:"+Pattern);
    if (isShown != null && !isShown.equals("") && !isShown.equals("null") && !isShown.equals("undefined"))
      IsShown = Integer.parseInt(isShown);
    if (isRecord != null && !isRecord.equals("") && !isRecord.equals("null") && !isRecord.equals("undefined"))
      IsRecord = Integer.parseInt(isRecord);
    if (isPaint != null && !isPaint.equals("") && !isPaint.equals("null") && !isPaint.equals("undefined"))
      IsPaint = Integer.parseInt(isPaint);
    // Datameaning datameaning= new
    // Datameaning(null,name,ChanpinId,Type,FunctionId,StartRejister,RejisterNum,ByteNum,ByteAddress,IsInput,FunctionCode,IsHex,Scale,unit,IsPositive,IsJiexi,gongshi,IsPanduan,IsBaojing,IsRecord,icon,Pid,Isparent);
    Datameaning datameaning = new Datameaning(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    datameaning.setName(name);
    datameaning.setBit(Bit);
    datameaning.setIsRecord(IsRecord);
    datameaning.setIsPaint(IsPaint);
    datameaning.setType(Type);
    datameaning.setFunctionId(FunctionId);
    datameaning.setIsShown(IsShown);
    datameaning.setIsBaojingPanduan(IsBaojingPanduan);
    datameaning.setByteNum(ByteNum);
    datameaning.setByteAddress(ByteAddress);
    datameaning.setIsHex(IsHex);
    datameaning.setScale(Scale);
    datameaning.setUnit(unit);
    datameaning.setIsPositive(IsPositive);
    datameaning.setIsJiexi(IsJiexi);
    datameaning.setGongshi(gongshi);
    datameaning.setIsPanduan(IsPanduan);
    datameaning.setIsSlove(IsBaojing);
    datameaning.setIsInput(IsInput);
    datameaning.setFunctionCode(functionCode);
    datameaning.setPid(Pid);
    datameaning.setPattern(Pattern);
    datameaning.setIsparent(Isparent);
    datameaning.setZero(zero);
    datameaning.setBitAddress(BitAddress);
    int a = datameaningService.insertDatameaning(datameaning);
    if (a > 0)
      return new GlobalResult(200, "添加成功", null);
    else
      return new GlobalResult(400, "添加失败", null);
  }

  @RequestMapping(value = "/admin/findDatameaningListById", method = RequestMethod.POST)
  @ResponseBody
  public List<Datameaning> findDatameaningListById(String id) {
    // System.out.println(id);
    List<Datameaning> lists = new ArrayList<>();
    List<Datameaning> a = datameaningService.findDatameaningListById(Integer.parseInt(id));
    // List<Datameaning> list=datameaningService.getDMAllSons(Integer.parseInt(id));
    // if(datameaning.getId()!=0) lists.add(datameaning);
    /*
     * List<Datameaning>
     * a=datameaningService.findDatameaningListById(Integer.parseInt(id));
     *
     * for(int i=0;i<a.size();i++) { lists.add(a.get(i)); }
     */
    for (int i = 0; i < a.size(); i++) {
      if (a.get(i).getId() != 0)
        lists.add(a.get(i));
    }
    /*
     * for(int i=0;i<list.size();i++) { if(list.get(i).getId()!=0)
     * lists.add(list.get(i)); }
     */
    // System.out.println(list);
    return lists;

    /*
     * System.out.println(id); List<Datameaning>
     * list=datameaningService.findDatameaningListById(Integer.parseInt(id));
     * System.out.println(list); return list;
     */
  }

  @RequestMapping(value = "/admin/findDatameaningById", method = RequestMethod.POST)
  @ResponseBody
  public Datameaning findDatameaningById(String id) {
    Datameaning datameaning = datameaningService.findDatameaningById(Integer.parseInt(id.trim()));

    return datameaning;
  }

  @RequestMapping(value = "/admin/getAllSons2", method = RequestMethod.POST)
  @ResponseBody
  public List<Datameaning> getAllSons2(String pid) {
    List<Datameaning> list = datameaningService.getDMAllSons(Integer.parseInt(pid));
    System.out.println("pid:" + pid);
    System.out.println("list:" + list);
    return list;
  }

  @RequestMapping(value = "/admin/deleteDatamenaing", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult deleteDatamenaing(String id) {
    if (id == null)
      return new GlobalResult(400, "删除失败", null);
    List<Datameaning> all = datameaningService.queryAll();
    Datameaning datameaning = datameaningService.findDatameaningById(Integer.parseInt(id));
    List<Datameaning> list = datameaningService.getAllSonPoint(all, datameaning.getId());
    int a = datameaningService.deleteDatameaningById(Integer.parseInt(id));
    int b = 1;
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        b = datameaningService.deleteDatameaningById(list.get(i).getId());
      }
    }
    if (a > 0 && b > 0)
      return new GlobalResult(200, "删除成功", null);
    else
      return new GlobalResult(400, "删除失败", null);

  }

  @RequestMapping(value = "/admin/batchAddDatameaning", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult batchAddDatameaning(String num, String type, String functionId, String pid, String isparent) {
    System.out.println("调用一次批量增加！");
    Integer Num = null, Pid = null, Isparent = null, ChanpinId = null, LocationId = null, Type = null,
            FunctionId = null;
    if (num != null && !num.equals("") && !num.equals("null"))
      Num = Integer.parseInt(num);
    if (pid != null && !pid.equals("") && !pid.equals("null"))
      Pid = Integer.parseInt(pid);
    if (isparent != null && !isparent.equals("") && !isparent.equals("null"))
      Isparent = Integer.parseInt(isparent);
    // if(chanpinId!=null&&!chanpinId.equals("")&&!chanpinId.equals("null"))ChanpinId=Integer.parseInt(chanpinId);
    // if(locationId!=null&&!locationId.equals("")&&!locationId.equals("null"))LocationId=Integer.parseInt(locationId);
    if (type != null && !type.equals("") && !type.equals("null"))
      Type = Integer.parseInt(type);
    if (functionId != null && !functionId.equals("") && !functionId.equals("null"))
      FunctionId = Integer.parseInt(functionId);
    System.out.println("num:" + num);
    int j = 0;

    for (int i = 0; i < Num; i++) {
      Datameaning datameaning = new Datameaning(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
      datameaning.setPid(Pid);
      datameaning.setName("测试");
      // datameaning.setChanpinId(ChanpinId);
      // datameaning.setLocationId(LocationId);
      datameaning.setType(Type);
      datameaning.setFunctionId(FunctionId);
      datameaning.setIsparent(Isparent);
      int b = datameaningService.insertDatameaning(datameaning);
      j += b;
    }
    if (j == Num) {
      return new GlobalResult(200, "批量添加成功", null);
    } else {
      return new GlobalResult(400, "批量添加失败", null);
    }
  }

  @RequestMapping(value = "/admin/updateDatameaning", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult updateDatameaning(String id, String name, String type, String functionId, String byteAddress,
                                        String byteNum, String bitAddress, String isInput, String functionCode, String isHex, String scale,
                                        String unit, String isPositive, String isJiexi, String gongshi, String isPanduan, String isBaojing,
                                        String isBaojingPanduan, String isShown, String isRecord, String pattern, String zero, String first, String isPaint,
                                        String pid, String isparent, String bit) {
    if (id == null)
      return new GlobalResult(400, "修改失败", null);
    Integer Bit = null, BitAddress = null, Id = null, Pattern = null, Pid = null, IsInput = null, IsPaint = null,
            ChanpinId = null, Type = null, FunctionId = null, IsBaojingPanduan = null, IsShown = null,
            ByteNum = null, ByteAddress = null, IsHex = null, IsPositive = null, IsJiexi = null, IsPanduan = null,
            IsBaojing = null, IsRecord = null, Isparent = null;
    Double Scale = null;
    if (bit != null && !bit.equals("") && !bit.equals("null"))
      Bit = Integer.parseInt(bit);
    //System.out.println("bit:" + Bit);
    if (id != null && !id.equals(""))
      Id = Integer.parseInt(id);
    //System.out.println("id:" + Id);
    if (isInput != null && !isInput.equals(""))
      IsInput = Integer.parseInt(isInput);
    //System.out.println("isInput:" + isInput);
    if (pid != null && !pid.equals(""))
      Pid = Integer.parseInt(pid);
    //System.out.println("Pid:" + Pid);

    if (type != null && !type.equals("") && !type.equals("null"))
      Type = Integer.parseInt(type);
    //System.out.println("Type:" + Type);
    if (functionId != null && !functionId.equals("") && !functionId.equals("null"))
      FunctionId = Integer.parseInt(functionId);
    //System.out.println("FunctionId:" + FunctionId);

    if (byteNum != null && !byteNum.equals("") && !byteNum.equals("null"))
      ByteNum = Integer.parseInt(byteNum);
    //System.out.println("ByteNum:" + ByteNum);
    if (byteAddress != null && !byteAddress.equals(""))
      ByteAddress = Integer.parseInt(byteAddress);
    //System.out.println("ByteAddress:" + ByteAddress);
    if (isHex != null && !isHex.equals(""))
      IsHex = Integer.parseInt(isHex);
    //System.out.println("IsHex:" + IsHex);
    if (isPositive != null && !isPositive.equals(""))
      IsPositive = Integer.parseInt(isPositive);
    //System.out.println("IsPositive:" + IsPositive);
    if (isJiexi != null && !isJiexi.equals(""))
      IsJiexi = Integer.parseInt(isJiexi);
    //System.out.println("IsJiexi:" + IsJiexi);
    if (isPanduan != null && !isPanduan.equals(""))
      IsPanduan = Integer.parseInt(isPanduan);
    //System.out.println("IsPanduan:" + IsPanduan);
    if (isBaojing != null && !isBaojing.equals(""))
      IsBaojing = Integer.parseInt(isBaojing);
    //System.out.println("IsBaojing:" + IsBaojing);
    // if(isRecord!=null&&!isRecord.equals(""))IsRecord=Integer.parseInt(isRecord);
    // System.out.println("IsRecord:"+IsRecord);
    //System.out.println("isparent:" + isparent);
    if (isparent != null && !isparent.equals(""))
      Isparent = Integer.parseInt(isparent);

    //System.out.println("Isparent:" + Isparent);
    if (bitAddress != null && !bitAddress.equals("") && !bitAddress.equals("null"))
      BitAddress = Integer.parseInt(bitAddress);

    if (pattern != null && !pattern.equals("") && !pattern.equals("null") && !pattern.equals("undefined"))
      Pattern = Integer.parseInt(pattern);
    //System.out.println("Pattern:" + Pattern);
    if (scale != null && !scale.equals(""))
      Scale = Double.parseDouble(scale);
    //System.out.println("Scale:" + Scale);
    if (isBaojingPanduan != null && !isBaojingPanduan.equals("") && !isBaojingPanduan.equals("null")
            && !isBaojingPanduan.equals("undefined"))
      IsBaojingPanduan = Integer.parseInt(isBaojingPanduan);
    if (isShown != null && !isShown.equals("") && !isShown.equals("null") && !isShown.equals("undefined"))
      IsShown = Integer.parseInt(isShown);
    if (isRecord != null && !isRecord.equals("") && !isRecord.equals("null") && !isRecord.equals("undefined"))
      IsRecord = Integer.parseInt(isRecord);
    if (isPaint != null && !isPaint.equals("") && !isPaint.equals("null") && !isPaint.equals("undefined"))
      IsPaint = Integer.parseInt(isPaint);
    Datameaning datameaning = datameaningService.findDatameaningById(Id);
    //System.out.println("Id:" + datameaning.getId());
    datameaning.setId(Id);
    datameaning.setIsRecord(IsRecord);
    datameaning.setIsPaint(IsPaint);
    datameaning.setName(name);
    datameaning.setIsBaojingPanduan(IsBaojingPanduan);
    datameaning.setIsShown(IsShown);
    // datameaning.setChanpinId(ChanpinId);
    datameaning.setType(Type);
    datameaning.setFunctionId(FunctionId);
    // datameaning.setStartRejister(StartRejister);
    // datameaning.setRejisterNum(RejisterNum);
    datameaning.setByteNum(ByteNum);
    datameaning.setByteAddress(ByteAddress);
    datameaning.setIsHex(IsHex);
    datameaning.setScale(Scale);
    datameaning.setUnit(unit);
    datameaning.setIsPositive(IsPositive);
    datameaning.setIsJiexi(IsJiexi);
    datameaning.setGongshi(gongshi);
    datameaning.setIsPanduan(IsPanduan);
    datameaning.setIsSlove(IsBaojing);
    // datameaning.setModel(model);
    datameaning.setIsInput(IsInput);
    datameaning.setFunctionCode(functionCode);
    datameaning.setPid(Pid);
    datameaning.setPattern(Pattern);
    datameaning.setZero(zero);
    datameaning.setFirst(first);
    datameaning.setBitAddress(BitAddress);
    datameaning.setBit(Bit);
    // datameaning.setThird(third);
    datameaning.setIsparent(Isparent);
    System.out.println("datameaning:" + datameaning);
    // int b=1;

    int a = datameaningService.updateDatameaningById(datameaning);
    System.out.println("a:" + a);
    // System.out.println("已进入修改2！");
    if (a > 0)
      return new GlobalResult(200, "修改成功", null);
    else
      return new GlobalResult(400, "修改失败", null);
  }

  @RequestMapping(value = "/admin/addPanDuan", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult addPanDuan(int data, String name, String datameaningId, String isError) {
    PanDuan panDuan = new PanDuan(data, name, Integer.parseInt(datameaningId), Integer.parseInt(isError));
    //panDuan.setData(data);
    //panDuan.setDatameaningId(Integer.parseInt(datameaningId));
    //panDuan.setName(name);
    //panDuan.setIsError(Integer.parseInt(isError));
    if (panDuanService.insertPanDuanData(panDuan) > 0)
      return new GlobalResult(200, "添加成功", null);
    else
      return new GlobalResult(400, "添加失败", null);
  }

  @RequestMapping(value = "/admin/updatePanDuan", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult updatePanDuan(int data, String name, String datameaningId, String isError) {
    if (datameaningId == null)
      return new GlobalResult(400, "修改失败", null);
    PanDuan panDuan = new PanDuan(data, name, Integer.parseInt(datameaningId), Integer.parseInt(isError));
    //panDuan.setData(data);
    //panDuan.setDatameaningId(Integer.parseInt(datameaningId));
    //panDuan.setName(name);
    //panDuan.setIsError(Integer.parseInt(isError));
    if (panDuanService.updatePanDuandata(panDuan) > 0)
      return new GlobalResult(200, "修改成功", null);
    else
      return new GlobalResult(400, "修改失败", null);
  }

  @RequestMapping(value = "/admin/PanDuanListByPage", method = RequestMethod.POST)
  @ResponseBody
  public List<PanDuan> PanDuanListByPage(Integer page, Integer rows) {
    return panDuanService.findAll(page, rows);

  }

  @RequestMapping(value = "/admin/deletePanDuan", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult deletePanDuan(int data, String datameaningId) {
    if (datameaningId == null)
      return new GlobalResult(400, "删除失败", null);
    if (panDuanService.deletePanDuanData(data, Integer.parseInt(datameaningId)) > 0)
      return new GlobalResult(200, "删除成功", null);
    else
      return new GlobalResult(400, "删除失败", null);
  }

  @RequestMapping(value = "/admin/ImageListByPage", method = RequestMethod.POST)
  @ResponseBody
  public DataGridResult ImageListByPage(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                        @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
    return imageservice.queryAll(page, rows);

  }

  @RequestMapping(value = "/admin/allImageList", method = RequestMethod.POST)
  @ResponseBody
  public List<Image> allImageList() {
    return imageservice.getAll();
  }

  @RequestMapping(value = "/admin/deleteImage", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult deleteImage(String id) {
    if (id == null) return new GlobalResult(400, "删除失败", null);
    //Image tempImage=new Image(Integer.parseInt(id),null,null,null,null);
    if (imageservice.deleteImageById(Integer.parseInt(id)) > 0) return new GlobalResult(200, "删除成功", null);
    else return new GlobalResult(400, "删除失败", null);
  }

  @RequestMapping("/admin/showPhotoByPath")
  public void showPhotoByPath(HttpServletResponse response, HttpServletRequest request, @RequestParam("path") String path) throws IOException {

    ApplicationHome h = new ApplicationHome(getClass());
    File jarF = h.getSource();
    //System.out.println(jarF.getParentFile().toString());


    //System.out.println("path:"+path);
    String realPath = jarF.getParentFile().toString() + "/classes/static/";
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

  @RequestMapping(value = "/admin/toDouble", method = RequestMethod.POST)
  @ResponseBody
  public List<Pair<Double, Double>> toDouble(String dots) {
    System.out.println(dots);
    List<Pair<Double, Double>> result = new ArrayList<Pair<Double, Double>>();
    String[] Dot = dots.split("-");
    for (String dot : Dot) {
      String[] dotsplit = dot.split(",");
      double x = Double.parseDouble(dotsplit[0].substring(1));
      double y = Double.parseDouble(dotsplit[1].substring(0, dotsplit[1].length() - 1));
      //System.out.println(x+","+y);
      result.add(new Pair<Double, Double>(x, y));
    }
    return result;
  }

  @RequestMapping(value = "/admin/dianweiPhotoImport", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult dianweiPhotoImport(@RequestParam("ZCfile") MultipartFile ZCfile, @RequestParam("YCfile") MultipartFile YCfile, @RequestParam("YJfile") MultipartFile YJfile, HttpServletRequest request) {
    String zcfilename = ZCfile.getOriginalFilename();
    String ycfilename = YCfile.getOriginalFilename();
    String yjfilename = YJfile.getOriginalFilename();
    int a = 1;
    int b = 1;
    int c = 1;
    //System.out.println(zcfilename);
    if (zcfilename != null && !zcfilename.equals("")) {
      int start = zcfilename.lastIndexOf(".") + 1;
      try {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String path = realPath + "WEB-INF/dotImage/正常";
        File zhengchang = new File(path + ".png");
        if (!zhengchang.exists()) {
          zhengchang = new File(path + ".jpg");
        }
        File zc = new File(zhengchang.toString());
        zc.delete();
        File newzc = new File(realPath + "WEB-INF/dotImage/" + "正常." + zcfilename.substring(start));
        FileUtil.copy(ZCfile, newzc);
        //return true;
      } catch (Exception e) {
        a = 0;
        e.printStackTrace();
        //return false;
      }
    }

    if (ycfilename != null && !ycfilename.equals("")) {
      int start = ycfilename.lastIndexOf(".") + 1;
      try {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String path = realPath + "WEB-INF/dotImage/异常";
        File yichang = new File(path + ".png");
        if (!yichang.exists()) {
          yichang = new File(path + ".jpg");
        }
        File yc = new File(yichang.toString());
        yc.delete();
        File newyc = new File(realPath + "WEB-INF/dotImage/" + "异常." + ycfilename.substring(start));
        FileUtil.copy(YCfile, newyc);
        //return true;
      } catch (Exception e) {
        b = 0;
        e.printStackTrace();
        //return false;
      }
    }

    if (yjfilename != null && !yjfilename.equals("")) {
      int start = yjfilename.lastIndexOf(".") + 1;
      try {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String path = realPath + "WEB-INF/dotImage/预警";
        File yujing = new File(path + ".png");
        if (!yujing.exists()) {
          yujing = new File(path + ".jpg");
        }
        File yj = new File(yujing.toString());
        yj.delete();
        File newyj = new File(realPath + "WEB-INF/dotImage/" + "预警." + yjfilename.substring(start));
        FileUtil.copy(YJfile, newyj);

      } catch (Exception e) {
        c = 0;
        e.printStackTrace();

      }
    }

    if (a == 1 && b == 1 && c == 1) {
      return new GlobalResult(200, "修改成功", null);
    } else {
      return new GlobalResult(400, "修改失败", null);
    }

  }

  @RequestMapping(value = "/admin/uploadTempPhoto", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult uploadTempPhoto(@RequestParam("import_file") MultipartFile import_file, HttpServletRequest request) {
    try {
      String filename = import_file.getOriginalFilename();
      int start = filename.lastIndexOf(".") + 1;
      String realPath = request.getSession().getServletContext().getRealPath("/");
      String path = realPath + "WEB-INF/temp/temp";
      File temp = new File(path + ".png");
      if (!temp.exists()) {
        temp = new File(path + ".jpg");
      }
      File old = new File(temp.toString());
      old.delete();
      File news = new File(realPath + "WEB-INF/temp/" + "temp." + filename.substring(start));
      FileUtil.copy(import_file, news);
      return new GlobalResult(200, "加载成功", null);
    } catch (Exception e) {

      e.printStackTrace();
      return new GlobalResult(400, "加载失败", null);
    }
  }

  @RequestMapping("/admin/showTempPhoto")
  public void showTempPhoto(HttpServletResponse response, HttpServletRequest request) throws IOException {
    String realPath = request.getSession().getServletContext().getRealPath("/");
    String pa = ".png";
    String path = realPath + "WEB-INF/temp/temp";
    File temp = new File(path + pa);
    if (!temp.exists()) {
      pa = ".jpg";
    }
    File file = new File(path + pa);
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

  @RequestMapping(value = "/admin/updateImage", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult updateImage(String id, String name, String dots, String dotsize) {
    System.out.println("dots:  " + dots);
    if (id == null)
      return new GlobalResult(400, "修改失败", null);
    Image tempImage = new Image(Integer.parseInt(id), name, null, dots, Double.parseDouble(dotsize));
    if (imageservice.updateImage(tempImage) > 0)
      return new GlobalResult(200, "修改成功", null);
    else
      return new GlobalResult(400, "修改失败", null);
  }

  @RequestMapping(value = "/admin/updateChanpinPhoto", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult updateChanpinPhoto(String id, String chanpinId, String name) {
    if (id == null)
      return new GlobalResult(400, "修改失败", null);
    Chanpin chanpin = chanpinservice.findChanpinById(Integer.parseInt(chanpinId));
    if (chanpin == null)
      return new GlobalResult(400, "产品类别不存在", null);
    if (chanpin.getIsparent() == 0 || chanpin.getPid() == 0)
      return new GlobalResult(400, "当前分配的不是产品类别", null);
	/*	List<ChanpinPhoto> list = chanpinPhotoService.getAll();
		for (ChanpinPhoto chanpinPhoto : list) {
			if (chanpinPhoto.getChanpinId() == Integer.parseInt(chanpinId))
				return new GlobalResult(400, "产品已被分配图片", null);
		}*/
    Chanpin parChanpin = chanpinservice.findChanpinById(chanpin.getPid());
    ChanpinPhoto tempImage = new ChanpinPhoto(Integer.parseInt(id), chanpin.getLocation(), parChanpin.getName(),
            Integer.parseInt(chanpinId), name, null);
    if (chanpinPhotoService.updateChanpinPhoto(tempImage) > 0)
      return new GlobalResult(200, "修改成功", null);
    else
      return new GlobalResult(400, "修改失败", null);
  }

  @RequestMapping(value = "/admin/chanpinPhotoImport", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult chanpinPhotoImport(MultipartFile mfile, @PathParam("chanpinId") String chanpinId) {
    Chanpin chanpin = chanpinservice.findChanpinById(Integer.parseInt(chanpinId));
    if (chanpin == null)
      return new GlobalResult(400, "产品类别不存在", null);
    if (chanpin.getIsparent() == 0 || chanpin.getPid() == 0)
      return new GlobalResult(400, "当前分配的不是产品类别", null);
    List<ChanpinPhoto> list = chanpinPhotoService.getAll();
    for (ChanpinPhoto chanpinPhoto : list) {
      if (chanpinPhoto.getChanpinId() == Integer.parseInt(chanpinId))
        return new GlobalResult(400, "产品已被分配图片", null);
    }
    try {
      chanpinPhotoService.doImport(mfile, Integer.parseInt(chanpinId));
      return new GlobalResult(200, "文件上传成功", null);
    } catch (IOException e) {
      e.printStackTrace();
      return new GlobalResult(400, "文件上传失败", null);
    }

  }

  @RequestMapping(value = "/admin/deleteChanpinPhoto", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult deleteChanpinPhoto(String id) {
    if (id == null)
      return new GlobalResult(400, "删除失败", null);
    if (chanpinPhotoService.deleteChanpinPhoto(Integer.parseInt(id)) > 0)
      return new GlobalResult(200, "删除成功", null);
    else
      return new GlobalResult(400, "删除失败", null);
  }

  @RequestMapping(value = "/admin/findAll", method = RequestMethod.POST)
  @ResponseBody
  public List<ChanpinTree> findAll() {
    List<ChanpinTree> list = chanpinservice.findChanpinList();
    // System.out.println(list);
    return list;
  }

  @RequestMapping(value = "/admin/findAllDatameaning", method = RequestMethod.POST)
  @ResponseBody
  public List<DatameaningTree> findAllDatameaning() {
    List<DatameaningTree> list = datameaningService.findDatameaningList();
    //System.out.println(list);
    return list;
  }


  @RequestMapping(value = "/admin/addErrorSet", method = RequestMethod.POST)
  @ResponseBody
  public GlobalResult addErrorSet(String chanpinId, String datameaningId, String isOpen, String value) {
    Integer DatameaningId = null, ChanpinId = null, IsOpen = null;
    if (datameaningId != null && !datameaningId.equals(""))
      DatameaningId = Integer.parseInt(datameaningId);
    if (chanpinId != null && !chanpinId.equals(""))
      ChanpinId = Integer.parseInt(chanpinId);
    if (isOpen != null && !isOpen.equals("") && !isOpen.equals("undefined"))
      IsOpen = Integer.parseInt(isOpen);
    if (datameaningService.findDatameaningById(DatameaningId) == null)
      return new GlobalResult(400, "解析表编号不存在", null);
    if (chanpinservice.findChanpinById(ChanpinId) == null)
      return new GlobalResult(400, "产品编号不存在", null);
    ErrorSet errorSet = new ErrorSet(null, ChanpinId, DatameaningId, IsOpen, null, null, null, value);
    //errorSet.setChanpinId(ChanpinId);
    //errorSet.setDatameaningId(DatameaningId);
    //errorSet.setIsOpen(IsOpen);
    //errorSet.setValue(value);
    if (errorSetService.insertErrorSet(errorSet) > 0)
      return new GlobalResult(200, "添加成功", null);
    else
      return new GlobalResult(400, "添加失败", null);
  }
}
