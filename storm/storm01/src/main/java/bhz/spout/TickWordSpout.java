package bhz.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * storm_定时机制tick使用
 *
 * @author xubh
 * @date 2017-04-07
 * @modify
 * @copyright
 */
public class TickWordSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private String[] sentences = {"a", "b", "c"};
    private int index = 0;

    public void nextTuple() {
        this.collector.emit(new Values(sentences[index]));
        index++;
        if (index >= sentences.length) {
            index = 0;
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
    }

    public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}