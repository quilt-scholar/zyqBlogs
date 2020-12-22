package com.zou.zyqblogs.monitor;

import com.zou.zyqblogs.service.BlogService;
import com.zou.zyqblogs.service.WebsiteVisitsService;
import com.zou.zyqblogs.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

@Component
//统计当天网站访问人数：统计session
public class CountListeren implements HttpSessionListener {
    @Autowired
    private WebsiteVisitsService wvs;

    //创建session监听：看你的一举一动
    //一但创建session就会触发一次该监听事件
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //查询表今天内是否有当天的网站访问量信息
        Map<String, Object> map = wvs.websiteDayVisits();
        if (map!=null) {//有则就修改该数据中访问量，+1
            map.put("day_visits",Integer.parseInt(map.get("day_visits").toString())+1);
            wvs.updDayVisits(map);
        }else{//没有则在表中创建当天数据
            //使用工具类将需要的数据封装为map
            map=new HashMap<>();
            map.put("day_visits",1);
            String[] keys= new String[]{"keys", "values"};
            map= ParamsUtil.saveMap(keys,map.keySet(),map.values());
            wvs.addObjects(map);
        }
    }

    //销毁session监听
    //一但注销session就会触发一次该监听事件
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}