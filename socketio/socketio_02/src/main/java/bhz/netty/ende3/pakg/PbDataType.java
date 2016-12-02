package bhz.netty.ende3.pakg;

/**
 * Data type of CTIP
 */
public final class PbDataType<T> {
    private final int id;
    private final Class<T> type;
    private final StringAdapter<T> adapter;

    public PbDataType(int id, Class<T> type, StringAdapter<T> adapter) {
        this.id = id;
        this.type = type;
        this.adapter = adapter;
    }

    /**
     * number identity of type
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public Class<T> getType() {
        return type;
    }

    public StringAdapter<T> getAdapter() {
        return adapter;
    }

    @Override
    public String toString() {
        return "PbDataType{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PbDataType)) {
            return false;
        }

        PbDataType that = (PbDataType) o;

        if (id != that.id) {
            return false;
        }
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
