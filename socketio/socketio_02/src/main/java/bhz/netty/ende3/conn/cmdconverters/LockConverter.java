package bhz.netty.ende3.conn.cmdconverters;

import bhz.netty.ende3.pakg.AbstractPkg;
import bhz.netty.ende3.pakg.TCTCPkg;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Converter for unlock command
 */
@Component
public class LockConverter implements DeviceCommandConverter {

    private static final Collection<String> CMDS = Collections.singleton("door.lock");

    @Override
    public Collection<String> getSupportedCommands() {
        return CMDS;
    }

    @Override
    public AbstractPkg convert(String command) {
        TCTCPkg pkg = new TCTCPkg();
        pkg.setId(command);
        pkg.setSequence(0);
        pkg.setTime(new Date());
        pkg.setCategoryId("VC");
        pkg.setAction("LOCK");
        Map parmMap = new HashMap<String, Object>();
        parmMap.put("v1", 1000);
        pkg.setParamMap(parmMap);
        return pkg;
    }
}
