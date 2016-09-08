/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client;

import signalgo.client.models.MethodCallInfo;
import signalgo.client.models.MethodCallbackInfo;
import signalgo.client.util.GoAsyncHelper;
import signalgo.client.util.GoConvertorHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mehdi akbarian
 */
public class GoStreamWriter {
    
    private GoConvertorHelper convertorHelper;

    public GoStreamWriter() {
        convertorHelper=new GoConvertorHelper();
    }
    
    
    static Object o=new Object();
    public void send(final OutputStream outputStream,MethodCallInfo callInfo) throws JsonProcessingException, UnsupportedEncodingException, IOException{
        byte[] data=convertorHelper.byteConvertor(callInfo);
        byte[] size=getSize(data.length);
        final byte[] result=peroperData(data, size, (byte)0,(byte) 1);
        System.err.println("size = "+result.length);
        GoAsyncHelper.run(new Runnable() {
            public void run() {
                try {
                    
                    outputStream.write(result);
                    
                } catch (IOException ex) {
                    Logger.getLogger(GoStreamWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
    }

    public void typeAuthentication(OutputStream outputStream) throws IOException {
        byte[] data=convertorHelper.byteConvertor("SignalGo/1.0");
        byte[] d= Charset.forName("UTF-8").encode("SignalGo/1.0").array();
        outputStream.write(data);
    }
    
    public void sendDeliveryNotify(final OutputStream outputStream, MethodCallbackInfo callInfo) throws JsonProcessingException, UnsupportedEncodingException, IOException {
        byte[] data = convertorHelper.byteConvertor(callInfo);
        byte[] size = getSize(data.length);
        final byte[] result = peroperData(data, size, (byte) 0, (byte) 2);
        System.err.println("size = " + result.length);
        GoAsyncHelper.run(new Runnable() {
            public void run() {
                try {

                    outputStream.write(result);

                } catch (IOException ex) {
                    Logger.getLogger(GoStreamWriter.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }
    
    
    public byte[] getSize(int length){
        byte[] len=ByteBuffer.allocate(4).putInt(length).array();
        
        for (int i = 0; i < len.length / 2; i++) {
            byte temp = len[i];
            len[i] = len[len.length - i - 1];
            len[len.length - i - 1] = temp;
        }
        return len;
    }
    
    public byte[] peroperData(byte[] data ,byte[] length,byte compressMode ,byte dataType){
        byte[] result=new  byte[data.length+6];
        result[0]=dataType;
        result[1]=compressMode;
        for(int i=2;i<6;i++){
            result[i]=length[i-2];
        }
        System.arraycopy(data, 0, result, 6, data.length);
        return result;
    }
    
    
    
}
