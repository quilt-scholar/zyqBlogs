package com.zou.zyqblogs.conroller;

import com.github.pagehelper.PageInfo;
import com.zou.zyqblogs.service.*;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ImageVerify;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;


    //查询所有数据
    @PostMapping("/finAll")
    @ResponseBody
    public ResultUtils finall(@RequestParam Map<String,Object> map){
        map.put("orderBy","comment_id desc");
        List<Map<String, Object>> maps = commentService.finallObjects(map);
        PageInfo<Map<String, Object>> list = new PageInfo<Map<String, Object>>(maps);
        return new ResultUtils(CodeEnum.SUCCED,list);
    }

    //批量审核
    @PostMapping("/audits")
    @ResponseBody
    public ResultUtils audits(int[] ids){
        //将审核通过状态与需要审核的ID数组
        Map<String, Object> map = new HashMap<>();
        map.put("comment_status",1);
        map.put("ids",ids);
        //执行批量审核方法
        int i = commentService.auditComments(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //批量删除
    @PostMapping("/dels")
    @ResponseBody
    public ResultUtils dels(int[] ids){
        //将要删除的ID数组，根据指定字段删除
        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        map.put("tableId","comment_id");
        int i = commentService.delObjects(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //删除
    @PostMapping("/del")
    @ResponseBody
    public ResultUtils del(@RequestParam Map<String,Object> map){
        int i =commentService.delObject(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }


    //跳转回复评论页面
    @GetMapping("/commentReply.html")
    public String edit(Model model,int id){
        model.addAttribute("comment_id",id);
        return "/afterEnd/children/commentReply";
    }

    //回复评论操作
    @PostMapping("/reply")
    @ResponseBody
    public ResultUtils reply(@RequestParam Map<String,Object> map){
        //将传递的id放在数组中，并添加到map中
        int[] ids= {Integer.parseInt((String) map.get("ids"))};
        map.put("ids",ids);
        int i = commentService.auditComments(map);//执行回复评论
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
    //前端页面添加评论
    @PostMapping("/add")
    @ResponseBody
    public ResultUtils add(@RequestParam Map<String,Object> map){
        //判断验证码是否正确
        if(!map.get("verify").toString().equals(ImageVerify.getVerify())){
            return new ResultUtils(CodeEnum.BEDEFEATED,null);
        }
        map.remove("verify");//删除验证码
        //将map键值分离，在存入新map中
        String[] keys={"keys", "values"};
        map= ParamsUtil.saveMap(keys,map.keySet(),map.values());

        int i = commentService.addObjects(map);//执行添加回复操作
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

}
