package com.zou.zyqblogs.mapper;

import com.zou.zyqblogs.pojo.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

//@Mapper
@Repository //需要加dao层的注册bean的注解，否则service将会找不到
public interface AdminUserMapper extends GeneralMapper {
    //验证账号密码是否正确
    public AdminUser queryAdminUserByUser(AdminUser au);
    //修改用户信息
    public int updAdminUser(@Param("maps") Map<String,Object> map);
}
