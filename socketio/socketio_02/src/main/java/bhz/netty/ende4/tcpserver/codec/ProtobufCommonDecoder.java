package bhz.netty.ende4.tcpserver.codec;

import com.google.protobuf.MessageLite;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class ProtobufCommonDecoder extends ProtobufDecoder {
    /**
     * Creates a new instance.
     *
     * @param prototype
     */
    public ProtobufCommonDecoder(MessageLite prototype) {
        super(prototype);
    }
}
