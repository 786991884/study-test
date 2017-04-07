package bhz.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.*;

/**
 * 统计单词结果
 *
 * @author xubh
 * @date 2017-04-06
 * @modify
 * @copyright
 */
public class ReportBolt extends BaseRichBolt {
    private HashMap<String, Long> counts = null;

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        Long count = tuple.getLongByField("count");
        this.counts.put(word, count);
    }

    @Override
    public void prepare(Map config, TopologyContext context, OutputCollector collector) {
        counts = new HashMap<>();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
    }

    public void cleanup() {
        System.err.println("---final counts---");
        List<String> keys = new ArrayList<>();
        keys.addAll(counts.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            System.err.println(key + " : " + this.counts.get(key));
        }
        System.err.println("---end---");
    }
}
