package com.zou.zyqblogs.service.impl;

import com.zou.zyqblogs.mapper.LinkMapper;
import com.zou.zyqblogs.mapper.MenuMapper;
import com.zou.zyqblogs.service.GeneralService;
import com.zou.zyqblogs.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl extends GeneralServiceImpl implements MenuService {
    @Autowired
    private MenuMapper mm;

    public MenuServiceImpl() {
        super("tb_menu");
    }

    @Override
    public int uploadMenu(Map<String, Object> map) {
        return mm.uploadMenu(map);
    }
}
