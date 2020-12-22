package com.zou.zyqblogs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private int tagId; //标签Id
    private String tagName; //标签名称
    private int isDeleted; ////是否删除 0-未删除 1-删除
    private Date createTime; //创建时间
}
