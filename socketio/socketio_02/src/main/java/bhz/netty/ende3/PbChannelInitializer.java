package bhz.netty.ende3;

import bhz.netty.ende3.conn.NettyClientConnections;
import bhz.netty.ende3.handler.PbCodec;
import bhz.netty.ende3.handler.PbMapper;
import bhz.netty.ende3.handler.PbOutboundHandler;
import bhz.netty.ende3.handler.PbPackageHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Initializer for channel which support CTIP
 */
@Component
public class PbChannelInitializer extends ChannelInitializer<Channel> {

    private final List<PbPackageHandler<?>> handlers;
    private final NettyClientConnections connections;
    private final PbMapper mapper;
    private final PbOutboundHandler outboundHandler;

    @Autowired
    public PbChannelInitializer(@Qualifier("ctipConnections") NettyClientConnections connections,
                                PbMapper ctipMapper,
                                PbOutboundHandler outboundHandler,
                                List<PbPackageHandler<?>> handlers) {
        this.connections = connections;
        this.mapper = ctipMapper;
        this.handlers = handlers;
        this.outboundHandler = outboundHandler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                // register handler which count and memorize opened channels
                this.connections.getChannelHandler(),
                new StringDecoder(StandardCharsets.US_ASCII),
                new StringEncoder(StandardCharsets.US_ASCII),
                new PbCodec(mapper),
                outboundHandler,
                new ProtobufServerHandler(this.connections, handlers)
        );
    }
}
