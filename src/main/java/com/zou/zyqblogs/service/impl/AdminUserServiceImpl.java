package com.zou.zyqblogs.service.impl;

import com.zou.zyqblogs.mapper.AdminUserMapper;
import com.zou.zyqblogs.pojo.AdminUser;
import com.zou.zyqblogs.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional//在service层开启事务使用
//继承通用service，实现自己的service
public class AdminUserServiceImpl extends GeneralServiceImpl implements AdminUserService {
    //注入用户mapper
    @Autowired
    private AdminUserMapper aum;

    //将该流程使用的表传递给通用service中
    public AdminUserServiceImpl(){
        super("tb_admin_user");
    }

    //验证账号密码是否正确
    @Override
    public AdminUser queryAdminUserByUser(AdminUser au) {
        return aum.queryAdminUserByUser(au);
    }

    //修改用户信息
    @Override
    public int updAdminUser(Map<String, Object> map) {
        return aum.updAdminUser(map);
    }
}
