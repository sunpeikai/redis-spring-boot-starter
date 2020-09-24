/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.redis.utils;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunpeikai
 * @version RedisInfoUtils, v0.1 2020/9/24 17:48
 * @description
 */
public class RedisInfoUtils {

    private static final List<String> INFO_TAG = Arrays.asList("Server","Clients","Memory","Persistence"
            ,"Stats","Replication","CPU","Cluster","Keyspace");

    private static final List<String> INFO = Arrays.asList("redis_version","process_id","uptime_in_seconds"
            ,"uptime_in_days","lru_clock","connected_clients","used_memory_human","used_memory_peak_human"
            ,"used_memory_rss","used_memory_lua","mem_fragmentation_ratio","total_system_memory_human"
            ,"used_cpu_sys","used_cpu_user");

    public static Map<String,String> systemInfo(){
        Map<String, String> result = new HashMap<>();
        // 获取redis信息
        String infoStr = RedisUtils.info();
        // 把section头部去掉
        for (String tag : INFO_TAG) {
            infoStr = infoStr.replaceAll("# " + tag, "");
        }
        // 换行分组
        String[] infos = infoStr.split("\n");
        // 处理成MAP
        for (String info : infos) {
            info = info.trim();
            if(!StringUtils.isEmpty(info)){
                String[] keyValue = info.split(":");
                // 筛选出需要的信息
                if(INFO.contains(keyValue[0])){
                    result.put(keyValue[0],keyValue[1]);
                }
            }
        }
        return result;
    }
}
