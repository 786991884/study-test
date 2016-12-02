package bhz.netty.ende3.pakg;

import java.util.Date;
import java.util.Map;

/**
 * TAO package
 * <code>TAO VER SEQ ACTION TIME TK NEED_ACK LON LAT ALT SPD DIR VSTATUS TSTATUS DSTATUS PARAM_MAP VERIFY</code>
 */
@PbPackageInfo(id = "TAO")
public class TAOPkg extends AbstractTAPkg {
    private String action;
    private String token;
    private boolean needAck;
    private double lon;
    private double lat;
    private int alt;
    private int speed;
    private int direction;
    private Map<String, Object> vehicleStatus;
    private Map<String, Object> terminalStatus;
    private Map<String, Object> driverStatus;
    private Map<String, Object> paramMap;

    @PbFieldInfo(position = 3)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @PbFieldInfo(position = 4, type = PbDataTypes.TYPE_TIME)
    public Date getTime() {
        return super.getTime();
    }


    @PbFieldInfo(position = 5)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @PbFieldInfo(position = 6, type = PbDataTypes.TYPE_BOOL)
    public boolean isNeedAck() {
        return needAck;
    }

    public void setNeedAck(boolean needAck) {
        this.needAck = needAck;
    }

    @PbFieldInfo(position = 7, type = PbDataTypes.TYPE_DOUBLE)
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @PbFieldInfo(position = 8, type = PbDataTypes.TYPE_DOUBLE)
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @PbFieldInfo(position = 9, type = PbDataTypes.TYPE_INT)
    public int getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    @PbFieldInfo(position = 10, type = PbDataTypes.TYPE_INT)
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @PbFieldInfo(position = 11, type = PbDataTypes.TYPE_INT)
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @PbFieldInfo(position = 12, type = PbDataTypes.TYPE_MAP)
    public Map<String, Object> getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(Map<String, Object> vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    @PbFieldInfo(position = 13, type = PbDataTypes.TYPE_MAP)
    public Map<String, Object> getTerminalStatus() {
        return terminalStatus;
    }

    public void setTerminalStatus(Map<String, Object> terminalStatus) {
        this.terminalStatus = terminalStatus;
    }

    @PbFieldInfo(position = 14, type = PbDataTypes.TYPE_MAP)
    public Map<String, Object> getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(Map<String, Object> driverStatus) {
        this.driverStatus = driverStatus;
    }

    @PbFieldInfo(position = 15, type = PbDataTypes.TYPE_MAP)
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
