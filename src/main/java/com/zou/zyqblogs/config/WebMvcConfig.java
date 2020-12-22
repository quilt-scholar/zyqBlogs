package com.zou.zyqblogs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zou.zyqblogs.Interceptors.DiyUrlInterceptor;
import com.zou.zyqblogs.Interceptors.LoginInterceptor;
import com.zou.zyqblogs.Interceptors.XssInterceptor;
import com.zou.zyqblogs.filter.XssStringJsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequestWrapper;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    //如果拦截器需要注入Service，可以将拦截器注入到配置类中在添加拦截器，
    // 也可以将拦截器注册为bean，在添加拦截器
    @Autowired
    private DiyUrlInterceptor dui;
    //注意使用注解注册bean，无法注入，因为它是new的对象，而不是bean
//    @Bean
//    DiyUrlInterceptor getDiyUrlInterceptor() {
//        return new DiyUrlInterceptor();
//    }
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new XssInterceptor()).addPathPatterns("/**");
        //根据添加顺序进入拦截器
        registry.addInterceptor(dui).addPathPatterns("/**")
                .excludePathPatterns("/commentfinall","/comment/add","/blogFilter","/blogViews","/","/index.html","/links.html","/article/**","/upload/**","/img","/login.html","/login","/css/**","/js/**","/images/**","/editor/**","/layui/**");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/commentfinall","/comment/add","/blogFilter","/blogViews","/","/index.html","/links.html","/article/**","/upload/**","/img","/login.html","/login","/css/**","/js/**","/images/**","/editor/**","/layui/**");
    }


    //添加视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("/afterEnd/Login/login");
//        registry.addViewController("/index.html").setViewName("/leadingEnd/index");
//        registry.addViewController("/").setViewName("/leadingEnd/index");
//        registry.addViewController("/main.html").setViewName("/afterEnd/main/main");
//        registry.addViewController("/addBlog.html").setViewName("/afterEnd/main/writeBlogs");
//        registry.addViewController("/blog.html").setViewName("/afterEnd/admin/blog");
//        registry.addViewController("/comment.html").setViewName("/afterEnd/admin/comment");
//        registry.addViewController("/category.html").setViewName("/afterEnd/admin/category");
//        registry.addViewController("/tag.html").setViewName("/afterEnd/admin/tag");
//        registry.addViewController("/link.html").setViewName("/afterEnd/admin/link");
//        registry.addViewController("/set.html").setViewName("/afterEnd/system/set");
//        registry.addViewController("/pass.html").setViewName("/afterEnd/system/pass");
    }

    //添加资源处理器
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+"/upload/");
    }

    /**
     * 过滤json类型的
     * @param builder
     * @return
     */
    @Bean
    @Primary
    public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
        //解析器
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //注册xss解析器
        SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");
        xssModule.addSerializer(new XssStringJsonSerializer());
        objectMapper.registerModule(xssModule);
        //返回
        return objectMapper;
    }
}
