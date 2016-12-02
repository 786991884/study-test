package bhz.netty.ende3.handler;

import bhz.netty.ende3.conn.cmdconverters.DeviceCommandConverter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Adapter which handle outgoing messages and convert its into protocol packages.
 * ChannelOutboundHandlerAdapter
 */
@ChannelHandler.Sharable
@Component
public class PbOutboundHandler extends ChannelHandlerAdapter {

    private final Map<String, DeviceCommandConverter> converters;

    @Autowired
    public PbOutboundHandler(Collection<DeviceCommandConverter> converters) {
        this.converters = new HashMap<>();
        for (DeviceCommandConverter converter : converters) {
            for (String cmd : converter.getSupportedCommands()) {
                this.converters.put(cmd, converter);
            }
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Assert.notNull(msg, "msg is null");
        if ((msg instanceof String)) {
            String command = (String) msg;
            String cmd = command;
            DeviceCommandConverter converter = converters.get(cmd);
            if (converter == null) {
                throw new RuntimeException("Unsupported command: " + cmd);
            }
            msg = converter.convert(command);
        }
        super.write(ctx, msg, promise);
    }
}
