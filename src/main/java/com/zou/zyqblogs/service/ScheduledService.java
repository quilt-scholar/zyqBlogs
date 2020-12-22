package com.zou.zyqblogs.service;

import com.zou.zyqblogs.mapper.ScheduledMapper;
import com.zou.zyqblogs.mapper.WebsiteVisitsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ScheduledService {
    @Autowired
    private ScheduledMapper sm;
    @Autowired
    private WebsiteVisitsMapper wvm;

    //cron表达式: 秒  分  时  日  月  周几
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleDelImg(){
        //标记flag，用于标记文件是否被使用，使用为true，未被使用则为false
        boolean flag=false;
        //储存上传文件的的文件对象
        File uploadFile = new File(System.getProperty("user.dir") + "/upload");
        //查询使用了图片的表中图片字段数据，整合为一个字段查询出来，用‘,’分割
        List<Map<String, Object>> maps = sm.finAllImgDel();
        //获取上传文件夹下所有文件目录数组，并循环遍历
        for (File file : uploadFile.listFiles()) {
            flag=false;//每遍历一次文件，初始化标记为false
            if (file.isFile()){//判断文件是否为文件
                //循环遍历查询出来的数据List
                for (Map<String, Object> map : maps) {
                    //将遍历出来的map中的图片路径字段通过‘,’分割
                    String[] delimgs = map.get("delimg").toString().split(",");
                    for (String delimg : delimgs) {//循环分割的数组
                        //将遍历出来的字符串，截取掉前面的/upload/
                        delimg=delimg.substring(delimg.lastIndexOf("/")+1);
                        //判断该文件是否与查询粗来的数据相等，相等着将flag赋值为true
                        if(file.getName().equals(delimg)){//不相等则不改变flag
                            flag=true;
                        }
                    }
                }
                //再在后面判断flag，为true则该文件被使用不会删除，反之删除
                if(!flag){
                    file.delete();
                }
            }
        }

    }

    @Scheduled(cron="0 0 0 * * 1")
    public void scheduleDel(){
        //删除近一个月内以外的访问量数据
        int i = wvm.delExceptNear1MonthWithinVisitor();
    }
}
