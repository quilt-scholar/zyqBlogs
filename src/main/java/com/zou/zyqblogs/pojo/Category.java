package com.zou.zyqblogs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int categoryId; //分类Id
    private String categoryName; //分类名称
    private String categoryIcon; //分类图标
    private String categoryRank; //分类排序值
    private int isDeleted; ////是否删除 0-未删除 1-删除
    private Date createTime; //创建时间
}
