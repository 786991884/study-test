package bhz.storm.redis.example;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class StormRedisBolt implements IBasicBolt {

    private static final long serialVersionUID = 2L;
    private RedisOperations redisOperations = null;
    private String redisIP = null;
    private int port;

    public StormRedisBolt(String redisIP, int port) {
        this.redisIP = redisIP;
        this.port = port;
    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        Map<String, Object> record = new HashMap<String, Object>();
        //"firstName","lastName","companyName")
        record.put("firstName", input.getValueByField("firstName"));
        record.put("lastName", input.getValueByField("lastName"));
        record.put("companyName", input.getValueByField("companyName"));
        redisOperations.insert(record, UUID.randomUUID().toString());
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    public void prepare(Map stormConf, TopologyContext context) {
        redisOperations = new RedisOperations(this.redisIP, this.port);
    }

    public void cleanup() {

    }

}

