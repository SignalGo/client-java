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
    @GoMethodName(name = "GetAllProjects2",type = GoMethodName.MethodType.invoke)
    public void hello(){
            connector.autoInvokeAsync(new GoResponseHandler<MessageContract<List<Project>>>() {
                @Override
                public void onResponse(MessageContract<List<Project>> contract) {
                    MessageContract<List<Project>> o=contract;
                    System.err.println("hiiiiiiiiiiiiiiiiiiiiiiiii  "+(++a));
                }
            },new DateTime(0));
    }
    
    
    
    void destroy(){
        connector.destroyCallBack(this);
    }
    
 
    public void getConnector(Connector c) {
        connector=c;
        
    }
    
    
}



