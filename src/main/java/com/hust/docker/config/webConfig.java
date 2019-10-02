package com.hust.docker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class webConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //springboot 2.0由于拦截器的存在，springboot不会给默认相对路径加上/static，因此需要拦截器中添加放行资源处添加静态资源文件路径
        //这样就不会拦截static目录下的文件，并且不需要在html引用资源的相对路径上加上/static了。
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}