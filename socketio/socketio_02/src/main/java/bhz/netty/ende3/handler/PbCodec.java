package bhz.netty.ende3.handler;

import bhz.netty.ende3.pakg.AbstractPkg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * Codec which do basic parse and create CTIP messages
 */
public class PbCodec extends MessageToMessageCodec<String, AbstractPkg> {

    private final PbMapper mapper;

    public PbCodec(PbMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPkg msg, List<Object> out) throws Exception {
        String encoded = mapper.encode(msg);
        out.add(encoded);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        AbstractPkg decoded = mapper.decode(msg);
        out.add(decoded);
    }
}
