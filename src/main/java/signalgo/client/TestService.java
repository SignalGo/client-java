/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client;

import signalgo.client.annotations.GoMethodName;
import signalgo.client.annotations.GoServiceName;

import java.util.List;

import org.joda.time.DateTime;

/**
 *
 * @author mehdi
 */
@GoServiceName(name = "CPMService" , type = GoServiceName.GoClientType.Java ,usage = GoServiceName.GoUsageType.both)
public class TestService implements ClientDuplex{
    static int a=0;
    Connector connector;
    public TestService() {
//        super(connector);
        
    }
    @GoMethodName(name = "hello",type = GoMethodName.MethodType.invoke)
    public void hello(){
        connector.invokeAsync("hello","CPMService",new GoResponseHandler() {
            public void onResponse(Object t) {
                if(t!=null){
                    System.out.print(t.toString());

                }
            }
        },"mehdi");
    }

    @GoMethodName(name = "GetUserName",type = GoMethodName.MethodType.emit)
    public String bye(){
        System.err.println("hhhhhhh");
        //hello();
        return "bye "+"mehdi";
    }

    @GoMethodName(name = "GetData",type = GoMethodName.MethodType.invoke)
    public void getData(DateTime dateTime){
        System.err.println("time = "+dateTime);
        connector.autoInvokeAsync(new GoResponseHandler<MyClass>() {
            @Override
            public void onResponse(MyClass t) {
                System.out.println("myClass :  "+t.dateTime);
            }
        }, dateTime);
    }
    
    
    
    void destroy(){
        connector.destroyCallBack(this);
    }
    
 
    public void getConnector(Connector c) {
        connector=c;
        
    }
    
    
}



