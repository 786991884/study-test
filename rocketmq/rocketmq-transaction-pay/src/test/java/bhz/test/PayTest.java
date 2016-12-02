package bhz.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import bhz.mq.MQProducer;
import bhz.util.FastJsonConvert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

/** 
 * <br>类 名: BaseTest 
 * <br>描 述: 描述类完成的主要功能 
 * <br>作 者: bhz
 * <br>创 建： 2013年5月8日 
 * <br>版 本：v1.0.0 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */

@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(rollbackFor = Exception.class)
public class PayTest {
	
	
	@Autowired
	private MQProducer mQProducer;
	
	@Autowired
	private LocalTransactionExecuter transactionExecuterImpl;
	
	@Test
	public void test1() {
		try {
			System.out.println(this.mQProducer);
			System.out.println(this.transactionExecuterImpl);
			
			//构造消息数据
			Message message = new Message();
			//主题
			message.setTopic("pay");
			//子标签
			message.setTags("tag");
			//key
			String uuid = UUID.randomUUID().toString();
			System.out.println("key: " + uuid);
			message.setKeys(uuid);
			JSONObject body = new JSONObject();
			body.put("userid", "z3");
			body.put("money", "1000");
			body.put("pay_mode", "OUT");
			body.put("balance_mode", "IN");
			message.setBody(FastJsonConvert.convertObjectToJSON(body).getBytes());
			
			//添加参数
			Map<String, Object> transactionMapArgs = new HashMap<String, Object>();
			
			this.mQProducer.sendTransactionMessage(message, this.transactionExecuterImpl, transactionMapArgs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test2() throws Exception {
		long end = new Date().getTime();
		long begin = end - 60 * 1000 * 60 * 24;
		QueryResult qr = this.mQProducer.queryMessage("pay", "6e4cefbb-5216-445f-9027-d96dd54a4afb", 10, begin, end);
		List<MessageExt> list = qr.getMessageList();
		for(MessageExt me : list){
			
			Map<String, String> m = me.getProperties();
			System.out.println(m.keySet().toString());
			System.out.println(m.values().toString());
			System.out.println(me.toString());
			System.err.println("内容: " + new String(me.getBody(), "utf-8"));
			System.out.println("Prepared :" + me.getPreparedTransactionOffset());
			LocalTransactionState ls = this.mQProducer.check(me);
			System.out.println(ls);
			//this.mQProducer.getTransactionCheckListener()
		}
		//System.out.println("qr: " + qr.toString());
		//C0A8016F00002A9F0000000000034842
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
