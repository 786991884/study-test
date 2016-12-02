package bhz.netty.ende3.pakg;

import java.util.Date;
import java.util.Map;

/**
 * Terminal control command.
 * <code>TC SEQ ID TC ACTION PARAM_MAP TIME VERIFY</code>
 */
@PbPackageInfo(id = "TC:TC")
public class TCTCPkg extends AbstractTCPkg {
    private String action;

    @PbFieldInfo(position = 4)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @PbFieldInfo(position = 5, type = PbDataTypes.TYPE_MAP)
    @Override
    public Map<String, Object> getParamMap() {
        return super.getParamMap();
    }

    @PbFieldInfo(position = 6, type = PbDataTypes.TYPE_TIME)
    @Override
    public Date getTime() {
        return super.getTime();
    }
}
