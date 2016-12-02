package bhz.netty.ende3.conn;

/**
 * Interface for connection id generator. <p/>
 * <b>Note that id generator can simultaneous invoked on the same argument and in this case it must produce identity result.</b>
 */
public interface ConnectionIdGenerator {
    /**
     * The id must be an unique per cluster. Also channel can not have different id-s. <p/>
     * <b>Note that id generator can simultaneous invoked on the same argument and in this case it must produce identity result.</b>
     * @param connection
     * @return id or null
     */
    String getId(NettyClientConnection connection);
}
