package digitalcity.demeyert.overstockmanager.exception;

/**
 * thrown when an entity couldn't be found
 */
public class ElementNotFoundException extends RuntimeException {

    private final Class<?> clazz;
    private final Object forId;

    public ElementNotFoundException(Class<?> clazz, Object forId) {
        super("Cannot find entity {"+clazz.getSimpleName()+"} for id {"+ forId +"}");
        this.clazz = clazz;
        this.forId = forId;
    }

    public ElementNotFoundException(String message, Class<?> clazz, Object forId) {
        super(message);
        this.clazz = clazz;
        this.forId = forId;
    }


    public Class<?> getClazz() {
        return clazz;
    }

    public Object getForId() {
        return forId;
    }
}

