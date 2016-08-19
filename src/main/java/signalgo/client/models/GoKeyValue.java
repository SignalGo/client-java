/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client.models;

/**
 *
 * @author mehdi akbarian
 */
public class GoKeyValue<T1,T2> {
    private T1 key;
    private T2 value;

    public GoKeyValue(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    }

    public GoKeyValue() {
    }

    public T1 getKey() {
        return key;
    }

    public void setKey(T1 key) {
        this.key = key;
    }

    public T2 getValue() {
        return value;
    }

    public void setValue(T2 value) {
        this.value = value;
    }
    
    
}
