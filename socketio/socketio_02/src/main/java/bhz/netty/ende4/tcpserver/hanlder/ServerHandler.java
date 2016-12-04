package bhz.netty.ende4.tcpserver.hanlder;

import bhz.netty.ende4.proto.chat.ClientServerMsg;
import bhz.netty.ende4.tcpserver.constant.MsgTypeConstant;
import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ChannelHandler.Sharable
public class ServerHandler extends ChannelHandlerAdapter {
    private static Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    private ConcurrentHashMap<String, Channel> userChannels = new ConcurrentHashMap<String, Channel>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>>>> I'm server.");
////        ctx.write("Hello world!");
////        ctx.flush();
//        String msg = "Are you ok?";
//        ByteBuf encoded = ctx.alloc().buffer(msg.length());
//        encoded.writeBytes(msg.getBytes());
//        ctx.write(encoded);
//        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ClientServerMsg.Req) {
            ClientServerMsg.Req req = (ClientServerMsg.Req) msg;

            if (req.getType().equals(MsgTypeConstant.LOGIN)) {
                String sessionId = req.getContent().toStringUtf8();
                userChannels.put(sessionId, ctx.channel());
                ClientServerMsg.Rsp.Builder rsp = ClientServerMsg.Rsp.newBuilder();
                rsp.setType(MsgTypeConstant.LOGIN);
                HashMap map = new HashMap();
                map.put("name", "zhangsan");
                map.put("city", "北京");
                rsp.setContent(ByteString.copyFrom(JSON.toJSONString(map).getBytes()));

                ctx.channel().writeAndFlush(rsp.build());
            } else if (req.getType().equals(MsgTypeConstant.SEND_TEXT_MSG)) {
                System.out.println(String.format("type:%s not support.", req.getType()));

            }

        } else {
            System.out.println("receive other msg." + msg);
        }

    }
}
