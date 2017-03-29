package bhz.drpc1;


import org.apache.storm.utils.DRPCClient;

public class DrpcExclam {

    public static void main(String[] args) throws Exception {
        DRPCClient client = new DRPCClient(null, "192.168.1.114", 3772);
        for (String word : new String[]{"hello", "goodbye"}) {
            System.out.println(client.execute("exclamation", word));
        }
    }
}
