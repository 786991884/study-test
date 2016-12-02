package bhz.netty.ende3.conn.events;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Event caused on client disconnection. <p/>
 */
public class ClientDisconnectedEvent extends ConnectionEvent {

    /**
     * Disconnection cause
     */
    public enum Cause {
        TIMEOUT, CLIENT_CLOSE, SERVER_CLOSE
    }

    public static class Builder extends ConnectionEvent.Builder<Builder, ClientDisconnectedEvent> {

        private Cause cause;

        /**
         * Disconnection cause
         *
         * @return
         */
        public Cause getCause() {
            return cause;
        }

        /**
         * Disconnection cause
         *
         * @param cause
         * @return
         */
        public Builder cause(Cause cause) {
            setCause(cause);
            return thiz();
        }

        /**
         * Disconnection cause
         *
         * @param cause
         */
        public void setCause(Cause cause) {
            this.cause = cause;
        }

        @Override
        public ClientDisconnectedEvent build() {
            return new ClientDisconnectedEvent(this);
        }
    }

    private final Cause cause;

    @JsonCreator
    public ClientDisconnectedEvent(Builder b) {
        super(b);
        this.cause = b.cause;
    }

    /**
     * Disconnection cause
     *
     * @return
     */
    public Cause getCause() {
        return cause;
    }

    public static Builder builder() {
        return new Builder();
    }
}
