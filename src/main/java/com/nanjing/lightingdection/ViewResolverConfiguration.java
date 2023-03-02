package com.nanjing.lightingdection;

import com.nanjing.lightingdection.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
/*
 *
 * 用于配置对jsp和html访问的视图解析器*/
public class ViewResolverConfiguration {
  @Configuration // 用来定义 DispatcherServlet 应用上下文中的 bean
  @EnableWebMvc
  @ComponentScan("com.nanjing.lightingdection")
  public class WebConfig implements WebMvcConfigurer {
    @Bean
    public InternalResourceViewResolver htmlViewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/html/");
      viewResolver.setViewClass(HandleResourceViewExists.class); // 设置检查器
      viewResolver.setSuffix(".html");
      viewResolver.setOrder(0);
      viewResolver.setContentType("text/html;charset=UTF-8");
      return viewResolver;
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setViewClass(HandleResourceViewExists.class); // 设置检查器
      viewResolver.setPrefix("/jsp/");
      viewResolver.setSuffix(".jsp");
      viewResolver.setOrder(0);
      viewResolver.setContentType("text/html;charset=UTF-8");
      return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry
          .addInterceptor(new LoginInterceptor())
          .addPathPatterns("/**")
          .excludePathPatterns("/system/login") // 过滤掉登录页面
          .excludePathPatterns("/static/**")
          .excludePathPatterns("/test"); // 过滤掉静态资源
    }
  }
}
