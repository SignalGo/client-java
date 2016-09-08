/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client.util;

import signalgo.client.models.GoKeyValue;
import signalgo.client.models.ParameterInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.DateTime;

/**
 *
 * @author white
 */
public class GoClientHelper {
    
    private static ConcurrentHashMap<String,GoKeyValue<GoAutoResetEvent,Object>> waitedMethodsForResponse;
    private GoConvertorHelper convertorHelper;
    private boolean isDisposed = false;
    public GoClientHelper(){
        convertorHelper=new GoConvertorHelper();
    }
    public List<ParameterInfo> getListOfParam(Object... param) {
        List<ParameterInfo> params = new ArrayList();
        for (int i = 0; i < param.length; i++) {
            try {
                String value = convertorHelper.serialize(dataCorrection(param[i]));
                String type = param[i].getClass().getName();
                params.add(new ParameterInfo(type, value));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
       
        return params;
    }
    
    public Object dataCorrection(Object o){
        if(o instanceof DateTime){
            return ((DateTime) o).toString();
        }
        return o;
    }
    public boolean addInvokedMethod(String guid,GoKeyValue gkv){
        if(waitedMethodsForResponse==null)
            waitedMethodsForResponse=new ConcurrentHashMap<String, GoKeyValue<GoAutoResetEvent,Object>>();
        if(waitedMethodsForResponse.containsKey(guid))
            return false;
        waitedMethodsForResponse.put(guid, gkv);
        return true;
    }
    
    public GoKeyValue<GoAutoResetEvent,Object> getGoKeyValue(String guid){
        if(waitedMethodsForResponse==null)
            return null;
        return waitedMethodsForResponse.get(guid);
    }
    
    public boolean removeInvokedMethod(String guid){
        if(waitedMethodsForResponse==null)
            return false;
        return waitedMethodsForResponse.remove(guid, waitedMethodsForResponse.get(guid));
    }
    
    public boolean setValue(String guid,Object value){
        if(getGoKeyValue(guid)!=null){
            getGoKeyValue(guid).setValue(value);
            return true;
        }
        return false;  
    }
    public void endWait(String guid){
        if (getGoKeyValue(guid) != null) {
            GoKeyValue gkv=getGoKeyValue(guid);
            synchronized(gkv){
                gkv.notify();
            }
        }
    }
    
    public void dispose() {
        if(waitedMethodsForResponse==null)
            return;
        isDisposed = true;
        for (Map.Entry<String, GoKeyValue<GoAutoResetEvent, Object>> entry : waitedMethodsForResponse.entrySet()) {
            entry.getValue().getKey().notify();
        }
    }

    public boolean isDisposed() {
        return isDisposed;
    }
}
