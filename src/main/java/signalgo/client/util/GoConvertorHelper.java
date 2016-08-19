/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.TimeZone;

/**
 *
 * @author mehdi akbarian
 */
public class GoConvertorHelper {
private static String CHARSET="UTF-8";
    public GoConvertorHelper() {
    }
    
    private  ObjectMapper mapper ;
    public ObjectMapper getObjectMapper(){
        if(mapper==null){
        mapper=new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.setTimeZone(TimeZone.getDefault());
        }
        return mapper;
    }
    
    public String serialize(Object object) throws JsonProcessingException{
        return getObjectMapper().writeValueAsString(object);
    }
    
    public <T extends Object> T deserialize(String data,Class<T> s) throws IOException{
        return getObjectMapper().readValue(data, s);
    }
   
    public <T extends Object> T deserialize(String data,Type type) throws IOException{
        return getObjectMapper().readValue(data,getObjectMapper().constructType(type));
    }
    
    public byte[] byteConvertor(Object object) throws JsonProcessingException, UnsupportedEncodingException{
        String raw=serialize(object);
        return raw.getBytes(CHARSET);
    }
    
    public boolean configureMapper(MapperFeature feature,boolean activate){
        if(mapper!=null){
            mapper.configure(feature, activate);
            return true;
        }
        return false;
    }
}
