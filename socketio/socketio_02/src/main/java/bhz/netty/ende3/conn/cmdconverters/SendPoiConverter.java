package bhz.netty.ende3.conn.cmdconverters;

import bhz.netty.ende3.pakg.AbstractPkg;
import bhz.netty.ende3.pakg.TCTCPkg;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Converter for sendPoi command
 */
@Component
public class SendPoiConverter implements DeviceCommandConverter {
    private static final Collection<String> CMDS = Collections.singleton("send.poi");

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
        pkg.setAction("POI");
        Map parmMap = new HashMap<String, Object>();
        parmMap.put("v", 1000);
        pkg.setParamMap(parmMap);
        return pkg;
    }
}
