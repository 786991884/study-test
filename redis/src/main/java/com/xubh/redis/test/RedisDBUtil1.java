package com.xubh.redis.test;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Set;


import redis.clients.jedis.JedisPoolConfig;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * 满足集群分片
 */
public class RedisDBUtil1 extends RedisSource{

	 
	private static ShardedJedisPool pool;
	static{
		init();
	}
	private static void init() {
		 
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(max_active));
		config.setMaxIdle(Integer.valueOf(max_idle));
		config.setMaxWaitMillis(Integer.valueOf(max_wait));
		config.setTestOnBorrow(Boolean.valueOf(isTest));
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		String[] addressArr = redis_address.split(",");
		for (String str : addressArr) {
			JedisShardInfo shardInfo = new JedisShardInfo(str.split(":")[0], Integer.parseInt(str.split(":")[1]),str.split(":")[2]);
			shardInfo.setSoTimeout(Integer.valueOf(timeout));
			list.add(shardInfo);
		}
		pool = new ShardedJedisPool(config, list);
		 
	}

	public static ShardedJedis getRedisTemplate() {
		ShardedJedis shardedJedis = pool.getResource();
		
		
		return shardedJedis;
	}
	
	public static void setValue(byte[] key, byte[] value) {
		ShardedJedis jedis = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			jedis.set(key, value);
			//jedis.lp
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
	}
	
	public static void setValue(String key, String value) {
		ShardedJedis jedis = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			jedis.set(key, value);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
	}
	
	public static void setExpireValue(byte[] key, byte[] value, int second){
		ShardedJedis jedis = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			jedis.set(key, value);
			jedis.expire(key, second);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
	}
	
	public static void setExpireValue(String key, String value, int second){
		ShardedJedis jedis = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			jedis.set(key, value);
			jedis.expire(key, second);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
	}
	
	
	public static void setHashValue(String mapName, String key, String value){
		ShardedJedis jedis = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			jedis.hset(mapName, key, value);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
	}
	
	public static void setHashValue(byte[] mapName, byte[] key, byte[] value){
		ShardedJedis jedis = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			jedis.hset(mapName, key, value);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
	}
	
	public static void zadd(String key, Map<String, Double> scoreMembers){
		ShardedJedis jedis = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			jedis.zadd(key, scoreMembers);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
	}
	
	public static Set<String> zrangeByscore(String key, String value){
		ShardedJedis jedis = null;
		Set<String> result = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			result = jedis.zrangeByScore(key, value, "+inf",0,1);
			RedisDBUtil1.closeJedis(jedis);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return result;
	}
	
	public static byte[] getValue(byte[] key) {
		ShardedJedis jedis = null;
		byte[] re = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.get(key);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static String getValue(String key) {
		ShardedJedis jedis = null;
		String re = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.get(key);	
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static String getHashValue(String mapName, String key){
		ShardedJedis jedis = null;
		String re = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.hget(mapName, key);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static byte[] getHashValue(byte[] mapName, byte[] key){
		ShardedJedis jedis = null;
		byte[] re = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.hget(mapName, key);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static Map<String, String> getAllKeys(String mapName){
		ShardedJedis jedis = null;
		Map<String, String> re = null;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.hgetAll(mapName);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static long incr(String key){
		ShardedJedis jedis = null;
		long re = 0;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.incr(key);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static long setHashHincr(String key,String field , long value){
		ShardedJedis jedis = null;
		long re = 0;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.hincrBy(key, field, value);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static long del(String key){
		ShardedJedis jedis = null;
		long re = 0;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.del(key);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	public static long zrem(String key , String... members){
		ShardedJedis jedis = null;
		long re = 0;
		try {
			jedis = RedisDBUtil1.getRedisTemplate();
			re = jedis.zrem(key, members);
			RedisDBUtil1.closeJedis(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			RedisDBUtil1.closeBreakJedis(jedis);
		}
		return re;
	}
	
	
	/**
	 * 正常连接池回收
	 * @param jedis
	 */
	public static void closeJedis(ShardedJedis jedis){
		   if(jedis!=null){
			   pool.returnResource(jedis);
		   }
	}
	
	/**
	 * 异常连接池回收
	 * @param jedis
	 */
	public static void closeBreakJedis(ShardedJedis jedis){
		if(jedis!=null){
			pool.returnBrokenResource(jedis);
		}
	}
}
