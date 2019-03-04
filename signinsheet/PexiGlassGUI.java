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

//PexiGlassGUI Class
public class PexiGlassGUI extends JFrame {
    
    private static boolean isAdded = false;//added to main sign-in table
    private static Estimate currentEstimate;// current estimate from Estimate class 
    
    private static SheetTable table;// refers to main sign-in table
    
    //pexiglass thickness list 
    private static  String[] thickList = new String[]{" ","1/8", "1/4"};
    
    //JLabels 
    private static JLabel lengthL;
    private static JLabel widthL;
    private static JLabel quantityL;
    private static JLabel totalL;
    private static JLabel thicknessL;
    
    //JTextField 
    private static JTextField lengthTF;
    private static JTextField widthTF;
    private static JTextField quantityTF; 
    private static JTextField totalTF; 
    
    //JComboBoxs 
    private static JComboBox<String> thicknessCB = new JComboBox<String>(thickList);
    
    //JButtons 
    private static JButton estimateB;
    private  JButton submitB;//cant be static because of mulitiple calls 
    private JButton addB; 
    
    //create totalsTable from InfoTable 
    private static InfoTable totalsTable;
    
    //constructor with provided data
    public PexiGlassGUI(SheetTable t){
        
        //setting parent settings 
        super("PexiGlass Estimate ( All estimate are subjected to change )");
        super.setLayout(new BorderLayout());
        
        //JPanels for organization 
        JPanel firstRow = new JPanel();
        JPanel secondRow = new JPanel();
        JPanel thirdRow = new JPanel();
        
        //refers main sign-in table 
        this.table = t; 
        
        //set Labels 
        this.lengthL = new JLabel("Length: ");
        this.widthL = new JLabel("Width: ");
        this.quantityL = new JLabel("Quantity: ");
        this.totalL = new JLabel("Total: ");
        this.thicknessL = new JLabel("Thickness: ");
        
        //set Text fields 
        this.lengthTF = new JTextField(5);
        this.widthTF = new JTextField(5);
        this.quantityTF = new JTextField(5);
        this.totalTF = new JTextField(5);
        
        //make totalTF not editable 
        this.totalTF.setEditable(false); 
        
        //set buttons 
        this.estimateB = new JButton("Get Estimate");
        this.submitB = new JButton("Submit");
        this.addB = new JButton("+");
        
       //set columns IDs 
        String[] columnID = new String[]{"Quantity","Dimensions", "Estimate"};
        final Class[] columnClass = new Class[]{Integer.class, String.class, String.class};
        boolean[] editable = new boolean[]{false,false,false};
        
        //create totalsTables 
        this.totalsTable = new InfoTable(columnID,columnClass,editable,"center");
        
        //make totalsTables scrollable
        JScrollPane scrollPane = new JScrollPane(this.totalsTable);
        
        //add ButtonHandler to buttons 
        this.estimateB.addActionListener(new ButtonHandler());
        this.submitB.addActionListener(new ButtonHandler());
        this.addB.addActionListener(new ButtonHandler());
        
        //add labels, text fields, and buttons to JPanels 
        firstRow.add(this.lengthL);
        firstRow.add(this.lengthTF);
        firstRow.add(this.widthL);
        firstRow.add(this.widthTF);
        firstRow.add(this.thicknessL);
        firstRow.add(this.thicknessCB);
        firstRow.add(this.quantityL);
        firstRow.add(this.quantityTF);
        secondRow.add(estimateB);
        secondRow.add(addB);
        //thirdRow.add(this.totalL);
        thirdRow.add(scrollPane);
        //thirdRow.add(this.totalTF);
        secondRow.add(this.submitB);
        
        //add JPanels to parent class
        super.add(firstRow, BorderLayout.PAGE_START);
        super.add(secondRow, BorderLayout.CENTER);
        super.add(thirdRow, BorderLayout.PAGE_END);
        
        //set dimension column size
        totalsTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        
        //final parent class settings 
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//when close button is pushed, exit out 
        super.setSize(400, 600);// set JFrame size
        super.pack();
        super.setVisible(true);// display JFrame
        super.setLocationRelativeTo(null);
        
    }//END of PexiGlassGUI Class constructor 
    
    //Button Handler
    private class ButtonHandler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent a){
            
            //if "Get Estimate" is pressed 
            if(a.getActionCommand() == "Get Estimate"){
                
                //convert length and width to decimels and fractions 
                FractionToDecimel ftd = new FractionToDecimel(PexiGlassGUI.this.lengthTF.getText(),PexiGlassGUI.this.widthTF.getText());
                
                double l = ftd.getLength();//get dicemel length
                double w = ftd.getWidth();//get dicemel width
                
                //convert string to int for quantity 
                int q = Integer.parseInt(PexiGlassGUI.this.quantityTF.getText());
                
                //get selected thickness from combo box
                int i = PexiGlassGUI.this.thicknessCB.getSelectedIndex();
                String s = PexiGlassGUI.this.thickList[i];
                
                //get fraction size or dimension 
                String size = ftd.toString();
                
                //calculate estimate
                currentEstimate = new PexiglassEstimate(l,w,q,i,s,size);
                currentEstimate.calculateEstimate();
                
                //update total row that is being worked on 
                updateTotalRow();
                
                
            }//END of "Get Estimate" action command IF
            
            //if "Submit" pressed 
            if(a.getActionCommand() == "Submit"){
                
                //pass estimate to current customer in main sign-in table 
                PexiGlassGUI.this.table.passDetails(PexiGlassGUI.this.currentEstimate);
                dispose();// close estimate window 
            }//END of "Submit" action command IF
            
            //if "+" pressed 
            if(a.getActionCommand() == "+"){
                
                //pass estimate to current customer in main sign-in table
                PexiGlassGUI.this.table.passDetails(PexiGlassGUI.this.currentEstimate);
                PexiGlassGUI.this.isAdded = true;//estimate is added 
                
                reset();//reset text fields to blank 
                
            }// END "+" action command IF
             
        }//END of action listener 
    }//END of ButtonHandler()
    
    //gets details 
    public String getDetails(){
        
        return currentEstimate.toString();
        
    }//END of getDetails()
    
    //resets for next estimate 
    private void reset(){
        
        this.lengthTF.setText("");
        this.widthTF.setText("");
        this.quantityTF.setText("");
        this.totalTF.setText("");
        this.thicknessCB.setSelectedIndex(0);
        
    }//END of reset()
   
    //update total row
    private void updateTotalRow(){
        
        //if totalsTable is empty or estimate has been added 
        if(this.totalsTable.getRowCount() == 0 || this.isAdded == true){
           
            //add estimate to totalsTable 
           this.totalsTable.addRow(this.currentEstimate.toObjectArray());  
           this.isAdded = false;//reset to not added 
        }//END of empty table or has been add IF 
        
        //if totalsTable not empty or not added 
        else{
            
           //remove the most recent estimate 
           this.totalsTable.tableModel().removeRow(this.totalsTable.getRowCount() - 1);
           
           //add updated estimate 
           this.totalsTable.addRow(this.currentEstimate.toObjectArray()); 
        }//END of ELSE
        
    }//END of updateTotalRow()
    
    //set estimate mode 
    public void EstimateMode(){
        
        //lockout following buttons 
        this.submitB.setEnabled(false);
        this.addB.setEnabled(false);
    }//END of EstimateMode()
  
}//END of PexiGlassGUI Class 
