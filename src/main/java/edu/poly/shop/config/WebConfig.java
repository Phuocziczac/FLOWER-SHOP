package edu.poly.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.poly.shop.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // Đảm bảo interceptor áp dụng cho tất cả các đường dẫn
                .excludePathPatterns("/site/account/login", "/site/account/Register"); // Các đường dẫn không cần interceptor
    }
}

