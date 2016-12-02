package bhz.netty.ende3.pakg;

/**
 * Adapter which is responsible for conversion pb data between java type and it string representation.
 */
public interface StringAdapter<T> {

    void toString(StringBuilder sb, T o);

    T fromString(String string);
}
