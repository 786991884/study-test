package bhz.bolt;

import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xubh
 * @date 2017-04-07
 * @modify
 * @copyright
 */
public class TickWordCountBolt extends BaseBasicBolt {
    Map<String, Integer> counts = new ConcurrentHashMap<String, Integer>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        if (tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID) && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)) {
            System.err.println("TickWordCount bolt: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
            //模拟聚合打印结果
            for (String key : counts.keySet()) {
                System.err.println("key: " + key + "   count: " + counts.get(key));
            }
            //模拟10秒钟的结果处理以后清空操作
            counts.clear();
        } else {
            String result = tuple.getStringByField("word");
            if (counts.get(result) == null) {
                counts.put(result, 1);
            } else {
                counts.put(result, counts.get(result) + 1);
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

    //设置10秒发送一次tick心跳
    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config conf = new Config();
        conf.put(conf.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 10);
        return conf;
    }
}
