package bhz.netty.ende3.pakg;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CTIP utilities
 */
public final class PBUtils {

    private PBUtils() {
    }


    /**
     * Resolve package type by annotation on specified type.
     *
     * @param type
     * @return
     */
    public static String getId(Class<?> type) {
        PbPackageInfo packageInfo = type.getAnnotation(PbPackageInfo.class);
        Assert.notNull(packageInfo, "Need an annotation " + PbPackageInfo.class.getName() + " on " + type);
        return packageInfo.id();
    }

    public static FieldMapping[] createFieldsMapping(Class<?> type) {
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(type);
        List<FieldMapping> fieldList = new ArrayList<>(descriptors.length);
        for (int i = 0; i < descriptors.length; ++i) {
            PropertyDescriptor descriptor = descriptors[i];
            FieldMapping fieldMapping = FieldMapping.create(descriptor);
            if (fieldMapping == null) {
                continue;
            }
            fieldList.add(fieldMapping);
        }
        FieldMapping[] fields = fieldList.toArray(new FieldMapping[fieldList.size()]);
        Arrays.sort(fields);
        // validation
        for (int i = 0; i < fields.length; ++i) {
            FieldMapping field = fields[i];
            if (field.getNumber() != i) {
                throw new RuntimeException(MessageFormat.format("Invalid mapping, number of property \"{0}\" not equal with it index in array: \"{1}\". " +
                        "It can happen if properties is sparse or different properties have one number: {2}", field, i, Arrays.toString(fields)));
            }
        }
        return fields;
    }
}
