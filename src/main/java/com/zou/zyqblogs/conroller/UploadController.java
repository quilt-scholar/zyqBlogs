package com.zou.zyqblogs.conroller;

import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadController {
    public static String image;//保存上传
    public static int i=1;

    //文章封面图，内容图片上传
    @PostMapping("/article/upload")
    @ResponseBody
    public Map<String,Object> articleUpload(@RequestParam(value = "editormd-image-file") MultipartFile file) throws IOException {
        //获取上传文件的名称和文件的后缀
        String fileName = file.getOriginalFilename();
        String fileSuffix=fileName.substring(fileName.indexOf("."));
        //获取上传文件保存路径，并判断文件夹是否存在，不存在则创建
        File nativefile= new File(System.getProperty("user.dir")+"/upload");
        if(!nativefile.exists()){
            nativefile.mkdirs();
        }

        //通过UUID获取上传后文件的名称
        fileName= UUID.randomUUID().toString()+fileSuffix;

        Map<String,Object> res = new HashMap<>();
        try {
            file.transferTo(new File(nativefile+"//"+fileName));
            if(i==1) {
                image = "/upload/" + fileName;
                i++;
            }
            //给editormd进行回调
            res.put("url","/upload/"+fileName);
            res.put("success", 1);
            res.put("message", "upload success!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
    //文章封面修改
    @PostMapping("/editIcon")
    @ResponseBody
    public ResultUtils articleIconEdit(@RequestParam(value = "file") MultipartFile file,String filename){
        //获取上传文件的名称和文件的后缀
        String fileName = file.getOriginalFilename();
        String fileSuffix=fileName.substring(fileName.indexOf("."));
        if(!filename.contains("/upload") || filename==null){
            filename="/upload/"+UUID.randomUUID().toString()+fileSuffix;
        }
        try {
            file.transferTo(new File(System.getProperty("user.dir")+filename));
            return new ResultUtils(CodeEnum.SUCCED,filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //分类图标上传
    @PostMapping("/category/upload")
    @ResponseBody
    public ResultUtils categoryUpload(@RequestParam(value = "file") MultipartFile file) {
        //获取上传文件的名称和文件的后缀
        String fileName = file.getOriginalFilename();
        String fileSuffix=fileName.substring(fileName.indexOf("."));
        //获取上传文件保存路径，并判断文件夹是否存在，不存在则创建
        File nativefile= new File(System.getProperty("user.dir")+"/upload");
        if(!nativefile.exists()){
            nativefile.mkdirs();
        }

        //通过UUID获取上传后文件的名称
        fileName= UUID.randomUUID().toString()+fileSuffix;

        Map<String,Object> res = new HashMap<>();
        try {
            file.transferTo(new File(nativefile+"//"+fileName));
            res.put("category_icon","/upload/"+fileName);
            return new ResultUtils(CodeEnum.SUCCED,res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

}
