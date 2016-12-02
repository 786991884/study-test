package bhz.netty.ende3.conn;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 */
public final class ConnectionAttributesImpl implements ConnectionAttributes {
    private final ConcurrentMap<Key<?>, Object> map = new ConcurrentHashMap<>();

    @Override
    public <T> T get(Key<T> key) {
        Object o = map.get(key);
        return cast(key, o);
    }

    private static <T> T cast(Key<T> key, Object o) {
        return key.getType().cast(o);
    }

    @Override
    public <T> T put(Key<T> key, T value) {
        Object old = map.put(key, cast(key, value));
        return cast(key, old);
    }

    @Override
    public <T> T computeIfAbsent(Key<T> key, Function<Key<?>, ?> function) {
        Object old = map.computeIfAbsent(key, function);
        return cast(key, old);
    }

    @Override
    public Map<Key<?>, Object> getMap() {
        return ImmutableMap.copyOf(map);
    }
}
