package bhz.netty.ende3.conn;

import org.springframework.util.concurrent.ListenableFuture;

/**
 * Iface for connection between gateway and client.
 */
public interface ClientConnection {
    /**
     * Id of client connection. <p/>
     * It can be null, because some implementations may provide id only after authentication. <p/>
     * Note that this id must be unique for all system, therefore it must include 'module instance id' and its local connection id.
     * @return
     */
    String getId();

    /**
     * Id of connected device. In lifetime of connection it attribute can be changed from null to concrete device id (in
     * some protocols we can not known device id at first package).
     * @return
     */
    String getDevice();

    /**
     * Return connection info.
     * @return
     */
    ConnectionInfo getConnectionInfo();

    /**
     * Mutable container for different connection attributes.
     * @return
     */
    ConnectionAttributes getAttributes();

    /**
     * Close connection.
     * @return
     */
    ListenableFuture<Void> close();

    /**
     * Push generic data into connection. Data handled by protocol implementation, some implementations can support
     * usual java bean, other - not, so we can check supported messages by {@link ConnectionInfo#getSupportedMessages() }.
     * @param data
     * @return listenable future
     */
    <T> ListenableFuture<T> send(T data);
}
