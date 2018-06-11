/** 
 * Project Name:crm_dubbox_provider 
 * File Name:RedisClusterUtil.java 
 * Package Name:com.cdel.util 
 * Date:2016年5月27日上午10:45:48 
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved. 
 * 
*/  
  
package com.xly.utils;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/** 
 * ClassName:RedisClusterUtil <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016年5月27日 上午10:45:48 <br/> 
 * @author   dell 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class RedisCluster
{
    private static JedisCluster jedisCluster = null;

    public JedisCluster getJedisCluster()
    {   // Jedis连接池配置
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数, 默认8个
        config.setMaxTotal(1000);
        // 最大空闲连接数, 默认8个
        config.setMaxIdle(1000);
        // 最小空闲连接数, 默认0
        config.setMinIdle(100);
        // 获取连接时的最大等待毫秒数,  默认-1
        config.setMaxWaitMillis(6 * 1000);
        // 对拿到的connection进行validateObject校验
        config.setTestOnBorrow(true);

        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("10.42.50.115", 6379));
        nodes.add(new HostAndPort("10.42.50.117", 6379));
        nodes.add(new HostAndPort("10.42.50.118", 6379));
        nodes.add(new HostAndPort("10.42.50.115", 7379));
        nodes.add(new HostAndPort("10.42.50.117", 7379));
        nodes.add(new HostAndPort("10.42.50.118", 7379));


        jedisCluster = new redis.clients.jedis.JedisCluster(nodes,config);
        return jedisCluster;
    }

    private RedisCluster() {
    }
    private static RedisCluster instance = new RedisCluster();
    public static RedisCluster getInstance(){
        return instance;
    }
    public static void main(String[] args){

    }

}
