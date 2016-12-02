package bhz.netty.ende3.pakg;

import java.util.Date;

/**
 * Package of PB.
 *
 * @author Lenovo
 * @date 2016-12-01
 * @modify
 * @copyright
 */
public abstract class AbstractPkg {
    private String type;
    private int sequence;
    private Date time;

    @PbFieldInfo(position = 0)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
