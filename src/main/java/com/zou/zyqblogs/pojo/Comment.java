package com.zou.zyqblogs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentId; //评论id
    private int blogId; //关联博客id
    private String commentator; //评论人
    private String email; //评论人邮箱
    private String websiteUrl; //网址
    private String commentBody; //评论内容
    private Date commentCreateTime; //发起评论时间
    private String commentatorIp; //评论时ip地址
    private String replyBody; //回复内容
    private Date replyCreateTime; //回复的时间
    private int commentStatus; //评论的状态 0-未审核 1-审核
    private int isDeleted; //是否删除 0-未删除 1-删除

}
