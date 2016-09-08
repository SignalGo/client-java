/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;

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
            mapper.configure(MapperFeature.USE_ANNOTATIONS, true);
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
            mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setTimeZone(TimeZone.getDefault());
            SimpleModule module = new SimpleModule();
            module.addSerializer(DateTime.class,new DateTimeSerializer());
            module.addDeserializer(DateTime.class,new DateTimeDeserializer());
            mapper.registerModule(module);
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

    class DateTimeSerializer extends StdSerializer<DateTime>{
        DateTimeSerializer(){
            this(null);
        }

        protected DateTimeSerializer(Class<DateTime> t) {
            super(t);
        }

        @Override
        public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(dateTime.toString());
        }
    }

    class DateTimeDeserializer extends StdDeserializer<DateTime> {

        protected DateTimeDeserializer(Class<?> vc) {
            super(vc);
        }

        public DateTimeDeserializer() {
            this(null);
        }

        @Override
        public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String s=jsonParser.readValueAs(String.class);
            DateTime dateTime=new DateTime(s);
            return dateTime;
        }
    }
}
