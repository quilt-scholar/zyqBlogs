package com.zou.zyqblogs.conroller;

import com.github.pagehelper.PageInfo;
import com.zou.zyqblogs.service.ConfigService;
import com.zou.zyqblogs.service.LinkService;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.*;

@Controller
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    //查询所有友链
    @PostMapping("/finall")
    @ResponseBody
    public ResultUtils finall(@RequestParam Map<String,Object> map) {
        map.put("orderBy","link_rank desc");
        List<Map<String, Object>> list = linkService.finallObjects(map);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return new ResultUtils(CodeEnum.SUCCED, pageInfo);
    }

    //添加标签页面
    @GetMapping("/linkAdd.html")
    public String linkAdd(){
        return "afterEnd/children/linkAdd";
    }
    //添加友链
    @PostMapping("/add")
    @ResponseBody
    public ResultUtils add(@RequestParam Map<String,Object> map){
        //添加标签，将map参数blog分为key，与val集合,并存入新map中
        String[] keys= {"keys", "values"};
        map= ParamsUtil.saveMap(keys,map.keySet(),map.values());

        int i = linkService.addObjects(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //批量删除
    @PostMapping("/dels")
    @ResponseBody
    public ResultUtils dels(int[] ids){
        Map<String,Object> map=new HashMap<>();
        map.put("ids",ids);
        map.put("tableId","link_id");

        int i = linkService.delObjects(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
    //删除
    @PostMapping("/del")
    @ResponseBody
    public ResultUtils del(@RequestParam Map<String,Object> map){
        int i = linkService.delObject(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //修改友链页面
    @GetMapping("/linkEdit.html")
    public String linkEdit(Model model,@RequestParam Map<String,Object> map){
        model.addAttribute("link",linkService.finallObjectById(map));
        return "afterEnd/children/linkEdit";
    }
    //修改
    @PostMapping("/edit")
    @ResponseBody
    public ResultUtils edit(@RequestParam Map<String,Object> map){
        int i = linkService.updLink(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
}
