package bhz.netty.ende3.conn;

/**
 * Invoked at connection close
 */
public interface ConnectionCloseCallback {
    void connectionClose(ClientConnection connection);
}
