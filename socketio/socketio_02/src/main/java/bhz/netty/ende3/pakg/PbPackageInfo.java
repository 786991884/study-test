package bhz.netty.ende3.pakg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker for CTIP message
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PbPackageInfo {
    /**
     * Identification string of package type.
     *
     * @return
     */
    String id();
}
