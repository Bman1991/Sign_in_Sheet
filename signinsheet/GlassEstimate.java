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

//Glase estimate Class
public class GlassEstimate extends Estimate {
    
    private String sizeStr;
    
    public GlassEstimate(double l, double w, int q, String size){
        
        super(l,w,q,0.0);//set parent constructor variables and set total to 0.0
        
        this.sizeStr = size;// set size or dimensions of glass cut 
    }//END of GlassEstimate()
    
    //get size
    public String getSize(){
        
        return this.sizeStr;
        
    }//END of getSize()
    
    //--- Override Methods ---
    
    //calculate estimate
    @Override
    public double calculateEstimate(){
        
        //get parent variables and calculate estimate
        super.setTotal((super.getLength() * super.getWidth() * 0.015 + super.getLaborCost()) * super.getQuantity());
        
        return super.getTotal();//return estimate
    }//END of calculateEstimate()
    
    //convert to object array 
    @Override
    public Object[] toObjectArray(){
        
        return new Object[]{super.getQuantity(), getSize(),String.format("$%.2f",super.getTotal())};
        
    }//END of toObjectArray()
    
    //convert to object array short version 
    @Override
    public Object[] toObjectArrayShort(){
        
        return new Object[]{"Glass Cut", super.getQuantity(), getSize(), String.format("$%.2f",super.getTotal())};
        
    }//END toObjectArrayShort()
    
}//END of GlassEstimate Class
