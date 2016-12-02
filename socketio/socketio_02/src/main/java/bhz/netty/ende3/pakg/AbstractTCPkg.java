package bhz.netty.ende3.pakg;

import java.util.Map;

/**
 * Abstract package bean for TC packets (from system to device)
 */
public class AbstractTCPkg extends AbstractPkg {
    private String id;
    private String categoryId;
    private Map<String, Object> paramMap;

    @PbFieldInfo(position = 1, type = PbDataTypes.TYPE_INT)
    @Override
    public int getSequence() {
        return super.getSequence();
    }

    /**
     * Communication code, sim card number or device number,
     * it must be able to uniquely identify the car machine ID number.
     *
     * @return
     */
    @PbFieldInfo(position = 2)
    public String getId() {
        return id;
    }

    /**
     * Communication code, sim card number or device number,
     * it must be able to uniquely identify the car machine ID number.
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Command category identification string
     *
     * @return
     */
    @PbFieldInfo(position = 3)
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Command category identification string
     *
     * @param categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
