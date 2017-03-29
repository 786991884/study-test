package bhz.redis.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TestRedis {

    private static Jedis jedis = new Jedis("192.168.1.101", 6379);

    /**
     * @param
     */
    @Test
    public void test1() {
//		jedis.set("sex","man");
//		System.out.println(jedis);
//		
//		List<String> list = jedis.mget("name","age","sex");
//        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			String string = (String) iterator.next();
//			System.out.println(string);
//		}
//        
//        Map<String, String> user = new HashMap<String, String>();
//        user.put("name","huangyuxuan");
//        user.put("age", "0.5");
//        user.put("sex","男");
//        jedis.hmset("user",user);

        //
//        List<String> rsmap = jedis.hmget("user", "name", "age","sex");
//        System.out.println(rsmap);  
        //
//        jedis.hdel("user","age");
//        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null  
//        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2 
//        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true  
//        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key  
//        System.out.println(jedis.hvals("user"));//返回map对象中的所有value 
        //
        //testStr();
        //testList();
        testSet();


//        Iterator<String> iter=jedis.hkeys("user").iterator();  
//        while (iter.hasNext()){  
//            String key = iter.next();  
//            System.out.println(key+":"+jedis.hmget("user",key));  
//        }  
    }

    public static void testStr() {
        //-----添加数据----------
        jedis.set("name", "bhz");//向key-->name中放入了value-->xinxin
        System.out.println(jedis.get("name"));//执行结果：xinxin  

        jedis.append("name", " is my lover"); //拼接
        System.out.println(jedis.get("name"));

        jedis.del("name");  //删除某个键
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name", "bhz", "age", "27", "qq", "174754613");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

    }

    public static void testList() {
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        //先向key java framework中存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));

        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }


    public static void testSet() {
        //添加
        jedis.sadd("user1", "liuling");
        jedis.sadd("user1", "xinxin");
        jedis.sadd("user1", "ling");
        jedis.sadd("user1", "zhangxinxin");
        jedis.sadd("user1", "who");
        //移除noname
        jedis.srem("user1", "who");
        System.out.println(jedis.smembers("user1"));//获取所有加入的value
        System.out.println(jedis.sismember("user1", "who"));//判断 who 是否是user集合的元素
        System.out.println(jedis.srandmember("user1"));
        System.out.println(jedis.scard("user1"));//返回集合的元素个数
    }

    @Test
    public void test() {
        Jedis jedis = new Jedis("1.1.1.2", 6379);
//        List<String> list = jedis.mget("name", "age");
//        list.forEach(System.out::println);
        final String SYS_USER_TAB = "SYS_USER_TAB";
        final String SYS_USER_TAB_SEL_AGE_25 = "SYS_USER_TAB_SEL_AGE_25";
        final String SYS_USER_TAB_SEL_SEX_m = "SYS_USER_TAB_SEL_SEX_m";
        final String SYS_USER_TAB_SEL_SEX_w = "SYS_USER_TAB_SEL_SEX_w";

        Set<String> userAges = jedis.smembers(SYS_USER_TAB_SEL_AGE_25);

        for (String id : userAges) {
            String result = jedis.hget(SYS_USER_TAB, id);
            System.out.println(result);

        }
        String u1id = UUID.randomUUID().toString();
        User u1 = new User(u1id, "z1", "20", "w");
        jedis.sadd(SYS_USER_TAB_SEL_SEX_w, u1id);


        String u2id = UUID.randomUUID().toString();
        User u2 = new User(u2id, "z2", "25", "m");
        jedis.sadd(SYS_USER_TAB_SEL_SEX_m, u2id);
        jedis.sadd(SYS_USER_TAB_SEL_AGE_25, u2id);


        String u3id = UUID.randomUUID().toString();
        User u3 = new User(u3id, "z3", "20", "m");
        jedis.sadd(SYS_USER_TAB_SEL_SEX_m, u3id);


        String u4id = UUID.randomUUID().toString();
        User u4 = new User(u4id, "z4", "21", "m");
        jedis.sadd(SYS_USER_TAB_SEL_SEX_m, u4id);


        String u5id = UUID.randomUUID().toString();
        User u5 = new User(u5id, "z5", "29", "w");
        jedis.sadd(SYS_USER_TAB_SEL_SEX_w, u5id);
        jedis.sadd(SYS_USER_TAB_SEL_AGE_25, u5id);


        Map<String, String> map = new HashMap<>();
        map.put(u1id, JSON.toJSONString(u1));
        map.put(u1id, JSON.toJSONString(u2));
        map.put(u3id, JSON.toJSONString(u3));
        map.put(u4id, JSON.toJSONString(u4));
        map.put(u5id, JSON.toJSONString(u5));

        jedis.hmset("SYS_USER_TAB", map);


    }
}
