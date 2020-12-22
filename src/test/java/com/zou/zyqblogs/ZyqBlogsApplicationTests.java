package com.zou.zyqblogs;

import com.zou.zyqblogs.pojo.Blog;
import com.zou.zyqblogs.service.*;
import com.zou.zyqblogs.utils.MD5Util;
import com.zou.zyqblogs.utils.ParamsUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;
import sun.security.rsa.RSASignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@SpringBootTest
class ZyqBlogsApplicationTests {
    @Autowired
    private MenuService ms;

    @Test
    void contextLoads() throws Exception {
        List<Map<String, Object>> maps = ms.finallObjects(new HashMap<>());
        List<Map<String, Object>> mapss = ParamsUtil.fatherAndSonMenuList(maps, 0);
        for (Map<String, Object> m : mapss) {
            for (Map.Entry<String, Object> mm : m.entrySet()) {
                System.out.println(mm.getKey()+"----------------"+mm.getValue());
            }
            System.out.println("============");
        }
    }

}
