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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

//GlassGUI Class
public class GlassGUI extends JFrame {

    //variables 
    private static Estimate currentEstimate;// current estimate
    private static InfoTable totalsTable;// total cost table 
    private static boolean isAdded = false;// estimate is passed to current customer or not 
    private static SheetTable table;// refers to main sign-in table
    
    //JLabels 
    private static JLabel lengthL;
    private static JLabel widthL;
    private static JLabel quantityL;
    private static JLabel totalL;
    
    //JTextFields
    private static JTextField lengthTF;
    private static JTextField widthTF;
    private static JTextField quantityTF; 
    private static JTextField totalTF; 
   
    //JButtons 
    private static JButton estimateB;
    private  JButton submitB;
    private JButton addB; 
    
    public GlassGUI(SheetTable t){
        
        //sets parent class settings 
        super("Glass Estimate ( All estimates are subject to change )");
        super.setLayout(new BorderLayout());
        
        //JPanel containers for organization 
        JPanel firstRow = new JPanel();
        JPanel secondRow = new JPanel();
        JPanel thirdRow = new JPanel();
       
        //set table to main sign-in Table
        this.table = t;
        
        //set JLabels 
        this.lengthL = new JLabel("Length: ");
        this.widthL = new JLabel("Width: ");
        this.quantityL = new JLabel("Quantity: ");
        this.totalL = new JLabel("Total: ");
        
        //set Text fields 
        this.lengthTF = new JTextField(5);
        this.widthTF = new JTextField(5);
        this.quantityTF = new JTextField(5);
        this.totalTF = new JTextField(5);
        
        //set totalTF to not editable 
        this.totalTF.setEditable(false); 
        
        //set JButtons  
        this.estimateB = new JButton("Get Estimate");
        this.submitB = new JButton("Submit");
        this.addB = new JButton("+");
        
        // add ButtonHandlers to buttons 
        this.estimateB.addActionListener(new ButtonHandler());
        this.submitB.addActionListener(new ButtonHandler());
        this.addB.addActionListener(new ButtonHandler());
        
        //set column IDs 
        String[] columnID = new String[]{"Quantity","Dimensions", "Estimate"};
        final Class[] columnClass = new Class[]{Integer.class, String.class, String.class};
        boolean[] editable = new boolean[]{false,false,false};
        
        //create totalsTable from InfoTable 
        this.totalsTable = new InfoTable(columnID,columnClass,editable,"center");
        
        //make totalsTable scrollable 
        JScrollPane scrollPane = new JScrollPane(this.totalsTable);
        
        //add Labels, Text Fields, and Buttons to JPanel containers 
        firstRow.add(this.lengthL);
        firstRow.add(this.lengthTF);
        firstRow.add(this.widthL);
        firstRow.add(this.widthTF);
        firstRow.add(this.quantityL);
        firstRow.add(this.quantityTF);
        secondRow.add(estimateB);
        secondRow.add(addB);
        secondRow.add(this.submitB);
        thirdRow.add(scrollPane);
        
        //add JPanels to parent class 
        super.add(firstRow, BorderLayout.PAGE_START);
        super.add(secondRow, BorderLayout.CENTER);
        super.add(thirdRow, BorderLayout.PAGE_END);
        
        //set dimensions column width
        totalsTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        
        //final parent settings 
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//when close button is pushed, exit out 
        super.setSize(400, 600);// set JFrame size
        super.pack();
        super.setVisible(true);// display JFrame
        super.setLocationRelativeTo(null);

        
    }//END of GlassGUI Class
    
    //Button Handle method
    private class ButtonHandler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent a){
            
            //if "Get Estimate" is pressed
            if(a.getActionCommand() == "Get Estimate"){
              
                //convert length and width to decimel and fraction
                FractionToDecimel ftd = new FractionToDecimel(GlassGUI.this.lengthTF.getText(),GlassGUI.this.widthTF.getText());
                
                double l = ftd.getLength();// get decimel length
                double w = ftd.getWidth();// get decimel width 
                int q = Integer.parseInt(GlassGUI.this.quantityTF.getText());// convert string to int for quantity 
                String size = ftd.toString();// get size or dimension in fraction 
               
                //calculate estimate 
                currentEstimate = new GlassEstimate(l,w,q,size);
                currentEstimate.calculateEstimate();
                
                updateTotalRow();//update total row that is currently being worked on 
             
            }//END of "Get Estimate" action command IF
            
            //if "Submit" is pressed 
            if(a.getActionCommand() == "Submit"){
                
                //pass estimate to current customer in main sign-in table 
                GlassGUI.this.table.passDetails(GlassGUI.this.currentEstimate);
                reset();//reset text field to blank 
                dispose();//close Glass estimate window 
                
            }//END of "Submit" action command IF
            
            //if "+" is pressed 
            if(a.getActionCommand() == "+"){
                
                //pass estimate to current customer in main sign-in table
                GlassGUI.this.table.passDetails(GlassGUI.this.currentEstimate);
                GlassGUI.this.isAdded = true;// estimate is added 
                reset();// reset text field to blank 
            }//END of "+" action command IF
            
        }//END of action listener 
        
    }//END of ButtonHandler()
    
    //get details 
    public String getDetails(){
        
        return this.currentEstimate.toString();
        
    }//END of getDetails 
    
    //resets text fields for next estimate 
    private void reset(){
        
        this.lengthTF.setText("");
        this.widthTF.setText("");
        this.quantityTF.setText("");
        this.totalTF.setText("");
       
    }//END of reset()
    
    //updates total row 
     private void updateTotalRow(){
        
         //if totalsTable is empty or estimate has been added 
        if(this.totalsTable.getRowCount() == 0 || this.isAdded == true){
           
           //add estimate to totalsTable 
           this.totalsTable.addRow(this.currentEstimate.toObjectArray());  
           this.isAdded = false;//reset to not added 
        }//END of row empty or added IF 
        
        //if total is not empty or not added 
        else{
           //remove most recent added estimate
           this.totalsTable.tableModel().removeRow(this.totalsTable.getRowCount() - 1);
           
           //add newly update estimate
           this.totalsTable.addRow(this.currentEstimate.toObjectArray()); 
        }//END of ELSE 
        
    }//End of updateTotalRow()
     
     //set estimate mode 
     public void EstimateMode(){
        
        //lockout the following buttons 
        this.submitB.setEnabled(false);
        this.addB.setEnabled(false);
    }
}
