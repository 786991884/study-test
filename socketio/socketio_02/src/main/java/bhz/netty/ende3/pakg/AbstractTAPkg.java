package bhz.netty.ende3.pakg;

/**
 * Package which sent from device to server.
 */
public class AbstractTAPkg extends AbstractPkg {

    private String version;

    @PbFieldInfo(position = 2, type = PbDataTypes.TYPE_INT)
    @Override
    public int getSequence() {
        return super.getSequence();
    }

    /**
     * Protocol version
     *
     * @return
     */
    @PbFieldInfo(position = 1)
    public String getVersion() {
        return version;
    }

    /**
     * Protocol version
     *
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
