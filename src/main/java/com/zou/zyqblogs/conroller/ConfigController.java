package com.zou.zyqblogs.conroller;

import com.github.pagehelper.PageInfo;
import com.zou.zyqblogs.service.ConfigService;
import com.zou.zyqblogs.service.MenuService;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ConfigController {
    @Autowired
    public ConfigService configService;
    @Autowired
    public MenuService menuService;


    @GetMapping("/menu.html")
    public String menu(Model model){
        //查询转换为键值对形式的配置数据
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "afterEnd/system/menu";
    }
    @PostMapping("/finAllMenu")
    @ResponseBody
    public ResultUtils finAllMenu(@RequestParam Map<String,Object> map){
        map.put("orderBy","id desc");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(menuService.finallObjects(map));
        return new ResultUtils(CodeEnum.SUCCED,pageInfo);
    }
    @GetMapping("/menuAdd.html")
    public String menuAdd(){
        return "/afterEnd/children/MenuAdd";
    }
    @PostMapping("/finall")
    @ResponseBody
    public ResultUtils finAllMenu(){
        List<Map<String, Object>> maps = menuService.finallObjects(new HashMap<>());
        maps= ParamsUtil.fatherAndSonMenuList(maps,0);
        return new ResultUtils(CodeEnum.SUCCED,maps);
    }
    @PostMapping("/menuAdd")
    @ResponseBody
    public ResultUtils menuAdd(@RequestParam Map<String,Object> map){
        //将参数map转为Set(key)、Collection(val);
        //将map参数blog分为key，与val集合,并存入新map中
        String[] keys= {"keys", "values"};
        map= ParamsUtil.saveMap(keys,map.keySet(),map.values());

        int i = menuService.addObjects(map);//执行添加操作
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
    @PostMapping("/delMenu")
    @ResponseBody
    public ResultUtils delMenu(@RequestParam Map<String,Object> map){
        int i = menuService.delObject(map);//执行添加操作
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
    @GetMapping("/menuEdit.html")
    public String menuAdd(Model model,@RequestParam Map<String,Object> map){
        model.addAttribute("menu",menuService.finallObjectById(map));
        return "/afterEnd/children/MenuEdit";
    }
    @PostMapping("/editMenu")
    @ResponseBody
    public ResultUtils editMenu(@RequestParam Map<String,Object> map){
        int i = menuService.uploadMenu(map);//执行添加操作
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }


    //配置页面跳转
    @GetMapping("/set.html")
    public String set(Model model){
        //查询转换为键值对形式的配置数据
        model.addAttribute("set",configService.finallConfig());
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));

        return "afterEnd/system/set";
    }

    //配置信息修改
    @PostMapping("/websiteEdit")
    @ResponseBody
    public ResultUtils websiteEdit(@RequestParam Map<String,Object> map){
        Map<String,Object> result=new HashMap<>();
        int i=0;
        for (Map.Entry<String, Object> o : map.entrySet()) {//遍历map
            result.put("config_name",o.getKey());//将map中的键值对覅分别存入新的map中
            result.put("config_value",o.getValue());
            i+=configService.updWebsite(result);//执行修改操作
        }
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

//    @PostMapping("/yourEdit")
//    @ResponseBody
//    public ResultUtils yourEdit(@RequestParam Map<String,Object> map){
//        Map<String,Object> result=new HashMap<>();
//        int i=0;
//        for (Map.Entry<String, Object> o : map.entrySet()) {
//            result.put("config_name",o.getKey());
//            result.put("config_value",o.getValue());
//            i+=configService.updWebsite(result);
//        }
//        if(i>0){
//            return new ResultUtils(CodeEnum.SUCCED,null);
//        }
//        return new ResultUtils(CodeEnum.BEDEFEATED,null);
//    }
//
//    @PostMapping("/footerEdit")
//    @ResponseBody
//    public ResultUtils footerEdit(@RequestParam Map<String,Object> map){
//        Map<String,Object> result=new HashMap<>();
//        int i=0;
//        for (Map.Entry<String, Object> o : map.entrySet()) {
//            result.put("config_name",o.getKey());
//            result.put("config_value",o.getValue());
//            i+=configService.updWebsite(result);
//        }
//        if(i>0){
//            return new ResultUtils(CodeEnum.SUCCED,null);
//        }
//        return new ResultUtils(CodeEnum.BEDEFEATED,null);
//    }
}
