package bhz.netty.ende3.conn;


import bhz.netty.ende3.conn.events.ConnectionEvent;

/**
 * Entry point for client events.
 */
public interface ConnectionEventListener {

    /**
     * Accept connection events.
     *
     * @param event
     */
    void listen(ConnectionEvent event);
}
