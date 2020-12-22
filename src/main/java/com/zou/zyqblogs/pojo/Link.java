package com.zou.zyqblogs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private int linkId; //友链Id
    private int linkType; //友链类别
    private String linkName; //友链名称
    private String linkUrl; //友链链接
    private String linkDescription; //友链描述
    private int linkRank; //友链排序值
    private int isDeleted; //是否删除
    private Date createTime; //创建时间
}
