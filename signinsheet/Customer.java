/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsheet;

/**
 *
 * @author Brian
 */

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.ArrayList;


//Customer Class  
public class Customer implements Serializable{
    
    private String date;
    private String name;
    private String phone;
    private String request; 
    private String description;
    private boolean isDone;
    private boolean hasBeenCalled;
    private ArrayList<Estimate> estimate; 
    private static final DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
    
    //constructor without provided data
    public Customer(){
        
        Date currentDate = new Date();// gets todays date
        
        this.date = dateformat.format(currentDate);//formats date mm/dd/yyyy
        this.name = ""; // set customer name to blank
        this.phone = "000-000-0000"; // set customer phone number
        this.request = ""; //set request to blank 
        this.description = "";//set description to blank
        this.isDone = false;// not done
        this.hasBeenCalled = false;//not called
        this.estimate = null; // no estimates 
        
    }//END of Customer constructor without provide data
    
    //construstor with provided data
    public Customer(String n, String p, String r, String d, ArrayList e){
        
        Date currentDate = new Date();// get todays date 
        
        this.date = dateformat.format(currentDate);//formats date mm/dd/yyyy
        this.name = n;// set customer name 
        this.phone = p;//set customer phone number
        this.request = r;//set customer requests 
        this.description = d;//set request description
        this.isDone = false;// not done
        this.hasBeenCalled = false;// not called
        this.estimate = e;// set estimates 
        
    }
    //--- Gets Methods ---
    
    //get date
    public String getDate(){
        
        return date; 
        
    }//END of getDate()
    
    //get customer's name
    public String getName(){
        
        return name; 
        
    }//END of getName()
    
    //get customer's phone number
    public String getPhoneNumber(){
        
        return phone; 
        
    }//END of getPhoneNumber()
    
    //gets customer's requests
    public String getRequest(){
        
        return request; 
        
    }//END of getRequest()
    
    //gets request description
    public String getDescription(){
        
        return description; 
        
    }//END of getDescription()
    
    //gets a specific estimate
    public Estimate getEstimate(int i){
       
       return this.estimate.get(i);
    }//END of getEstimate()
    
    //gets a estimates 
    public ArrayList getEstimateList(){
        
        return this.estimate;
        
    }//END of getEstimateList
    
    //gets "Done" status
    public boolean doneYet(){
        
        return isDone;
        
    }//END of doneYet()
    
    //gets "Called" status
    public boolean calledYet(){
        
        return hasBeenCalled; 
        
    }//END of calledYet()
    
    //--- Sets methods ---
    
    //sets customer's name
    public void setName(String name){
        
        this.name = name; 
        
    }//END of setName()
    
    //sets customer's phone number
    public void setPhoneNumber(String phone){
        
        this.phone = phone; 
        
    }//END of setPhoneNumber()
    
    //set customer's request
    public void setRequest(String request){
        
        this.request = request; 
        
    }//END of setRequest()
    
    //set request description
    public void setDescription(String description){
        
        this.description = description; 
        
    }//END of setDescription
    
    //set estimate
    public void setEstimate(ArrayList e){
        
        this.estimate = e;
        
    }//END of setEstimate
    
    //set "Done" status
    public void setDone(boolean done){
        
        this.isDone = done;
        
    }//END of setDone()
    
    // set "Called" status
    public void alreadyCalled(boolean called){
        
        this.hasBeenCalled = called; 
        
    }//END of alreadyCalled()
    
    //-- misc. methods ---
    
    //add an estimate
    public void addToEstimate(Estimate e){
        
        this.estimate.add(e);
        
    }//addToEstimate()
    
    //delete estimate by entry number
    public void deleteFromEstimate(int i){
        
        this.estimate.remove(i);
        
    }//END of deleteFromEstimate()
    
    //delete estimate by estimate
    public void deleteFromEstimate(Estimate e){
        
        this.estimate.remove(e);
        
    }//END of deleteFromEstimate()
    
    //convert Customer variables to string format
    @Override
    public String toString(){
        
        return String.format("date: %s \tname: %s \tphone: %s\trequest: %s\tdescription: %s\tCalled: %b\n", getDate(), getName(), getPhoneNumber(), getRequest(), getDescription(), doneYet(), calledYet());
        
    }
    //convert object to object array 
    public Object[] toObjectArray(){
        
        return new Object[]{getDate(), getName(), getPhoneNumber(), getRequest(), getDescription(), doneYet(), calledYet()};
        
    }
   
}
