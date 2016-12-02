package bhz.netty.ende3;

import bhz.netty.ende3.conn.NettyClientConnection;
import bhz.netty.ende3.conn.NettyClientConnections;
import bhz.netty.ende3.handler.PbPackageHandler;
import bhz.netty.ende3.pakg.AbstractPkg;
import bhz.netty.ende3.pakg.Callback;
import bhz.netty.ende3.pakg.PBUtils;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送Protobuf数据和接收client发送的数据。一个自定义的处理器，通常我们的业务会在这里处理。
 *
 * @author Lenovo
 * @date 2016-11-30
 * @modify
 * @copyright //ChannelInboundHandlerAdapter
 */
public class ProtobufServerHandler extends ChannelHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ProtobufServerHandler.class);

    private final NettyClientConnections connections;

    private final Map<String, PbPackageHandler<?>> map = new HashMap<>();


    public ProtobufServerHandler(NettyClientConnections connections, List<PbPackageHandler<?>> handlers) {
        this.connections = connections;
        for (PbPackageHandler<?> handler : handlers) {
            final String id = getPackageType(handler);
            PbPackageHandler<?> old = map.put(id, handler);
            if (old != null && old != handler) {
                throw new IllegalArgumentException(MessageFormat.format("Can not replace handler {2} with new {1} on ''{0}'' package type.", id, handler, old));
            }
        }
    }

    private String getPackageType(PbPackageHandler<?> handler) {
        Class<? extends PbPackageHandler> handlerClass = handler.getClass();
        // resolve first generic arg of PbPackageHandler
        Class<?> packageClass = ResolvableType.forClass(PbPackageHandler.class, handlerClass).getGeneric(0).resolve();
        return PBUtils.getId(packageClass);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PersonProtos.Person person = (PersonProtos.Person) msg;
        //经过pipeline的各个decoder，到此Person类型已经可以断定
        System.out.println(person.getEmail());
        ChannelFuture future = ctx.writeAndFlush(build());
        //发送数据之后，我们手动关闭channel，这个关闭是异步的，当数据发送完毕后执行。
        future.addListener(ChannelFutureListener.CLOSE);

        //改造成异步读写
        AbstractPkg pkg = (AbstractPkg) msg;
        PbPackageHandler<AbstractPkg> handler = (PbPackageHandler<AbstractPkg>) map.get(pkg.getType());
        if (handler == null) {
            LOG.warn("No registered handler for package type: {}", pkg.getType());
            return;
        }
        NettyClientConnection connection = this.connections.getConnection(ctx.channel());
        PbHandlerContext handlerContext = new PbHandlerContext(connection, new ResponsePackageCallback(ctx));
        handler.handle(handlerContext, pkg);
    }

    private final class ResponsePackageCallback implements Callback<AbstractPkg> {
        private final ChannelHandlerContext ctx;
        private final WriteListener writeListener;

        public ResponsePackageCallback(ChannelHandlerContext ctx) {
            this.ctx = ctx;
            this.writeListener = new WriteListener(ctx);
        }

        @Override
        public void call(AbstractPkg resp) {
            ctx.write(resp).addListener(writeListener);
            ctx.flush();
        }
    }

    private class WriteListener implements ChannelFutureListener {
        private final ChannelHandlerContext ctx;

        public WriteListener(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            Throwable cause = future.cause();
            if (cause != null) {
                exceptionCaught(ctx, cause);
            }
        }
    }


    /**
     * 构建一个Protobuf实例，测试
     *
     * @return
     */
    public MessageLite build() {
        PersonProtos.Person.Builder personBuilder = PersonProtos.Person.newBuilder();
        personBuilder.setEmail("zhangsan@gmail.com");
        personBuilder.setId(1000);
        PersonProtos.Person.PhoneNumber.Builder phone = PersonProtos.Person.PhoneNumber.newBuilder();
        phone.setNumber("18610000000");

        personBuilder.setName("张三");
        personBuilder.addPhone(phone);

        return personBuilder.build();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("Exception in channel", cause);
        ctx.close();
    }

}