/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client.util;

/**
 *
 * @author white
 */
public class GoAsyncHelper {
    
    public static void run(Runnable runnable,boolean  daemon){
        Thread t=new Thread(runnable);
        t.setDaemon(daemon);
        t.start();
    }
    
    public static void run(Runnable runnable) {
        run(runnable,true);
    }

    
    
}
