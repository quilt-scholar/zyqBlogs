package com.zou.zyqblogs.conroller;

import com.github.pagehelper.PageInfo;
import com.zou.zyqblogs.service.BlogService;
import com.zou.zyqblogs.service.CategoryService;
import com.zou.zyqblogs.service.ConfigService;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;

    //查询分类表中所有数据
    @PostMapping("/finAll")
    @ResponseBody
    public ResultUtils finAll(@RequestParam Map<String,Object> map){
        map.put("orderBy","category_rank desc");//将orderBy添加到map
        //执行查询操作
        List<Map<String, Object>> list = categoryService.finallObjects(map);
        PageInfo<Map<String, Object>> pageInfo=new PageInfo<>(list);
        return new ResultUtils(CodeEnum.SUCCED,pageInfo);
    }

    //添加分类跳转
    @GetMapping("/categoryAdd.html")
    public String categoryAdd(){
        return "/afterEnd/children/categoryAdd.html";
    }

    //分类添加处理
    @PostMapping("/addCategory")
    @ResponseBody
    public ResultUtils addCategory(@RequestParam Map<String,Object> map){
        //将参数map转为Set(key)、Collection(val);
        //添加分类，将map参数blog分为key，与val集合,并存入新map中
        String[] keys= {"keys", "values"};
        map= ParamsUtil.saveMap(keys,map.keySet(),map.values());

        int i = categoryService.addObjects(map);//执行添加操作
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //批量删除操作
    @PostMapping("/dels")
    @ResponseBody
    public ResultUtils dels(Integer[] ids){
        int i = categoryService.dels(ids, blogService);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //删除分类
    @PostMapping("/del")
    @ResponseBody
    public ResultUtils del(@RequestParam Map<String,Object> map){
        //因为删除需要将其他的业务方法，所以需要事务管理
        int i = categoryService.del(map,blogService);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //修改分类页面跳转
    @GetMapping("/categoryEdit.html")
    public String edit(Model m, @RequestParam Map<String,Object> map){
        //根据ID获取指定分类，存入model
        m.addAttribute("category",categoryService.finallObjectById(map));
        return "/afterEnd/children/categoryUpd";
    }
    //修改分类操作
    @PostMapping("/updCategory")
    @ResponseBody
    public ResultUtils updCategory(@RequestParam Map<String,Object> map){
        int i = categoryService.updCategory(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
}
