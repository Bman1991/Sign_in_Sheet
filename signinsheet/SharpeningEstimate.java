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

//SharpeningEstimate Class
public class SharpeningEstimate extends Estimate {
    
    private String SharpenType;// type of sharpening 
    private String description; //description 
    private double price;// price of sharpening 
    
    //constructor with provided data 
    public SharpeningEstimate(int q, String s, String d){
        
        super(0.0,0.0,q,0.0);// set parent class
        this.SharpenType = s;// set sharpen type 
        this.description = d;// set description 
        
        setPrice(s);// set price 
        
    }//END of SharpeningEstimate()
    
    // set sharpen type, like knife, chainsaw, or mower blade  
    public void setSharpenType(String s){
        
        this.SharpenType = s;
        
    }//END of setSharpenType()
    
    //get sharpen type 
    public String getSharpenType(){
        
        return this.SharpenType;
        
    }//END of getSharpenType()
    
    //set description 
    public void setDescription(String d){
        
        this.description = d;
        
    }//END setDescription()
    
    //get description 
    public String getDescription(){
        
        return this.description;
        
    }//END of getDescription
    
    //set price 
    public void setPrice(String s){
        
        //if Knife sharpening 
        if(s == "Knife Sharpening"){
            this.price = 2; 
        }//END of Kife sharpening
        
        //if Scrissors Sharpening 
        if(s == "Scissor Sharpening"){
            this.price = 3;
        }//END of Scissor Sharpeing 
        
        //if Chainsaw Blade Sharping 
        if(s == "Chainsaw Blade Sharpening"){
            this.price = 10;
        }//END of Chainsaw blade sharpening 
        
        //if mower blade sharpening 
        if(s == "Mower Blade Sharpening"){
            this.price = 6; 
        }//END of Mower blade sharpeing 
        
    }//END of setPrice 
    
    //get price
    public double getPrice(){
        
        return this.price;
        
    }//END getPrice()
    
    //--- Override Methods ---
    
    //calculate estimate 
    @Override
    public double calculateEstimate(){
        
        //get parent variables and set total estimate 
        super.setTotal(super.getQuantity() * getPrice());
        
        return super.getTotal();//return total
    }//END of calculateEstimate()
    
    //convert to object array 
    @Override
    public Object[] toObjectArray(){
        
        return new Object[]{super.getQuantity(),getDescription(), String.format("$%.2f",super.getTotal())};
        
    }//END of toObjectArray()
    
    //convert to object array short version
    @Override 
    public Object[] toObjectArrayShort(){
        
        return new Object[]{getSharpenType(),super.getQuantity(), getDescription(), String.format("$%.2f",super.getTotal())};
        
    }//END toObjectArrayShort()
    
}//END SharpeningEstimate Class
