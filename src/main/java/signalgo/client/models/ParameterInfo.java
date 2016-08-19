/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client.models;

/**
 *
 * @author white
 */
public class ParameterInfo {
    
    private String type;
    private String value;

    public ParameterInfo() {
    }

    public ParameterInfo(String type, String value) {
        this.type = type;
        this.value = value;
    }
    
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
