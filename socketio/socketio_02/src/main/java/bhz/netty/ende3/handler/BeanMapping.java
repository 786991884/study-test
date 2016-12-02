package bhz.netty.ende3.handler;

import bhz.netty.ende3.pakg.AbstractPkg;
import bhz.netty.ende3.pakg.FieldMapping;
import bhz.netty.ende3.pakg.PBUtils;
import org.springframework.beans.BeanUtils;

/**
 * Entry of mapping
 */
public final class BeanMapping {
    private final Class<?> type;
    private final String id;
    private final FieldMapping[] fields;

    BeanMapping(Class<?> type) {
        this.type = type;
        this.id = PBUtils.getId(type);
        this.fields = PBUtils.createFieldsMapping(this.type);
    }

    /**
     * Package id.
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Create package bean.
     * @return
     */
    public AbstractPkg create() {
        return (AbstractPkg) BeanUtils.instantiate(type);
    }

    /**
     * Get mapping for specified field.
     * @param fieldNumber
     * @return
     */
    FieldMapping getFieldMapping(int fieldNumber) {
        FieldMapping mapping = fields[fieldNumber];
        return mapping;
    }

    /**
     * Return count of package fields.
     * @return
     */
    public int getSize() {
        return fields.length;
    }

}
