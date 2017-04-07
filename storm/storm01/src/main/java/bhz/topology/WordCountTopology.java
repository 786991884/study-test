package bhz.topology;

import bhz.bolt.ReportBolt;
import bhz.bolt.SplitSentenceBolt;
import bhz.bolt.WordCountBolt;
import bhz.spout.SentenceSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * spot来喷发字符串，bolt1来以空格来分隔字符串继续向后续的计算模块bolt2分发,
 * bolt2来通过来收集相同字符出现次数继续向计算模块bolt3分发，
 * 然后bolt3收集blot2的结果最终打印结果手动结束
 *
 * @author xubh
 * @date 2017-04-06
 * @modify
 * @copyright
 */
public class WordCountTopology {
    private static final String SPOUT = "spout";
    private static final String SPLIT_BOLT = "splitBolt";
    private static final String COUNT_BOLT = "countBolt";
    private static final String REPORT_BOLT = "reportBolt";
    private static final String TOPOLOGY_NAME = "wordCountTopology";

    public static void main(String[] args) throws InterruptedException {
        //数据发射器
        SentenceSpout spout = new SentenceSpout();
        //字符串分隔计算模块
        SplitSentenceBolt splitBolt = new SplitSentenceBolt();
        //字符串统计模块
        WordCountBolt countBolt = new WordCountBolt();
        //最后打印模块
        ReportBolt reportBolt = new ReportBolt();
        //创建Topology
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(SPOUT, spout);
        builder.setBolt(SPLIT_BOLT, splitBolt).shuffleGrouping(SPOUT);
        builder.setBolt(COUNT_BOLT, countBolt).fieldsGrouping(SPLIT_BOLT, new Fields("word"));
        builder.setBolt(REPORT_BOLT, reportBolt).globalGrouping(COUNT_BOLT);
        Config config = new Config();
        //定义本地storm集群，如果放在linux虚拟机上跑略有不同
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        Thread.sleep(10000);
        //kill Topology,当Topology启动以后会一直执行直到kill Topology
        cluster.killTopology(TOPOLOGY_NAME);
        //关闭集群，这个方法跟redis的集群关闭一样
        cluster.shutdown();
    }
}
