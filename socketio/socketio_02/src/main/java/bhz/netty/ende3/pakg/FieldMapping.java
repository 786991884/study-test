package bhz.netty.ende3.pakg;

import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * Mapping between package and bean fields
 */
public final class FieldMapping implements Comparable<FieldMapping> {
    private final PbDataType<?> type;
    private final int number;
    private final PropertyDescriptor descriptor;

    private FieldMapping(PropertyDescriptor descriptor, int position, PbDataType<?> type) {
        this.number = position;
        this.type = type;
        this.descriptor = descriptor;
        Assert.notNull(this.type);
        Assert.notNull(this.descriptor);
    }


    static FieldMapping create(PropertyDescriptor descriptor) {
        PbFieldInfo fi = null;
        Method reader = descriptor.getReadMethod();
        if (reader != null) {
            fi = reader.getAnnotation(PbFieldInfo.class);
        }
        Method writer = descriptor.getWriteMethod();
        if (fi == null && writer != null) {
            fi = writer.getAnnotation(PbFieldInfo.class);
        }
        if (fi == null) {
            return null;
        }
        PbDataType<?> type = PbDataTypes.getById(fi.type());
        return new FieldMapping(descriptor, fi.position(), type);
    }

    public PbDataType<?> getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public void set(AbstractPkg pkg, Object data) {
        try {
            Method writeMethod = descriptor.getWriteMethod();
            writeMethod.invoke(pkg, data);
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("Can not set value ''{0}'' to property ''{1}'' of type {2}.",
                    data, descriptor.getName(), descriptor.getPropertyType()));
        }
    }

    public Object get(AbstractPkg msg) {
        try {
            Method readMethod = descriptor.getReadMethod();
            return readMethod.invoke(msg);
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("Can not get value for property ''{0}'' of type {1}.",
                    descriptor.getName(), descriptor.getPropertyType()));
        }
    }

    @Override
    public int compareTo(FieldMapping o) {
        return Integer.compare(this.number, o.number);
    }

    @Override
    public String toString() {
        return "FieldMapping{" +
                "number=" + number +
                ", name=" + descriptor.getName() +
                '}';
    }

}
