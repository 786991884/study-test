package bhz.topology;

import bhz.bolt.SpliterBolt;
import bhz.bolt.WriterBolt;
import bhz.spout.MessageSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class MessageTopology {

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new MessageSpout());
        builder.setBolt("split-bolt", new SpliterBolt()).shuffleGrouping("spout");
        builder.setBolt("write-bolt", new WriterBolt()).shuffleGrouping("split-bolt");
        //本地配置
        Config config = new Config();
        config.setDebug(false);
        LocalCluster cluster = new LocalCluster();
        System.out.println(cluster);
        cluster.submitTopology("message", config, builder.createTopology());
        Thread.sleep(10000);
        cluster.killTopology("message");
        cluster.shutdown();
    }
}
