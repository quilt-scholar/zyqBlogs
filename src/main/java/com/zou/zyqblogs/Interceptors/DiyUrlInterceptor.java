package com.zou.zyqblogs.Interceptors;

import com.zou.zyqblogs.conroller.BlogController;
import com.zou.zyqblogs.service.BlogService;
import com.zou.zyqblogs.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DiyUrlInterceptor implements HandlerInterceptor {

    @Autowired
    BlogService blogService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        //获取不包含参数的请求URL，并截取到最后一个/后面的字符串
        String url = request.getRequestURL().toString();
        url=url.substring(url.lastIndexOf("/")+1);
        System.out.println(url);
        //创建一个map将url存进去
        Map<String, Object> map = new HashMap<>();
        map.put("blog_sub_url",url);
        //判断注入的blogService是否为空
        if (blogService!=null) {
            //根据blog_sub_url查询数据，并判断是否存在
            map = blogService.finallObjectById(map);
            if(map!=null){//存在则跳转到对应的页面
                request.getRequestDispatcher("/article/"+map.get("blog_id")).forward(request,response);
                return false;
            }
        }

        return true;
    }
}
