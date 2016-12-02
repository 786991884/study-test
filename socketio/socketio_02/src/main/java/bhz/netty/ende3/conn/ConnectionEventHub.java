package bhz.netty.ende3.conn;

import bhz.netty.ende3.conn.events.ConnectionEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Hub for event from connections
 */
@Component
public class ConnectionEventHub implements ConnectionEventListener {

    private final Set<ConnectionEventListener> listeners = new CopyOnWriteArraySet<>();


    public void addConnectionEventListener(ConnectionEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void listen(ConnectionEvent event) {
        Assert.notNull(event, "Event is null");
        sendToListeners(event);
    }

    private void sendToListeners(ConnectionEvent event) {
        for (ConnectionEventListener listener : listeners) {
            listener.listen(event);
        }
    }
}
