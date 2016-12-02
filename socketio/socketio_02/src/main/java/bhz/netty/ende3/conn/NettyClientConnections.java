package bhz.netty.ende3.conn;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.net.SocketAddress;
import java.util.Collection;

/**
 */
public class NettyClientConnections implements ClientsConnections {

    public static class Builder {
        private String name;
        private ConnectionInfo connectionInfo;
        private ConnectionIdGenerator idGenerator;
        private ConnectionCloseCallback closeCallback;

        /**
         * string which unique identify protocol
         *
         * @return
         */
        public String getName() {
            return name;
        }

        public Builder name(String name) {
            setName(name);
            return this;
        }

        /**
         * string which unique identify protocol
         *
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Connection info.
         *
         * @return
         */
        public ConnectionInfo getConnectionInfo() {
            return connectionInfo;
        }

        public Builder connectionInfo(ConnectionInfo connectionInfo) {
            setConnectionInfo(connectionInfo);
            return this;
        }

        /**
         * Connection info.
         *
         * @param connectionInfo
         */
        public void setConnectionInfo(ConnectionInfo connectionInfo) {
            this.connectionInfo = connectionInfo;
        }

        /**
         * channel id generator
         *
         * @return
         */
        public ConnectionIdGenerator getIdGenerator() {
            return idGenerator;
        }

        public Builder idGenerator(ConnectionIdGenerator idGenerator) {
            setIdGenerator(idGenerator);
            return this;
        }

        /**
         * channel id generator
         *
         * @param idGenerator
         */
        public void setIdGenerator(ConnectionIdGenerator idGenerator) {
            this.idGenerator = idGenerator;
        }

        /**
         * Invoked at connection close, it may used for listen channel close events
         *
         * @return
         */
        public ConnectionCloseCallback getCloseCallback() {
            return closeCallback;
        }

        /**
         * Invoked at connection close, it may used for listen channel close events
         *
         * @param closeCallback
         * @return
         */
        public Builder closeCallback(ConnectionCloseCallback closeCallback) {
            setCloseCallback(closeCallback);
            return this;
        }

        /**
         * Invoked at connection close, it may used for listen channel close events
         *
         * @param closeCallback
         */
        public void setCloseCallback(ConnectionCloseCallback closeCallback) {
            this.closeCallback = closeCallback;
        }

        public NettyClientConnections build() {
            return new NettyClientConnections(this);
        }
    }

    /**
     * Name of client connection attribute.
     */
    private static final AttributeKey<NettyClientConnection> CONNECTION_KEY = AttributeKey.valueOf(NettyClientConnection.class.getName());
    private final ChannelGroup channels;
    private final ChannelHandler channelHandler = new ClientConnectionsChannelHandler();

    private final String name;
    private final ConnectionInfo connectionInfo;
    private final ConnectionIdGenerator idGenerator;
    private final ConnectionCloseCallback closeCallback;

    /**
     * Create netty adapter for {@link ClientsConnections } iface.
     */
    private NettyClientConnections(Builder builder) {
        this.name = builder.getName();
        channels = new DefaultChannelGroup(name + "-clients", GlobalEventExecutor.INSTANCE);
        this.connectionInfo = builder.getConnectionInfo();
        this.idGenerator = builder.getIdGenerator();
        this.closeCallback = builder.closeCallback;
        Assert.hasLength(this.name, "name is null or empty");
        Assert.notNull(this.connectionInfo, "connectionInfo is null");
        Assert.notNull(this.idGenerator, "idGenerator is null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public ChannelHandler getChannelHandler() {
        return channelHandler;
    }

    @Override
    public void getConnections(Collection<ClientConnection> connections) {
        for (Channel channel : channels) {
            connections.add(getConnection(channel));
        }
    }

    @Override
    public void getConnectionsIds(Collection<String> ids) {
        for (Channel channel : channels) {
            NettyClientConnection connection = getConnection(channel);
            ids.add(connection.getId());
        }
    }

    @Override
    public ClientConnection getConnection(String id) {
        for (Channel channel : channels) {
            ClientConnection connection = getConnection(channel);
            String cid = connection.getId();
            if (cid.equals(id)) {
                return connection;
            }
        }
        return null;
    }

    @Override
    public ClientConnection getConnectionByDevice(String device) {
        for (Channel channel : channels) {
            ClientConnection connection = getConnection(channel);
            String cid = connection.getDevice();
            if (cid != null && cid.equals(device)) {
                return connection;
            }
        }
        return null;
    }

    private NettyClientConnection createConnectionWrapper(Channel channel) {
        return new NettyClientConnection(channel, this.idGenerator, connectionInfo);
    }

    private void registerNewChannel(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        NettyClientConnection connection = createConnectionWrapper(channel);
        channel.attr(CONNECTION_KEY).set(connection);
        channels.add(channel);
    }

    /**
     * Return connection associated with specified channel.
     *
     * @param channel
     * @return
     */
    public NettyClientConnection getConnection(Channel channel) {
        // we do not make this method static for possible future extension
        return channel.attr(CONNECTION_KEY).get();
    }

    private final class ClientConnectionsChannelHandler implements ChannelHandler {

        private Logger log = LoggerFactory.getLogger(ClientConnectionsChannelHandler.class);

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            registerNewChannel(ctx);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            if (closeCallback != null) {
                log.info("exec closeCallback.connectionClose,{}", getConnection(ctx.channel()));
                closeCallback.connectionClose(getConnection(ctx.channel()));
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.info("exec closeCallback.connectionClose,{}", getConnection(ctx.channel()));
            ctx.fireExceptionCaught(cause);
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {

        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {

        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

        }

        @Override
        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

        }

        @Override
        public void read(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {

        }
    }

}
