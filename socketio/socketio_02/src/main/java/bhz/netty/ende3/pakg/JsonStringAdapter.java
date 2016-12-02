package bhz.netty.ende3.pakg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Common adapter for JSON like data fields
 */
public final class JsonStringAdapter<T> implements StringAdapter<T> {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new PbJacksonModule());
    private final Class<T> type;

    public JsonStringAdapter(Class<T> type) {
        this.type = type;
    }

    @Override
    public void toString(StringBuilder sb, T o) {
        try {
            String s = mapper.writeValueAsString(o);
            sb.append(s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T fromString(String string) {
        try {
            T o = mapper.readValue(string, type);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
