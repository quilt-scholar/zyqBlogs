package com.zou.zyqblogs.service.impl;

import com.zou.zyqblogs.mapper.ConfigMapper;
import com.zou.zyqblogs.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ConfigServiceImpl extends GeneralServiceImpl implements ConfigService {
    @Autowired
    public ConfigMapper configMapper;

    public ConfigServiceImpl(){
        super("tb_config");
    }

    //根据配置名称查询数据，需要将其转为(config_name的val,config_value的val)形式，否则前端取值太难了
    public Map<String,Object> finallConfig(){
        String key=null,value = null;//预定义两个变量
        Map<String,Object> result=new HashMap<>();//创建一个map
        //查询配置表中所有数据
        List<Map<String, Object>> maps = this.finallObjects(new HashMap<>());
        //遍历查询到的数据map
        for (Map<String, Object> map : maps) {
            //遍历map
            for (Map.Entry<String, Object> o : map.entrySet()) {
                //将键值获取出来
                String k= o.getKey(),v=  o.getValue().toString();
                if(k.equals("config_name")){//如果键为配置名称，则将其v赋值给key
                    key=v;
                }
                if(k.equals("config_value")){//如果键为配置值，则将其v赋值给value
                    value=v;
                }
            }
            result.put(key,value);//存入map中
        }
        return result;
    }

    //修改配置信息
    @Override
    public int updWebsite(Map<String, Object> map) {
        //判断传递来的config_name是否存在
        if(configMapper.finallByConfigName(map.get("config_name").toString())!=null){
            return configMapper.updWebsite(map);//存在则修改配置
        }
        return 0;
    }
}
