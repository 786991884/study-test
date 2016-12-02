package bhz.netty.ende3.conn;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

/**
 * Client connection represented by netty channel.
 */
public class NettyClientConnection implements ClientConnection {
    private final Channel channel;
    private final ConnectionIdGenerator idGenerator;
    private volatile String id;
    private volatile String device;
    private String authCode;
    private final ConnectionInfo connectionInfo;
    private final ConnectionAttributesImpl attributes = new ConnectionAttributesImpl();

    NettyClientConnection(Channel channel, ConnectionIdGenerator idGenerator, ConnectionInfo connectionInfo) {
        Assert.notNull(channel, "channel is null");
        Assert.notNull(connectionInfo, "connectionInfo is null");
        this.channel = channel;
        this.idGenerator = idGenerator;
        this.connectionInfo = connectionInfo;
    }

    @Override
    public String getId() {
        // we believe that simultaneous call of it don't cause invalid state
        String localId = this.id;
        if (localId == null) {
            id = localId = this.idGenerator.getId(this);
        }
        return localId;
    }

    @Override
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    @Override
    public ConnectionAttributes getAttributes() {
        return this.attributes;
    }

    @Override
    public ListenableFuture<Void> close() {
        ChannelFuture channelFuture = channel.close();
        return wrap(channelFuture, null);
    }

    @Override
    public <T> ListenableFuture<T> send(T data) {
        ChannelFuture future = channel.writeAndFlush(data);
        return wrap(future, data);
    }

    private <T> SettableListenableFuture<T> wrap(ChannelFuture channelFuture, final T value) {
        final SettableListenableFuture<T> listenableFuture = new SettableListenableFuture<>();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Throwable cause = future.cause();
                if (cause != null) {
                    listenableFuture.setException(cause);
                } else {
                    listenableFuture.set(value);
                }
            }
        });
        return listenableFuture;
    }

    /**
     * Get connection channel.
     *
     * @return
     */
    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "NettyClientConnection{" +
                "id='" + id + '\'' +
                ", connectionInfo=" + connectionInfo +
                '}';
    }
}
