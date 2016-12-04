package bhz.netty.ende4.tcpserver.hanlder;

import bhz.netty.ende4.proto.chat.ClientServerMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    @Qualifier("serverHandler")
    private ChannelHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //增加编解码
        ch.pipeline()
                .addLast(new LoggingHandler(LogLevel.INFO))
//                .addLast("frameDecoder", new ProtobufVarint32FrameDecoder())
                .addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 4))
                .addLast("pbDecoder", new ProtobufDecoder(ClientServerMsg.Req.getDefaultInstance()))
//                .addLast("frameEncode", new ProtobufVarint32LengthFieldPrepender())
                .addLast("frameEncode", new LengthFieldPrepender(4))
                .addLast("protobufEncode", new ProtobufEncoder())
                .addLast(new ReaderIdleHandler(60))
                .addLast(serverHandler);
    }
}
