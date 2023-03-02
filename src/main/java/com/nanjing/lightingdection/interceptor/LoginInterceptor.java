package com.nanjing.lightingdection.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // System.out.println("preHandle");
    String url = request.getRequestURI();
    HttpSession session = request.getSession();
    if (session.getAttribute("user") == null && !url.equals("/test")) { // 校验登录标记
      // request.getRequestDispatcher("/login").forward(request, response);  //对于未登录的用户跳转到登录页面
      System.out.println(request.getContextPath());
      response.sendRedirect(request.getContextPath() + "/system/login");
      return false;
    }
    return true;
  }
}
