package bhz.topology;

import bhz.bolt.TickWordCountBolt;
import bhz.spout.TickWordSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @author xubh
 * @date 2017-04-07
 * @modify
 * @copyright
 */
public class TickTest {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new TickWordSpout());
        //启动3个线程按word值进行分组处理
        builder.setBolt("count", new TickWordCountBolt(), 3).fieldsGrouping("spout", new Fields("word"));
        Config conf = new Config();
        //设置一个全局的Topology发送tick心跳时间，测试优先级
        conf.put(conf.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 7);
        conf.setDebug(false);
        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
        }
    }
}