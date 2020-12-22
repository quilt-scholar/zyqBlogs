package com.zou.zyqblogs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private int blogId; //博客Id
    private String blogTitle; //标题
    private String blogSubUrl; //自定义URL
    private String blogCoverImage; //封面图
    private String blogContent; //内容
    private int blogCategoryId; //分类Id
    private String blogCategoryName; //分类名称
    private String[] blogTags; //标签
    private String blogStatus; //状态 0-草稿 1-发布
    private int blogViews; //流量
    private int enable_comment; //是否允许评论
    private int isDeleted; //是否删除
    private Date createTime; //创建时间
    private Date updateTime; //修改时间
}
