package com.nanjing.lightingdection.controller;

import com.nanjing.lightingdection.entity.Login;
import com.nanjing.lightingdection.entity.User;
import com.nanjing.lightingdection.service.LoginService;
import com.nanjing.lightingdection.service.UserService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  @Autowired private UserService userService;
  @Autowired private LoginService loginService;

  @RequestMapping(value = "/system/login", method = RequestMethod.GET)
  public ModelAndView login(ModelAndView model, Model model1) {
    Login a = loginService.getAll();
    String companyname = a.getCompanyname();
    String jiekou = a.getJiekou();
    String xieyi = a.getXieyi();
    String name = a.getName();
    model1.addAttribute("companyname", companyname);
    model1.addAttribute("jiekou", jiekou);
    model1.addAttribute("xieyi", xieyi);
    model1.addAttribute("name", name);

    model.setViewName("system/login");
    return model;
  }

  @RequestMapping("/system/loginImage")
  public void showLoginImage(HttpServletResponse response, HttpServletRequest request)
      throws IOException {
    Login a = loginService.getAll();
    String realPath = request.getSession().getServletContext().getRealPath("/");
    File file = new File(realPath + a.getUrl());
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

  @RequestMapping(value = "/system/login", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> login(
      @RequestParam(value = "username", required = true) String id,
      @RequestParam(value = "password", required = true) String psw,
      @RequestParam(value = "type", required = true) int type,
      HttpServletRequest request) {
    Map<String, String> ret = new HashMap<String, String>();
    // System.out.println("type: "+type);
    // System.out.println("name: "+id);
    // System.out.println("psw: "+psw);
    if (StringUtils.isEmpty(id)) {
      ret.put("type", "error");
      ret.put("msg", "用户名不能为空!");
      return ret;
    }
    if (StringUtils.isEmpty(psw)) {
      ret.put("type", "error");
      ret.put("msg", "密码不能为空!");
      return ret;
    }
    if (!(type == 1 || type == 2)) {
      ret.put("type", "error");
      ret.put("msg", "请选择用户类型！");
      return ret;
    }
    // 从数据库中去查找用户
    User user = userService.queryById(id);
    // System.out.println(user);
    if (type == 1) {

      if (user == null) {
        ret.put("type", "error");
        ret.put("msg", "不存在该用户!");
        // win.setMember("ret", ret);
        return ret;
      }
      int a = userService.isAdmin(user.getUser_code());
      // System.out.println("a: "+a);
      if (a == 1) {
        if (!psw.equals(user.getUser_pwd())) {
          ret.put("type", "error");
          ret.put("msg", "密码错误!");
          // win.setMember("ret", ret);
          return ret;
        }
      } else {
        ret.put("type", "error");
        ret.put("msg", "不存在该用户!");
        // win.setMember("ret", ret);
        return ret;
      }
    } else {
      // 系统用户

      if (user == null) {
        ret.put("type", "error");
        ret.put("msg", "不存在该用户!");
        // win.setMember("ret", ret);
        return ret;
      }
      int b = userService.isAdmin(user.getUser_code());
      if (b == 0) {
        if (!psw.equals(user.getUser_pwd())) {
          ret.put("type", "error");
          ret.put("msg", "密码错误!");
          // win.setMember("ret", ret);
          return ret;
        }
      } else {
        ret.put("type", "error");
        ret.put("msg", "不存在该用户!");
        // win.setMember("ret", ret);
        return ret;
      }
    }
    request.getSession().setAttribute("user", user);
    request.getSession().setMaxInactiveInterval(86400 * 365);
    request.getSession().setAttribute("userType", type);
    ret.put("catelog", type + "");
    ret.put("type", "success");
    ret.put("msg", "登录成功!");
    return ret;
  }

  @RequestMapping(value = "/user/login_out", method = RequestMethod.GET)
  public String loginOut(HttpServletRequest request) {
    request.getSession().setAttribute("user", null);
    return "redirect:/system/login";
  }
}
