package bhz.netty.ende3.pakg;


import java.util.Date;
import java.util.Map;

/**
 * We use common package bean for all 'TA*R' packages.
 * <code>TA*R SEQ TIME CODE MSG RESULT_MAP VERIFY\r\n</code>
 */
@PbPackageInfo(id = "TA*R")
public class TARPkg extends AbstractPkg {

    private int code;
    private String message;
    private Map<String, Object> resultMap;

    @PbFieldInfo(position = 1, type = PbDataTypes.TYPE_INT)
    @Override
    public int getSequence() {
        return super.getSequence();
    }

    @PbFieldInfo(position = 2, type = PbDataTypes.TYPE_TIME)
    @Override
    public Date getTime() {
        return super.getTime();
    }


    @PbFieldInfo(position = 3, type = PbDataTypes.TYPE_INT)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @PbFieldInfo(position = 4, type = PbDataTypes.TYPE_UTF8)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @PbFieldInfo(position = 5, type = PbDataTypes.TYPE_MAP)
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}
