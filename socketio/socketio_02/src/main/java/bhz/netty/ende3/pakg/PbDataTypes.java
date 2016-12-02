package bhz.netty.ende3.pakg;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Registry of known data types
 */
public final class PbDataTypes {
    public static final int TYPE_INT = 3;
    public static final int TYPE_ASCII = 4;
    public static final int TYPE_UTF8 = 2;
    public static final int TYPE_BYTES = 14;
    public static final int TYPE_BOOL = 6;
    public static final int TYPE_DOUBLE = 8;
    public static final int TYPE_TIME = 5;
    // below types in specification does not have code
    public static final int TYPE_LIST = 10;
    public static final int TYPE_MAP = 9;
    // for object we do not known correct id
    public static final int TYPE_OBJECT = -1;
    private final Map<Integer, PbDataType<?>> map = new TreeMap<>();
    /**
     * Note, it not truly ASCII, but in document ASCII string can only contain below symbols.
     */
    private static final Pattern ASCII_MATCHER = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final PbDataTypes INSTANCE = new PbDataTypes();

    private PbDataTypes() {
        register(TYPE_INT, Integer.class, new StringAdapter<Integer>() {
            @Override
            public void toString(StringBuilder sb, Integer o) {
                sb.append(o);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        });
        register(TYPE_ASCII, String.class, new StringAdapter<String>() {
            @Override
            public void toString(StringBuilder sb, String o) {
                sb.append(o);
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        }); //ascii
        register(TYPE_UTF8, String.class, new StringAdapter<String>() {
            @Override
            public void toString(StringBuilder sb, String o) {
                sb.append(Base64Utils.encodeToString(o.getBytes(StandardCharsets.UTF_8)));
            }

            @Override
            public String fromString(String string) {
                byte[] bytes = Base64Utils.decodeFromString(string);
                return new String(bytes, StandardCharsets.UTF_8);
            }
        }); //base64 utf-8 string
        register(TYPE_BYTES, byte[].class, new StringAdapter<byte[]>() {
            @Override
            public void toString(StringBuilder sb, byte[] o) {
                sb.append(Base64Utils.encodeToString(o));
            }

            @Override
            public byte[] fromString(String string) {
                return Base64Utils.decodeFromString(string);
            }
        }); // base64 bytes
        register(TYPE_BOOL, Boolean.class, new StringAdapter<Boolean>() {
            @Override
            public void toString(StringBuilder sb, Boolean o) {
                sb.append(o ? "T" : "F");
            }

            @Override
            public Boolean fromString(String string) {
                return string.equalsIgnoreCase("T");
            }
        }); // t,T,f,F without ''
        register(TYPE_DOUBLE, Double.class, new StringAdapter<Double>() {
            @Override
            public void toString(StringBuilder sb, Double o) {
                sb.append(o);
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        });
        register(TYPE_TIME, Date.class, new StringAdapter<Date>() {
            final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMddHHmmss")
                    .withZone(DateTimeZone.forID("+08:00"));

            @Override
            public void toString(StringBuilder sb, Date o) {
                try {
                    formatter.printTo(sb, o.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Date fromString(String string) {
                return new Date(formatter.parseMillis(string));
            }
        });// GMT +8 yyMMddHHmmss
        register(TYPE_LIST, List.class, new JsonStringAdapter<>(List.class));
        // TODO how we can resolve object type?
        register(TYPE_OBJECT, Object.class, new JsonStringAdapter<>(Object.class));
        register(TYPE_MAP, Map.class, new JsonStringAdapter<>(Map.class));
    }


    private <T> void register(int id, Class<T> type, StringAdapter<T> adapter) {
        PbDataType<T> cdt = new PbDataType<>(id, type, adapter);
        map.put(cdt.getId(), cdt);
    }

    /**
     * Resolve appropriate CTIP type by type id
     *
     * @param type
     * @return
     */
    public static PbDataType<?> getById(int type) {
        return INSTANCE.map.get(type);
    }

    /**
     * Resolve appropriate CTIP type by class of value.
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> PbDataType<T> getByValue(T value) {
        int type = TYPE_OBJECT;
        if (value instanceof Boolean) {
            type = TYPE_BOOL;
        } else if (value instanceof String) {
            if (isAscii((String) value)) {
                type = TYPE_ASCII;
            } else {
                type = TYPE_UTF8;
            }
        } else if (value instanceof Integer) {
            type = TYPE_INT;
        } else if (value instanceof Double) {
            type = TYPE_DOUBLE;
        } else if (value instanceof Date) {
            type = TYPE_TIME;
        } else if (value instanceof byte[]) {
            type = TYPE_BYTES;
        } else if (value instanceof List) {
            type = TYPE_LIST;
        } else if (value instanceof Map) {
            type = TYPE_MAP;
        }
        PbDataType<T> ctipDataType = (PbDataType<T>) getById(type);
        ctipDataType.getType().cast(value);// test for correct resolving
        return ctipDataType;
    }

    private static boolean isAscii(String value) {
        return ASCII_MATCHER.matcher(value).matches();
    }
}
