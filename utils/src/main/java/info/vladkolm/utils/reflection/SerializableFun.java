package info.vladkolm.utils.reflection;

import java.io.Serializable;

public interface SerializableFun extends Serializable {
    void accept(Object obj1, Object obj2);
}
