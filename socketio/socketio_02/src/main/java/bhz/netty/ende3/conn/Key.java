package bhz.netty.ende3.conn;

import java.io.Serializable;

/**
 * The immutable key with type for any purposes.
 */
public final class Key<T> implements Serializable {
    private final Class<T> type;
    private final String name;

    public Key(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Key)) {
            return false;
        }

        Key key = (Key) o;

        if (name != null ? !name.equals(key.name) : key.name != null) {
            return false;
        }
        if (type != null ? !type.equals(key.type) : key.type != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Key{" +
          "type=" + type +
          ", name='" + name + '\'' +
          '}';
    }
}
