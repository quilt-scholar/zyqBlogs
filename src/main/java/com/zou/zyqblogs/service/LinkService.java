package com.zou.zyqblogs.service;


import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface LinkService extends GeneralService {
    public int updLink(Map<String,Object> map);
}
