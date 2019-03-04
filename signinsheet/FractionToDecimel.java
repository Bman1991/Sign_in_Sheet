/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsheet;

import java.math.*;

/**
 *
 * @author Brian
 */

//Fraction to Decimel Class
//converts fractions to decimels and visa versa  
public class FractionToDecimel {
    
    private static double length;
    private static double width; 
    private static String lengthStr;
    private static String widthStr;
    
    //converts fraction to decimel
    public FractionToDecimel(String l, String w){
        
        this.length = convertToDecimal(l);// sets length to decimal format
        this.width = convertToDecimal(w);//set width to decimal format 
        
        this.lengthStr = convertToString(l);//converts length to string 
        this.widthStr = convertToString(w);//converts width to string 
    }//END of FractionToDecimel()
    
    //converts fraction to decimal 
    private double convertToDecimal(String s){
        
        double x = 0.0;
        
        // check if string contains dashes or forward slashes
        if(s.contains("-") == true && s.contains("/") == true){ 
            
           //convert fraction to decimal 
           x = convertFraction(s);
        }//END of is fraction IF
        
        //if its already a decimal 
        else{
            
            //convert string to double
            x = Double.valueOf(s);
            
        }//END of ELSE
        
        return x;// return decimal
        
    }//END of convertToDecimal()
     
    //convert to string or convert decimal to fraction 
    private String convertToString(String s){
        
       String str = "";
       
       //check if string contains a period
       if(s.contains(".") == true){ 
            
            //convert decimel to fraction 
            str = convertDecimel(s); 
       }//END of decimel IF
       
       //if its already a fraction 
       else{
           
            //set str to fraction 
           str = s;
       }//END of ELSE
       
       return str;//return fraction or string 
    }//END of convertToString
    
    //convert fraction to decimel 
    private double convertFraction(String x){
        
        //separates numbers into whole number, numerator, and denominator 
         double w = Double.valueOf(x.substring(0, x.indexOf('-')));//whole number = [1]-1/2
         double n = Double.valueOf(x.substring(x.indexOf('-') + 1, x.indexOf('/')));//numerator = 1-[1]/2
         double d = Double.valueOf(x.substring(x.indexOf('/') + 1));//denominator = 1-1/[2]
         
         double decimel = w + n / d;//calculate fraction 
         
         
         return decimel;// return decimel
         
    }//END convertFraction()
    
    //converts decimel to fraction 
    private String convertDecimel(String x){
        
       //separates numbers into whole number, numerator, and denominator
       int w = Integer.valueOf(x.substring(0, x.indexOf('.'))); //whole number = [1].5
       int n = Integer.valueOf(x.substring(x.indexOf('.') + 1));// numerator = 1.[5]
       int d = 1;// denominator set to 1
      
       //for charactor lengh of numerator, multiple denominator by 10 
       //if n =5 then d =10. OR n = 25 then d = 100 
      for(int i = 0; i < String.valueOf(n).length(); i++){
          d *= 10; 
      }//END of for loop
      
      //while the remainder is greater than 1
      // n = 5 and d = 10, the outcome will be 1/2
      while(d%5 < 1){
          
          n /= 5;
          d /= 5; 
      }//END of while loop 
       
       return String.format("%d-%d/%d", w,n,d);// return fraction 
    }
    
    //get length
    public double getLength(){
        
        return this.length;
        
    }//END of getLength()
    
    //get Width
    public double getWidth(){
        
        return this.width;
        
    }//END of getWidth()
    
    //get String length or fraction 
    public String getLengthStr(){
        
        return this.lengthStr;
        
    }//END of getLengthStr()
    
    //get String width
    public String getWidthStr(){
        
        return this.widthStr;
        
    }//END of getWidthStr()
    
    //convert dimensions to string. 
    @Override
    public String toString(){
        
        return String.format("%s x %s", getLengthStr(), getWidthStr());
        
    }//END of toString()
    
}//END of FractionToDeicmel Class