package signalgo.client;

import signalgo.client.annotations.GoMethodName;
import signalgo.client.annotations.GoServiceName;
import signalgo.client.models.MethodCallInfo;
import signalgo.client.models.MethodCallbackInfo;
import signalgo.client.models.ParameterInfo;
import signalgo.client.util.GoConvertorHelper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author mehdi akbarian
 */
public class GoCallbackHandler {

    private LinkedHashMap<String, List<Object>> callBackClasses;
    private GoConvertorHelper convertorHelper;
    private Connector connector;

    public GoCallbackHandler(Connector connector) {
        this.connector = connector;
        callBackClasses = new LinkedHashMap<String, List<Object>>();
        convertorHelper=new GoConvertorHelper();
    }

    public boolean registerCallback(Object observer) {
        String serviceName = getServiceName(observer);
        if (serviceName == null) {
            return false;
        }
        List<Object> observers;
        if (callBackClasses.containsKey(serviceName)) {
            observers = callBackClasses.get(serviceName);
            if (observers.contains(observer)) {
                return false;
            }
            observers.add(observer);
        } else {
            observers = new ArrayList<Object>();
            observers.add(observer);
        }
        callBackClasses.put(serviceName, observers);
        registerMethods(observer);
        return true;
    }

    public boolean removeCallback(Object observer) {
        if (callBackClasses != null) {
            String serviceName = getServiceName(observer);
            if (serviceName != null && callBackClasses.containsKey(getServiceName(observer))) {
                List<Object> observers = callBackClasses.get(serviceName);
                observers.remove(observer);
                return true;
            }
        }
        return false;
    }

    private void registerMethods(Object o) {
        List<String> methodNames = new ArrayList<String>();
        Method[] methods;
        if (o instanceof Class)
            methods = ((Class) o).getMethods();
        else
            methods = o.getClass().getMethods();
        for (Method m : methods) {
            if (m.getAnnotation(GoMethodName.class) != null) {
                methodNames.add(m.getName());
            }
        }
    }

    public void onServerCallBack(MethodCallInfo mci) throws Exception {
        if (callBackClasses.containsKey(mci.getServiceName())) {
            List<Object> observers = callBackClasses.get(mci.getServiceName());
            for (Object o : observers) {
                Method[] methods;
                if (o instanceof Class)
                    methods = ((Class) o).getMethods();
                else
                    methods = o.getClass().getMethods();
                for (Method m : methods) {
                    if (m.getAnnotation(GoMethodName.class) != null) {
                        GoMethodName gmn = m.getAnnotation(GoMethodName.class);
                        if (mci.getMethodName().equals(gmn.name()) && gmn.type() == GoMethodName.MethodType.emit) {
                            Object returnVal;
                            if (o instanceof Class) {
                                Object c = ((Class) o).newInstance();
                                returnVal = m.invoke(c, getParams(mci.getParameters(), m.getParameterTypes()));
                            } else {
                                returnVal = m.invoke(o, getParams(mci.getParameters(), m.getParameterTypes()));
                            }
                            sendDeliveryNotify(mci, returnVal);
                        }
                    }
                }
            }
        }
    }

    private Object[] getParams(List<ParameterInfo> pis, Class<?>[] paramType) throws ClassNotFoundException {
        Object[] params = new Object[pis.size()];
        for (int i = 0; i < pis.size(); i++) {
            try {
                params[i]=convertorHelper.deserialize(pis.get(i).getValue(),paramType[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    private String getServiceName(Object observer) {
        String serviceName = null;
        Class c;
        if (observer instanceof Class)
            c = (Class) observer;
        else
            c = observer.getClass();
        for (Annotation a : c.getAnnotations()) {
            if (a instanceof GoServiceName)
                serviceName = ((GoServiceName) a).name();
        }
        return serviceName;
    }

    private void sendDeliveryNotify(MethodCallInfo info, Object object) throws Exception {
        String data = null;
        if (object != null) {
            data = convertorHelper.serialize(object);
        }
        MethodCallbackInfo callbackInfo=new  MethodCallbackInfo();
        callbackInfo.setData(data);
        callbackInfo.setGuid(info.getGuid());
        connector.sendDeliveryNotify(callbackInfo);
        

    }
}
