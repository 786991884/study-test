package bhz.netty.ende3;


import bhz.netty.ende3.conn.NettyClientConnection;
import bhz.netty.ende3.handler.PbPackageHandler;
import bhz.netty.ende3.pakg.AbstractPkg;
import bhz.netty.ende3.pakg.Callback;


/**
 * Context which passed to {@link PbPackageHandler}
 */
public final class PbHandlerContext {
    private final Callback<AbstractPkg> responseHandler;
    private final NettyClientConnection connection;

    public PbHandlerContext(NettyClientConnection connection, Callback<AbstractPkg> responseHandler) {
        this.connection = connection;
        this.responseHandler = responseHandler;
    }

    /**
     * Return callback which handle response packages.
     *
     * @return
     */
    public Callback<AbstractPkg> getResponseHandler() {
        return responseHandler;
    }

    /**
     * Send response package through {@link #getResponseHandler()}
     *
     * @param pkg
     */
    public void sendResponse(AbstractPkg pkg) {
        this.responseHandler.call(pkg);
    }

    /**
     * Return current connection. <p/>
     * Note that if connection is established, but client is not yet login then it have null id.
     *
     * @return
     */
    public NettyClientConnection getConnection() {
        return connection;
    }

}
