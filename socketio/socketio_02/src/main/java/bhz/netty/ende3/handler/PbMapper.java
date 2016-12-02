package bhz.netty.ende3.handler;

import bhz.netty.ende3.pakg.*;

/**
 * A module which do conversion between packages and its string representation.
 */
public final class PbMapper {
    private static final String CRLF = "\r\n";
    private final PackagesRegistry registry;

    PbMapper(PackagesRegistry registry) {
        this.registry = registry;
    }

    public String encode(AbstractPkg msg) {
        final StringBuilder sb = new StringBuilder();
        String pkgType = resolvePackageType(msg);
        sb.append(pkgType);

        Class<? extends AbstractPkg> msgClass = msg.getClass();
        BeanMapping entry = registry.getBeanMapping(msgClass);
        if (entry == null) {
            throw new RuntimeException("Package " + msgClass + " is not registered in mapper.");
        }
        for (int i = 1; i < entry.getSize(); ++i) {
            sb.append(' ');
            FieldMapping fieldMapping = entry.getFieldMapping(i);
            Object value = fieldMapping.get(msg);
            if (value == null) {
                throw new RuntimeException("Package field '" + fieldMapping + "' can not be null.");
            }
            StringAdapter<Object> adapter = (StringAdapter<Object>) fieldMapping.getType().getAdapter();
            adapter.toString(sb, value);
        }
        sb.append(' ');
        sb.append(generateVerifyCode(sb.toString()));
        sb.append(CRLF);
        return sb.toString();
    }

    private String resolvePackageType(AbstractPkg msg) {
        Class<? extends AbstractPkg> msgClass = msg.getClass();
        final String packageType = PBUtils.getId(msgClass);
        if (packageType == null) {
            throw new RuntimeException("Package " + msgClass + " have null package type id.");
        }
        // we can use generic packages like 'TA*R' - this package represent all responses for TA packages
        // but client need concrete package type like 'TAOR'
        String concretePkgType = msg.getType();
        if (concretePkgType == null) {
            concretePkgType = packageType;
        }
        return concretePkgType;
    }

    public AbstractPkg decode(String rawMsg) {
        if (!rawMsg.endsWith(CRLF)) {
        }
        String msg = rawMsg.substring(0, rawMsg.length() - 2);// we trim \r\n
        final String[] fields = org.springframework.util.StringUtils.tokenizeToStringArray(msg, " ");
        final int length = fields.length;
        validate(msg, fields[length - 1]);
        //fill bean
        BeanMapping entry = registry.getBeanMapping(fields);
        AbstractPkg pkg = entry.create();
        // we skip VERIFY fields
        for (int i = 0; i < length - 1; ++i) {
            String field = fields[i];
            FieldMapping fieldMapping = entry.getFieldMapping(i);
            PbDataType<?> fieldType = fieldMapping.getType();
            Object value = parseType(fieldType, field);
            fieldMapping.set(pkg, value);
        }
        return pkg;
    }

    private <T> T parseType(PbDataType<T> fieldType, String field) {
        StringAdapter<T> adapter = fieldType.getAdapter();
        return adapter.fromString(field);
    }

    private void validate(String msg, String validationCode) {
        //validate
        String generatedCode = generateVerifyCode(msg.substring(0, msg.length() - validationCode.length()));
        if (!validationCode.equalsIgnoreCase(generatedCode)) {
        }
    }

    private String generateVerifyCode(String str) {
        // there we simply sum all bytes in string
        short sum = 0;
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; ++i) {
            sum += Math.abs(bytes[i]);
        }
        if (sum < 0) { // it can appear only on overload of int
        }
        return new String(Hex.encode(new byte[]{
                (byte) (sum >>> 8),
                (byte) sum
        }));
    }
}
