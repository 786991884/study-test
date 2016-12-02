package bhz.netty.ende3.handler;

import bhz.netty.ende3.PbHandlerContext;
import bhz.netty.ende3.conn.ConnectionEventHub;
import bhz.netty.ende3.conn.Key;
import bhz.netty.ende3.conn.NettyClientConnection;
import bhz.netty.ende3.conn.events.ClientConnectedEvent;
import bhz.netty.ende3.pakg.TAOPkg;
import bhz.netty.ende3.pakg.TARPkg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Sketch implementation of TAO package handler
 */
@Component
public class PbTAOHandler implements PbPackageHandler<TAOPkg> {

    private static final Logger LOG = LoggerFactory.getLogger(PbTAOHandler.class);

    public static final Key<String> KEY_AUTHENTICATION = new Key<>("opentsp.auth", String.class);
    private final ConnectionEventHub connectionEventHub;


    @Autowired
    public PbTAOHandler(ConnectionEventHub connectionEventHub) {
        this.connectionEventHub = connectionEventHub;
    }

    @Override
    public void handle(PbHandlerContext context, TAOPkg pkg) {
        String message = "ok";
        int code = 0;
        try {

            Map<String, Object> paramMap = pkg.getParamMap();
            /**
             1 sw_ver ◎ bstr tbox Firmware version
             2 hw_ver ◎ bstr tbox Hardware Version
             3 tboxid ◎ str tbox Global unique identification
             4 sim ◎ str tsim Card 11 phone number
             5 p_ver ◎ str Protocol version
             */
            final String tboxId = (String) paramMap.get("tboxid");
            Assert.hasLength(tboxId, "tboxid is null or empty");
            //save connection id
            NettyClientConnection connection = context.getConnection();
            connection.setDevice(tboxId);


            final String id = connection.getId();
            connection.getAttributes().put(KEY_AUTHENTICATION, "authentication");

            try {
                // send login event
                connectionEventHub.listen(ClientConnectedEvent.builder()
                        // we use 2 * 'connection timeout' as ttl for connection in push module
                        .ttl(connection.getChannel().config().getConnectTimeoutMillis() * 2)
                        .connectionId(id)
                        .build());
            } finally {
            }
        } catch (Exception e) {
            LOG.error("", e);
            message = e.toString();
            code = 1;
        }


        TARPkg tarPkg = new TARPkg();
        tarPkg.setType("TAOR");
        tarPkg.setCode(code);
        tarPkg.setMessage(message);
        tarPkg.setTime(new Date());
        HashMap<String, Object> resultMap = new HashMap<>();
        //1 re_apn str
        //2 re_apnid str
        //3 re_apnpwd str
        //4 re_ip str
        //5 re_port int
        //6 last_sw_ver bstr

        tarPkg.setResultMap(resultMap);
        context.sendResponse(tarPkg);
    }
}
