package bhz.netty.ende3.pakg;

/**
 * @author Lenovo
 * @date 2016-12-01
 * @modify
 * @copyright
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker for field in CTIP message
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PbFieldInfo {
    /**
     * Position of marked field in package
     *
     * @return
     */
    int position();

    /**
     * Id of field PbDataType
     *
     * @return
     */
    int type() default PbDataTypes.TYPE_ASCII;
}

