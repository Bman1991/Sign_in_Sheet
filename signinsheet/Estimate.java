/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsheet;

import java.io.Serializable;
import java.math.*;

/**
 *
 * @author Brian
 */

//Estimate abstract class
//implements serializable for read/write functions to file 
public abstract class Estimate implements Serializable{
    
    private double length;
    private double width; 
    private int quantity;
    private double total; 
    private double laborCost;
    
    //constructor with provided data
    public Estimate(double l, double w, int q, double t){
        
        this.length = l;//set length
        this.width = w;//set width
        this.quantity = q;//set quantity
        this.total = t;//set total
        this.laborCost = 0.0;//set laborCost
        
    }//END of Estimate Constructor with provided data
    
    //set Length
    public void setLength(double l){
        
        this.length = l;
        
    }//END of setLength()
    
    //get Length
    public double getLength(){
        
        return this.length;
        
    }//END of getLength()
    
    //set Width
    public void setWidth(double w){
        
        this.width = w; 
        
    }//END of setWidth()
    
    //get Width
    public double getWidth(){
        
        return this.width; 
        
    }//END getWidth()
    
    //set Quantity
    public void setQuantity(int q){
        
        this.quantity = q; 
        
    }//END of setQuantity()
    
    //get Quantity
    public int getQuantity(){
        
        return this.quantity;
        
    }//END getQuantity
    
    //set Total
    public void setTotal(double t){
        
        this.total = t;
        
    }//END setTotal()
    
    //get Total
    public double getTotal(){
        
        return this.total; 
        
    }//END getTotal()
    
    //get Labor Cost
    public double getLaborCost(){
        
        double price = 1.60;// price of labor
        
        //calculate labor 
        double labor = Math.ceil((getLength() + getWidth()) / 10);
        
        this.laborCost = labor * price;// set laborCost 
        
        return this.laborCost;//return laborCost
        
    }//END of getLaborCost()
    
    //conver Estimate variables to string format
    @Override
    public String toString(){
        
        return String.format("%d - %.2f x %.2f", this.quantity, this.length, this.width);
        
    }
    
    //--- abstract methods ---
    
    //calculate Estimate
    public abstract double calculateEstimate(); 
    
    //to object array 
    public abstract Object[] toObjectArray();
    
    //to object array short version 
    public abstract Object[] toObjectArrayShort();
}
