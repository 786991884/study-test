package bhz.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * SentenceSpout--单词生成类
 *
 * @author xubh
 * @date 2017-04-06
 * @modify
 * @copyright
 */
public class SentenceSpout extends BaseRichSpout {
    //用来发射数据的工具类
    private SpoutOutputCollector collector;
    private String[] sentences = {"my dog has fleas", "i like cold beverages", "the dog ate my homework", "don't have a cow man", "i don't think i like fleas"};
    private int index = 0;

    //每调用一次就可以向storm集群中发射一条数据（一个tuple元组），该方法会被不停的调用
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

    //初始化collector
    public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    //  定义字段id，该id在简单模式下没有用处，但在按照字段分组的模式下有很大的用处。
    // 该declarer变量有很大作用，我们还可以调用declarer.declareStream();来定义stramId，该id可以用来定义更加复杂的流拓扑结构
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }
}