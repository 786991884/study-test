package bhz.netty.ende3.conn.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;

/**
 * Event caused on client connection.
 */
public class ClientConnectedEvent extends ConnectionEvent {

    public static class Builder extends ConnectionEvent.Builder<Builder, ClientConnectedEvent> {

        private long ttl;

        /**
         * Time (in ms) to connection is alive, after last event (except disconnected event).
         *
         * @return
         */
        public long getTtl() {
            return ttl;
        }

        /**
         * Time (in ms) to connection is alive, after last event (except disconnected event).
         *
         * @param ttl
         * @return
         */
        public Builder ttl(long ttl) {
            setTtl(ttl);
            return this;
        }

        /**
         * Time (in ms) to connection is alive, after last event (except disconnected event).
         *
         * @param ttl
         */
        public void setTtl(long ttl) {
            this.ttl = ttl;
        }

        @Override
        public ClientConnectedEvent build() {
            return new ClientConnectedEvent(this);
        }
    }

    private final long ttl;

    @JsonCreator
    public ClientConnectedEvent(Builder b) {
        super(b);
        this.ttl = b.ttl;
    }

    public long getTtl() {
        return ttl;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    protected void toString(MoreObjects.ToStringHelper helper) {
        super.toString(helper);
        helper.add("ttl", ttl);
    }
}
