package com.zou.zyqblogs.utils;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zou.zyqblogs.service.AdminUserService;
import com.zou.zyqblogs.service.BlogService;
import com.zou.zyqblogs.service.ConfigService;
import com.zou.zyqblogs.service.TagService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsUtil {
    //获取菜单父子级分明的菜单列表
    public static List<Map<String,Object>> fatherAndSonMenuList(List<Map<String,Object>> maps,int pid){
        List<Map<String,Object>> list=new ArrayList<>();//创建一个List<Map<>>用来接收新的map
        for (Map<String, Object> map : maps) {//遍历maps(查询出来的所有菜单)
            //将ID与pid转为int
            Integer mPid=Integer.parseInt(map.get("pid").toString());
            Integer mId=Integer.parseInt(map.get("id").toString());
            if(mPid==pid){//判断遍历的mpid是否等于参数pid
                //相等则递归该方法，将所有菜单maps与当前的mId(作为pid)，传递进去
                //判断有没有子菜单，有就继续判断，直到没有返回的就是最终的菜单栏
                map.put("children",fatherAndSonMenuList(maps,mId));//将子菜单存入当前map中
                list.add(map);//添加到姓list中
            }
        }
        return list;
    }


    //前端页面公共数据提取
    public static Map<String, Object> indexPublicDataExtract(BlogService blogService
            , AdminUserService adminUserService, TagService tagService, ConfigService configService){
        HashMap<String, Object> maps = new HashMap<>();
        //获取需要存入新map数据的key，val，执行存入操作
        String[] keys={"page","limit","orderBy"};
        Map<String, Object> map=ParamsUtil.saveMap(keys,"1","5","create_time desc");
        maps.put("blogsTime",blogService.finallObjects(map));

        //获取需要存入新map数据的key，val，执行存入操作
        keys= new String[]{"page", "limit", "orderBy"};
        map=ParamsUtil.saveMap(keys,"1","5","blog_views desc");
        maps.put("blogsViews",blogService.finallObjects(map));

        //创建一个map，存入要查询字段的键值对
        map = new HashMap<>();
        map.put("admin_user_id", 1);
        maps.put("admin",adminUserService.finallObjectById(map));

        //查询标签并查询出使用该标签的博文数
        maps.put("tags",tagService.queryTagsNum(1,6,"num desc"));

        //查询配置表所有数据
        maps.put("set",configService.finallConfig());
        return maps;
    }

    //将需要的数据存入map：需要创建新map的情况使用
    public static Map<String,Object> saveMap(String[] keys, Object... obj){
        Map<String,Object> map=new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i],obj[i]);
        }
        return map;
    }
    //将需要的数据存入map：需要给map添加数据的情况使用
    public static Map<String,Object> saveMap(Map<String,Object> map,String[] keys, Object... obj){
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i],obj[i]);
        }
        return map;
    }
}
