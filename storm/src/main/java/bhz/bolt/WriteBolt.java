package bhz.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;

public class WriteBolt extends BaseBasicBolt {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(WriteBolt.class);

    private FileWriter writer;

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        //获取上一个组件所声明的Field
        String text = input.getStringByField("write");
        try {
            if (writer == null) {
                if (System.getProperty("os.name").equals("Windows 10")) {
                    writer = new FileWriter("D:\\099_test\\" + this);
                } else if (System.getProperty("os.name").equals("Windows 8.1")) {
                    writer = new FileWriter("D:\\099_test\\" + this);
                } else if (System.getProperty("os.name").equals("Windows 7")) {
                    writer = new FileWriter("D:\\099_test\\" + this);
                } else if (System.getProperty("os.name").equals("Linux")) {
                    System.out.println("----:" + System.getProperty("os.name"));
                    writer = new FileWriter("/usr/local/temp/" + this);
                }
            }
            log.info("【write】： 写入文件");
            writer.write(text);
            writer.write("\n");
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }


}
