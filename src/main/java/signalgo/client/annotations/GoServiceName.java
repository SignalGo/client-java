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
@Target(ElementType.TYPE)
public @interface GoServiceName {

    public enum GoClientType {
        Android(0), Java(1);
        private int id;
        private GoClientType(int id) {
            this.id = id;
        }
    }
    
    public enum GoUsageType {
        invoke(0), emit(1), both(2);
        private int id;

        private GoUsageType(int id) {
            this.id = id;
        }
    }

    String name() default "";
    
    GoClientType type() default GoClientType.Android;
    
    GoUsageType usage() default GoUsageType.both;
}
