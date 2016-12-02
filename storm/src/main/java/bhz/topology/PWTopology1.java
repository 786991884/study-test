package bhz.topology;

import bhz.bolt.PrintBolt;
import bhz.bolt.WriteBolt;
import bhz.spout.PWSpout;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;


public class PWTopology1 {

    public static void main(String[] args) throws Exception {
        //
        Config cfg = new Config();
        cfg.setNumWorkers(2);
        cfg.setDebug(true);


        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new PWSpout());
        builder.setBolt("print-bolt", new PrintBolt()).shuffleGrouping("spout");
        builder.setBolt("write-bolt", new WriteBolt()).shuffleGrouping("print-bolt");


        //1 本地模式
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("top1", cfg, builder.createTopology());
//		Thread.sleep(10000);
//		cluster.killTopology("top1");
//		cluster.shutdown();

        //2 集群模式
        StormSubmitter.submitTopology("top1", cfg, builder.createTopology());

    }
}
