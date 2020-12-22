package com.zou.zyqblogs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.zou.zyqblogs.mapper")//扫描mapper接口
@SpringBootApplication
@EnableTransactionManagement//默认是开启的，开启事务管理
@EnableScheduling //开启基于注解的定时任务
//@ServletComponentScan//扫描Servlet过滤器，扫描Servlet过滤器
public class ZyqBlogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZyqBlogsApplication.class, args);
    }

}
