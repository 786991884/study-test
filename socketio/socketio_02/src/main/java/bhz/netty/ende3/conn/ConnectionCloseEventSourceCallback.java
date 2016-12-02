package bhz.netty.ende3.conn;

import bhz.netty.ende3.conn.events.ClientDisconnectedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class ConnectionCloseEventSourceCallback implements ConnectionCloseCallback {

    private ConnectionEventHub eventHub;

    @Autowired
    public ConnectionCloseEventSourceCallback(ConnectionEventHub eventHub) {
        this.eventHub = eventHub;
    }

    @Override
    public void connectionClose(ClientConnection connection) {
        try {
            eventHub.listen(ClientDisconnectedEvent.builder()
                    //TODO right cause we do not known it in this context
                    .cause(ClientDisconnectedEvent.Cause.SERVER_CLOSE)
                    .connectionId(connection.getId())
                    .build());
        } finally {
        }
    }
}
