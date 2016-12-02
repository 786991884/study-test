package bhz.netty.ende3.handler;

/**
 * @author Lenovo
 * @date 2016-12-01
 * @modify
 * @copyright
 */

import bhz.netty.ende3.PbHandlerContext;
import bhz.netty.ende3.pakg.AbstractPkg;

/**
 * Iface for per package processors.
 */
public interface PbPackageHandler<T extends AbstractPkg> {
    /**
     * Invoked on each new package.
     *
     * @param context
     * @param pkg
     */
    void handle(PbHandlerContext context, T pkg);
}