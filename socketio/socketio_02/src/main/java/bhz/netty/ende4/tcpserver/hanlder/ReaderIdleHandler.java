package bhz.netty.ende4.tcpserver.hanlder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReaderIdleHandler extends ReadTimeoutHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(ReaderIdleHandler.class);

    public ReaderIdleHandler(int timeoutSeconds) {
        super(timeoutSeconds);
    }

    protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("read over time");
        super.readTimedOut(ctx);
    }
}
