package com.zou.zyqblogs.conroller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zou.zyqblogs.service.ConfigService;
import com.zou.zyqblogs.service.TagService;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    //查询所有标签
    @PostMapping("/finall")
    @ResponseBody
    public ResultUtils finall(@RequestParam Map<String,Object> map){
        map.put("orderBy","tag_id desc");
        List<Map<String, Object>> list = tagService.finallObjects(map);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return new ResultUtils(CodeEnum.SUCCED,pageInfo);
    }

    //根据标签名查询
    @PostMapping("/queryByName")
    @ResponseBody
    public ResultUtils queryByName(@RequestParam Map<String,Object> map){
        map.put("orderBy","tag_id desc");
        //创建一个map,判断需要模糊查询的key是否存在
        Map<String, Object> like = new HashMap<>();
        if(map.get("tag_name")!=null) {//存在则将它存入like
            like.put("tag_name", map.get("tag_name"));
            map.remove("tag_name");//将参数map中的blog_title删除
        }
        map.put("like",like);//价格模糊查询map存入参数map

        List<Map<String, Object>> list = tagService.finallObjects(map);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return new ResultUtils(CodeEnum.SUCCED,pageInfo);
    }

    //批量删除
    @PostMapping("/dels")
    @ResponseBody
    public ResultUtils dels(int[] ids){
        HashMap<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        map.put("tableId","tag_id");
        int i = tagService.delObjects(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //添加标签页面
    @GetMapping("/tagAdd.html")
    public String tagAdd(){
        return "/afterEnd/children/tagAdd";
    }
    //添加操作
    @PostMapping("/add")
    @ResponseBody
    public ResultUtils add(@RequestParam Map<String,Object> map){
        System.out.println("tag_name:"+map.get("tag_name"));
        //添加标签，将map参数blog分为key，与val集合,并存入新map中
        String[] keys={"keys", "values"};
        map= ParamsUtil.saveMap(keys,map.keySet(),map.values());

        int i = tagService.addObjects(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
    //删除
    @PostMapping("/del")
    @ResponseBody
    public ResultUtils del(@RequestParam Map<String,Object> map){
        int i = tagService.delObject(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
    //修改标签页面
    @GetMapping("/tagEdit.html")
    public String tagEdit(Model model,@RequestParam Map<String,Object> map){
        model.addAttribute("tag",tagService.finallObjectById(map));
        return "/afterEnd/children/tagEdit";
    }
    //修改
    @PostMapping("/edit")
    @ResponseBody
    public ResultUtils edit(@RequestParam Map<String,Object> map){
        int i = tagService.updTag(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

}
