package bhz.netty.ende3.handler;


import bhz.netty.ende3.pakg.AbstractPkg;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Registry for CTIP packages
 */
public class PackagesRegistry {
    private final Map<String, BeanMapping> idMap = new HashMap<>();
    private final Map<Class<?>, BeanMapping> classMap = new HashMap<>();

    public PackagesRegistry(Collection<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            BeanMapping entry = new BeanMapping(clazz);
            idMap.put(entry.getId(), entry);
            classMap.put(clazz, entry);
        }
    }

    public BeanMapping getBeanMapping(Class<? extends AbstractPkg> msgClass) {
        return classMap.get(msgClass);
    }

    public BeanMapping getBeanMapping(String[] fields) {
        // note that packages differs by type, and TC packages also differs by categoryId
        String messageType = fields[0];
        // so we need to use workaround
        if (messageType.equals("TC")) {
            //concat categoryId field
            messageType += ":" + fields[3];
        }
        return idMap.get(messageType);
    }
}
