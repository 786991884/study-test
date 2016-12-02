package bhz.netty.ende3.conn;

import java.util.Set;

/**
 * Connection info.
 */
public interface ConnectionInfo {

    /**
     * Set of the most basic types supported by underlying connection protocol.
     * @see
     * @return
     */
    Set<Class<?>> getSupportedMessages();
}
