package com.zou.zyqblogs.mapper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ScheduledMapper {
    List<Map<String,Object>> finAllImgDel();
}
