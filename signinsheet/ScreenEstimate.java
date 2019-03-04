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

//Screen estimate Class
public class ScreenEstimate extends Estimate {
    
    //pricing lists 
    private double[] laborList;
    private double[] splineList;
    private double[] alumList;
    private double[] petList;
    private double[] fiberList;
    
    private double surcharge;// surcharge if frame is wood  
    private String screenStr;// screen type 
    private String screenColor;// screen color 
    private double labor;// labor
    private double spline;//spline 
    private double screen;//screen cost 
    private String sizeStr;//size string format 
    
    //constructor with provided data
    public ScreenEstimate(double l, double w, int q, String s, String c, double sur, String size){
        
        //set parent class 
        super(l,w,q,0.0);
        
        //set price list 
        this.laborList = new double[]{3.20,4.80,6.40,8.00,9.60,11.20,12.86,14.40,16.00,17.60,19.20,20.80,22.40,24.00};
        this.splineList = new double[]{0.83,1.25,1.66,2.08,2.50,2.91,3.33,3.75,4.16,4.58,4.99,5.40,5.82,6.24};
        this.alumList = new double[]{0.99,.99,1.98,3.18,3.36,4.76,6.16,7.56,8.96,10.36,11.76,13.16,14.56,15.96};
        this.petList = new double[]{2.99,2.99,3.50,3.50,6.00,8.50,11.00,13.50,16.00,18.50,21.00,23.50,26.00,28.50};
        this.fiberList = new double[]{0.49,0.49,0.98,1.16,1.99,2.82,3.65,4.48,5.31,6.14,6.97,7.80,8.65,9.46};
        
        this.surcharge = sur;//set surcharge for wood frame
        this.screenStr = s;//set screen string type  
        this.screenColor = c;// set screen color 
        this.labor = 0.0;// set labor 
        this.spline = 0.0;//set spline 
        this.screen = 0.0;//set screen price 
        this.sizeStr = size;//set size string 
        
    }//END of constructor with provided data 
    
    //set labor cost 
    public void setLabor(int i){
        
        this.labor = laborList[i];
        
    }//END of setLabor()
    
    //get labor cost 
    public double getLabor(){
        
        return this.labor;
        
    }//END of getLabor()
    
    //set spline cost 
    public void setSpline(int i){
        
        this.spline = splineList[i];
        
    }//END of setSpline()
    
    //get spline cost 
    public double getSpline(){
        
        return this.spline;
        
    }//END of getSpline()
    
    //set screen type 
    public void setScreen(int i){
        
        if(this.screenStr == "Aluminum"){
            
            //set price 
            this.screen = this.alumList[i];
            
        }//END of Aluminum
        
        if(this.screenStr == "Fiberglass"){
            
            //set price 
            this.screen = this.fiberList[i];
            
        }//END of Fiberglass
        
        if(this.screenStr == "Pet Screen"){
            
            //set price 
            this.screen = this.petList[i];
            
        }//END of Pet screen 
        
    }//END setScreen()
    
    //set surcharge 
    public void setSurcharge(double sur){
        
        this.surcharge = sur;
        
    }//END setSurcharge()
    
    //get surcharge
    public double getSurcharge(){
        
        return this.surcharge;
        
    }//END getsurcharge()
    
    //get screen
    public double getScreen(){
        
        return this.screen;
        
    }//END of getScreen()
    
    //set screen string 
    public void setScreenString(String s){
        
        this.screenStr = s; 
        
    }//END setScreenString()
    
    //get screen string
    public String getScreenString(){
        
        return this.screenStr;
        
    }//END of getScreenString()
    
    //set screen color 
    public void setScreenColor(String c){
        
        this.screenColor = c;
        
    }//END of setScreenColor()
    
    //get screen color 
    public String getScreenColor(){
        
        return this.screenColor;
        
    }//END getScreenColor()
    
    //get pricing index
    public int getPricingIndex(double l, double w){
        
       int index = 0;
       
       double size = l + w; 
        
       if(size <= 20){
           index = 0;// set index  
       }//END of IF
       
       if(size >= 21){
           index = 1;// set index 
       }//END of IF
       
       if(size >= 31){
           index = 2;// set index
       }//END of IF
       
       if(size >= 41){
           index = 3;// set index
       }//END of IF
       
       if(size >= 51){
           index = 4;// set index
       }//END of IF
       
       if(size >= 61){
           index = 5;// set index
       }//END of IF
       
       if(size >= 71){
           index = 6;// set index
       }//END of IF
       
       if(size >= 81){
           index = 7;// set index
       }//END of IF
       
       if(size >= 91){
           index = 8;// set index
       }//END of IF
       
       if(size >= 101){
           index = 9;// set index 
       }//END of IF
       
       if(size >= 111){
           index = 10;// set index
       }//END of IF
       
       if(size >= 121){
           index = 11;// set index
       }//END of IF
       
       if(size >= 131){
           index = 12;// set index
       }//END of IF
       
       if(size >= 141){
           index = 13; // set index
       }//END of IF
             
       return index;// return pricing index 
        
    }//END of getPriceIndex()
    
    //get size string 
    public String getSize(){
        
        return this.sizeStr;
        
    }//END of getSize()
   
    //--- Override Methods ---
    
    //calcuate estimate
    @Override
    public double calculateEstimate(){
        
        //get price of labor, spline and screen by pricing index 
        int i = getPricingIndex(super.getLength(),super.getWidth());
        setLabor(i);
        setSpline(i);
        setScreen(i);
        
        //get parent variables and calculate estimate 
        super.setTotal((getLabor() + getSpline() + getScreen() + getSurcharge()) * super.getQuantity());
        
        return super.getTotal();//return total estimate 
    }//END of calculateEstimate()
    
    //convert to string 
    @Override
    public String toString(){
        
        return String.format("%d %s(%s) - %.2f x %.2f", super.getQuantity(),getScreenString(),getScreenColor(), super.getLength(),super.getWidth());
        
    }//END of toSring()
    
    //convert to object array 
    @Override
    public Object[] toObjectArray(){
        
        return new Object[]{super.getQuantity(),String.valueOf(getScreenString() + " / " +getScreenColor()+ " / " + getSize()), String.format("$%.2f",getLabor()), String.format("$%.2f",getSpline()), String.format("$%.2f",getScreen()), String.format("$%.2f",getSurcharge()), String.format("$%.2f",super.getTotal())};
        
    }//END of toObjectArray()
    
    //convert to object array short verison 
    @Override
    public Object[] toObjectArrayShort(){
        
        return new Object[]{"Screen Repair", super.getQuantity(), String.valueOf(getScreenString() + " / " +getScreenColor()+ " / " + getSize()),String.format("$%.2f", super.getTotal())};
        
    }//END of toObjectArrayShort()
    
}//END of ScreenEstimate()
