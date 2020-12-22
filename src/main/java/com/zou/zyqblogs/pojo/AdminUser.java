package com.zou.zyqblogs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser {
    private int adminUserId;
    private String loginUserName;
    private String loginPassword;
    private String nickName;
    private byte locked;
}
