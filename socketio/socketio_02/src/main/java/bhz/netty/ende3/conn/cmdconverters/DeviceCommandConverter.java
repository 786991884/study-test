package bhz.netty.ende3.conn.cmdconverters;


import bhz.netty.ende3.pakg.AbstractPkg;

import java.util.Collection;

/**
 * Module which is convert UserDeviceCommand to concrete
 */
public interface DeviceCommandConverter {
    AbstractPkg convert(String command);

    Collection<String> getSupportedCommands();
}
