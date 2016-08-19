
package signalgo.client;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by whiteman on 7/12/2016.
 */
public abstract class GoResponseHandler<T> {

    public abstract void onResponse(T t);

    public TypeToken<T> typeToken;
    public Type type; // or getRawType() to return Class<? super T>

    public Type getType() {
        typeToken = new TypeToken<T>(getClass()) {
        };
        type = typeToken.getType();
        return type;
    }

}
