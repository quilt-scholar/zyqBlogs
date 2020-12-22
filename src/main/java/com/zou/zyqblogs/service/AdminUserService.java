package com.zou.zyqblogs.service;

import com.zou.zyqblogs.pojo.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface AdminUserService extends GeneralService {
    //验证账号密码是否正确
    public AdminUser queryAdminUserByUser(AdminUser au);
    //修改用户信息
    public int updAdminUser(Map<String,Object> map);
}
