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
import javax.swing.table.*;

//SharpenGUI Class
public class SharpeningGUI extends JFrame {
    
    private static Estimate currentEstimate;//current estimate
    private static InfoTable totalsTable;//table for totals  
    private static boolean isAdded = false;// add to main sign-in table or not 
    private static SheetTable table;// refers to main sign-in table
    private static String type;// sharpening type 
    
    //JLabels
    private static JLabel quantityL;
    private static JLabel descriptionL;
    
    //JTextFields
    private static JTextField quantityTF;
    private static JTextField descriptionTF;
    
    //JButtons 
    private JButton getEstimateB;
    private JButton addB;
    private JButton submitB; 
    
    //constructor with provided data 
    public SharpeningGUI(SheetTable t, String s){
        
        //set parent settings 
        super(s + " Estimate ( All estimates are subjected to change )");
        super.setLayout(new BorderLayout()); 
        
        this.type = s;// set type of sharpening 
        
        //set JLabels 
        this.quantityL = new JLabel("Quantity: ");
        this.descriptionL = new JLabel("Description: ");
        
        //set JTextFields 
        this.quantityTF = new JTextField(5);
        this.descriptionTF = new JTextField(15);
        
        //set table 
        this.table = t; 
        
        //set column IDs 
        String[] columnID = new String[]{"Quantity", "Description", "Estimate"};
        Class[] columnClass = new Class[]{Integer.class, String.class, Double.class};
        boolean[] editable = new boolean[]{false,false,false};
        
        //create totalsTable from InfoTables 
        this.totalsTable = new InfoTable(columnID,columnClass,editable,"center");
        
        //make totalsTable scrollable 
        JScrollPane scrollPane = new JScrollPane(this.totalsTable);
        
        //set JButtons 
        this.getEstimateB = new JButton("Get Estimate");
        this.addB = new JButton("+");
        this.submitB = new JButton("Submit");
        
        //add ButtonHandler to buttons 
        this.getEstimateB.addActionListener(new ButtonHandler());
        this.addB.addActionListener(new ButtonHandler());
        this.submitB.addActionListener(new ButtonHandler());
        
        //JPanels for organization 
        JPanel firstRow = new JPanel();
        JPanel secondRow = new JPanel();
        JPanel thirdRow = new JPanel();
        
        //add labels, text fields, and buttons to JPanels 
        firstRow.add(this.quantityL);
        firstRow.add(this.quantityTF);
        firstRow.add(this.descriptionL);
        firstRow.add(this.descriptionTF);
        secondRow.add(this.getEstimateB);
        secondRow.add(this.addB);
        secondRow.add(this.submitB);
        thirdRow.add(scrollPane);
        
        //add JPanels to parent class
       super.add(firstRow, BorderLayout.PAGE_START);
       super.add(secondRow, BorderLayout.CENTER);
       super.add(thirdRow, BorderLayout.PAGE_END);
       
       //set description column width to 250 
       totalsTable.getColumnModel().getColumn(1).setPreferredWidth(250);
       
       //final parent settings 
       super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//when close button is pushed, exit out 
       super.setSize(400, 600);// set JFrame size
       super.pack();
       super.setVisible(true);// display JFrame
       super.setLocationRelativeTo(null);
    }//END of Constructor with provided data 
    
    //ButtonHandler 
    private class ButtonHandler implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent a){
        
            //if "Get Estimate" is pressed 
            if(a.getActionCommand() == "Get Estimate"){
                
                //convert string to int for quantity 
                int q = Integer.parseInt(SharpeningGUI.this.quantityTF.getText());
                
                //set description 
                String d = SharpeningGUI.this.descriptionTF.getText();
                
                //create and calculate estimate 
                SharpeningGUI.this.currentEstimate = new SharpeningEstimate(q,SharpeningGUI.this.type,d);
                SharpeningGUI.this.currentEstimate.calculateEstimate();
            
                updateTotalRow();// update total row that is being worked on 
            }//END of "Get Estimate" IF 
            
            //if "+" is pressed 
            if(a.getActionCommand() == "+"){
                
                //pass estimate to current customer in main sign-in table
                SharpeningGUI.this.table.passDetails(SharpeningGUI.this.currentEstimate);
                SharpeningGUI.this.isAdded = true;//has been added to main sign-in table
                reset();// reset for next estimate 
            
            }//END of "+" IF 
            
            //if "Submit" is pressed 
            if(a.getActionCommand() == "Submit"){
                
                //pass estimate to current customer in main sign-in table
                SharpeningGUI.this.table.passDetails(SharpeningGUI.this.currentEstimate);
                reset();// reset for next estimate 
                dispose();//close estimate window 
            
            }//END "Submit" IF 
        
        }// END of Action listener 
    }//END of ButtonHandler()
    
    //update total row 
    private void updateTotalRow(){
        
        //if totalsTable is empty or estimate has been added
        if(this.totalsTable.getRowCount() == 0 || this.isAdded == true){
            
            //add estimate to table
            this.totalsTable.addRow(this.currentEstimate.toObjectArray());
            this.isAdded = false;//reset to not added 
        }//END of empty table or has been add IF
        
        //if totalsTable not empty or not added
        else{
            
            //remove the most recent estimate
            this.totalsTable.tableModel().removeRow(this.totalsTable.getRowCount() -1);
            
            //add updated estimate
            this.totalsTable.addRow(this.currentEstimate.toObjectArray());
            
        }//END of ELSE
    }//END of updateTotalRow()
    
    //reset for nest estimate 
    private void reset(){
        
        this.quantityTF.setText("");
        this.descriptionTF.setText("");
        
    }//END of reset()
    
    //set Estimate mode
    public void EstimateMode(){
        
        //lockout following buttons 
        this.submitB.setEnabled(false);
        this.addB.setEnabled(false);
    }//END of EstimateMode()
    
}//END of SharpeningGUI Class

