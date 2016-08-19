/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client;

/**
 *
 * @author white
 */
public interface GoSocketListener {
    public enum SocketState{
        Connected,connecting,Reconnecting,Disconnected;
    }
    void onSocketChange(SocketState lastState ,SocketState correntState);
    
    void socketExeption(Exception e);
    
}
