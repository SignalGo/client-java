/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalgo.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.joda.time.DateTime;

/**
 *
 * @author white
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable {

  
    public int id;
 
    private String guid = null;
 
    public String title;
    
    public DateTime startDate;
  
    public DateTime endDate;
   
    public String manager;

    public int statementDuration;
}