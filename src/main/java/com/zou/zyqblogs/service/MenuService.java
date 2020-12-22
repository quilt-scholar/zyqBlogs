package com.zou.zyqblogs.service;

import com.zou.zyqblogs.mapper.GeneralMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface MenuService extends GeneralService {
    int uploadMenu(Map<String,Object> map);
}
