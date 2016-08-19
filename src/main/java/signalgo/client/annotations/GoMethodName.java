/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author mehdi akbarian
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GoMethodName {
    public enum MethodType{
        invoke(0),emit(1);
        
        private int id;

        private MethodType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
    String name();
    
    MethodType type();
    
}
