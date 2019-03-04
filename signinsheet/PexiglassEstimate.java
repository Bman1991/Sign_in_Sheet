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

//PexiglassEstimate Class
public class PexiglassEstimate extends Estimate {
    
    private double[] thicknessList;
    private double thickness;
    private String thicknessStr;
    private String sizeStr;
    
    //constructor with provided data 
    public PexiglassEstimate(double l, double w, int q, int i, String s, String size){
        
        //set parent class 
        super(l,w,q,0.0);
        
        this.thicknessList = new double[]{0.0, 0.015, 0.030};//thickness list 
        this.thickness = this.thicknessList[i];//pexiglass thickness 
        this.thicknessStr = s;// thickness string 
        this.sizeStr = size;// size string 
    }//END of constructor 
    
    //set thickness int 
    public void setThickness(int i){
        
        this.thickness = this.thicknessList[i];
        
    }//END of setThickness() int 
    
    //get thickness
    public double getThickness(){
        
        return this.thickness;
        
    }//END of getThickness() int 
    
    //set thickness string 
    public void setThicknessStr(String s){
        
        this.thicknessStr = s;
        
    }//END setThicknessStr()
    
    //get Thickness String 
    public String getThicknessStr(){
       
        return this.thicknessStr;
        
    }//END getThicknessStr()
    
    //get Size 
    public String getSize(){
        
        return this.sizeStr;
        
    }//End of getSize()
    
    //--- Override Methods ---
    
    //convert to object array 
    @Override
    public Object[] toObjectArray(){
        
        return new Object[]{super.getQuantity(),String.valueOf(getSize()+ " x " + getThicknessStr()),String.format("$%.2f",super.getTotal())};
        
    }//END toObjectArray()
    
    //convert to object array short version
    @Override
    public Object[] toObjectArrayShort(){
        
        return new Object[]{"Pexiglass Cut", super.getQuantity(), String.valueOf(getSize()+ " x " + getThicknessStr()), String.format("$%.2f",super.getTotal())};
        
    }//END of toObjectArrayShort()
    
    //Calculate Estimate
    @Override
    public double calculateEstimate(){
        
        //get parent variables and calculate estimate
        super.setTotal((super.getLength() * super.getWidth() * getThickness() + super.getLaborCost())*super.getQuantity()); 
        
        return super.getTotal();// return estimate 
    }//END of calculateEstimate()
    
    //convert size or dimension to string 
    @Override
    public String toString(){
        
        return String.format("%d - %.2f x %.2f x %s",super.getQuantity(),super.getLength(),super.getWidth(),getThicknessStr());
        
    }//END toString()
    
    
}
