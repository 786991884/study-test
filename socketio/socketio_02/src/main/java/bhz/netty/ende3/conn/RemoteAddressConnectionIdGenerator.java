package bhz.netty.ende3.conn;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Simple Id Generator, it identity channels by its remote address.
 */
public final class RemoteAddressConnectionIdGenerator implements ConnectionIdGenerator {
    @Override
    public String getId(NettyClientConnection connection) {
        Channel channel = connection.getChannel();
        SocketAddress socketAddress = channel.remoteAddress();
        if(!(socketAddress instanceof InetSocketAddress)) {
            return null;
        }
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        return inetSocketAddress.getHostString() + ":" + inetSocketAddress.getPort();
    }
}
