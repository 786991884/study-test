package bhz.netty.ende3.conn.events;


/**
 * Heartbeat event. It sent from client with interval some less than {@link ClientConnectedEvent#getTtl()}.
 */
public class ClientHeartbeatEvent extends ConnectionEvent {

    public static class Builder extends ConnectionEvent.Builder<Builder, ClientHeartbeatEvent> {

        @Override
        public ClientHeartbeatEvent build() {
            return new ClientHeartbeatEvent(this);
        }
    }

    public ClientHeartbeatEvent(Builder b) {
        super(b);
    }

    public static Builder builder() {
        return new Builder();
    }
}
