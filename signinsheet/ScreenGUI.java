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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


//ScreenGUI Class
public class ScreenGUI extends JFrame{
   
    private static Estimate currentEstimate;// current Estimate 
    private static SheetTable table;// refers to main sign-in table
    private static InfoTable iTable;// table 
    private static boolean isAdded = false;// added to main sign-in or not 
    
    //JLabels 
    private static JLabel lengthL = new JLabel("Length: ");
    private static JLabel widthL = new JLabel("Width: ");
    private static JLabel quantityL = new JLabel("Quantity: ");
    private static JLabel totalL = new JLabel("Total: ");
    private static JLabel pLaborL = new JLabel("Labor: ");
    private static JLabel pSplineL = new JLabel("Spline: ");
    private static JLabel pScreenTypeL = new JLabel("Screen Type: ");
    private static JLabel pScreenColorL = new JLabel("Screen Color:");
    private static JLabel pScreenCostL = new JLabel("Screen: ");
    private static JLabel pSurchargeL = new JLabel("Surcharge: ");
    private static JLabel priceMsg = new JLabel("Estimate subjected to change");
    
    //JTextFields
    private static JTextField lengthTF = new JTextField(5); 
    private static JTextField widthTF = new JTextField(5);
    private static JTextField quantityTF = new JTextField(5);
    private static JTextField totalTF = new JTextField(5);
    private static JTextField pLaborTF = new JTextField(5);
    private static JTextField pSplineTF = new JTextField(5);
    private static JTextField pScreenTF = new JTextField(5); 
    private static JTextField pSurchargeTF = new JTextField(5);
    
    //Screen type list 
    private static String[] screenList = new String[]{" ","Aluminum", "Fiberglass", "Pet Screen"};
    
    //screen color list
    private static String[] screenColorList = new String[]{" ", "Gray", "Black"};
    
    //JComboBox 
    private static JComboBox<String> screenType = new JComboBox<String>(screenList);
    private static JComboBox<String> screenColor = new JComboBox<String>(screenColorList);
   
    //JCheckBox 
    private static JCheckBox woodFrameC  = new JCheckBox("Is Wood Frame?");
    
    //JButtons 
    private static JButton getEstimate = new JButton("Get Estimate");
    private  JButton submitB = new JButton("Submit");//cant be static because of mulitiple calls 
    private JButton addB = new JButton("+");
    
    //constructor with provided data 
    public ScreenGUI(SheetTable t){
        
        //set parent settings 
       super("Screen Estimate ( all estimates will be subjected to change )");
       super.setLayout(new BorderLayout());
        
       //JPanels for organization 
       JPanel firstRow = new JPanel();
       JPanel secondRow = new JPanel();
       JPanel thirdRow = new JPanel();
       
       //refers to main sign-in table 
       this.table = t; 
       
       //add ButtonHandler to buttons 
       this.getEstimate.addActionListener(new ButtonHandler());
       this.submitB.addActionListener(new ButtonHandler());
       this.addB.addActionListener(new ButtonHandler());
       this.woodFrameC.addItemListener(new CheckBoxHandler());
       
       //add Item listener to combo box screen type 
       this.screenType.addItemListener(new ItemHandler());
       
       //make following not editable 
       this.pLaborTF.setEditable(false);
       this.pScreenTF.setEditable(false);
       this.pSplineTF.setEditable(false);
       this.pSurchargeTF.setEditable(false);
       this.totalTF.setEditable(false);
       
       //set column IDs 
       String[] columnID = new String[]{"Quantity","Type / Color / Size","Labor","Spline","Screen","Surcharge", "Estimate"};
       final Class[] columnClass = new Class[]{Integer.class, String.class, String.class, String.class, String.class, String.class, String.class};
       boolean[] editable = new boolean[]{false,false,false,false,false,false,false};
       
       //create table with InfoTable 
       this.iTable = new InfoTable(columnID, columnClass, editable, "center");
       
       //make table scrollable 
       JScrollPane scrollPane = new JScrollPane(this.iTable);//this.totalsTable);
       
       //set scroll pane size 
       scrollPane.setPreferredSize(new Dimension(1000,600));
       
       //add labels, text fields, and buttons to JPanels 
       firstRow.add(this.lengthL);
       firstRow.add(this.lengthTF);
       firstRow.add(this.widthL);
       firstRow.add(this.widthTF);
       firstRow.add(this.pScreenTypeL);
       firstRow.add(this.screenType);
       firstRow.add(this.pScreenColorL);
       firstRow.add(this.screenColor);
       firstRow.add(this.quantityL);
       firstRow.add(this.quantityTF);
       firstRow.add(this.woodFrameC);
        
        
       secondRow.add(this.getEstimate);
       secondRow.add(this.addB);
       
       secondRow.add(this.submitB);
       thirdRow.add(scrollPane);
       
       //add JPanels to parent class 
       super.add(firstRow, BorderLayout.PAGE_START);
       super.add(secondRow, BorderLayout.CENTER);
       super.add(thirdRow, BorderLayout.PAGE_END);
       
       //set table width to 450
       this.iTable.getColumnModel().getColumn(1).setPreferredWidth(450);
       
       //final parent settings 
       super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//when close button is pushed, exit out 
       super.setSize(400, 600);// set JFrame size
       super.pack();
       super.setVisible(true);// display JFrame
       super.setLocationRelativeTo(null);
        
    }//END of Constructor with provided data 
    
    //Button Handler 
    private class ButtonHandler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent a){
            
            //if "Get Estimate" pressed 
           if(a.getActionCommand() == "Get Estimate"){
               
               //convert length and width to decimel and fraction 
                FractionToDecimel ftd = new FractionToDecimel(ScreenGUI.this.lengthTF.getText(),ScreenGUI.this.widthTF.getText());
                
                double l = ftd.getLength();//get decimel length
                double w = ftd.getWidth();//get decimel width
                
                //convert string to int for quantity 
                int q = Integer.parseInt(ScreenGUI.this.quantityTF.getText());
                
                //get screen type and color 
                String s = ScreenGUI.this.screenList[ScreenGUI.this.screenType.getSelectedIndex()];
                String c = ScreenGUI.this.screenColorList[ScreenGUI.this.screenColor.getSelectedIndex()];
                String size = ftd.toString();// get size or dimension string 
                
                //if frame is wood 
                if(ScreenGUI.this.woodFrameC.isSelected() == true){
                    
                    //add surcharge 
                    ScreenGUI.this.currentEstimate = new ScreenEstimate(l,w,q,s,c,10.0,size);
                    
                }//END of wood frame IF
                
                //if frame is not wood 
                else{
                    
                    //don't add surchange 
                    ScreenGUI.this.currentEstimate = new ScreenEstimate(l,w,q,s,c,0.0,size);
                    
                }//END of ELSE
                
                getEstimate();//get estimate 
            
           }//END of "Get Estimate" action command IF 
           
           //if "Submit" is pressed 
           if(a.getActionCommand() == "Submit"){
               
               //pass estimate to current customer in main sign-in table
               ScreenGUI.this.table.passDetails(ScreenGUI.this.currentEstimate);
               reset();//reset window for next estimate
               dispose();//close estimate window 
            
           }//END of "Submit" action command IF
           if(a.getActionCommand() == "+"){
               
               //pass estimate to current customer in main sign-in table
               ScreenGUI.this.table.passDetails(ScreenGUI.this.currentEstimate);
               ScreenGUI.this.isAdded = true;// has been added 
               reset();//reset window for next estimate 
           }//END of "+" action command IF 
            
        }//END of action listener 
    }//END of ButtonHandler()
    
    //Item Handler for ComboBox
    private class ItemHandler implements ItemListener{
        
        @Override
        public void itemStateChanged(ItemEvent i){
            
            //if blank is selected 
            if(i.getItem() == ""){
                
                //enable screen color selection 
                ScreenGUI.this.screenColor.setEnabled(true);
                
            }//END of blank IF
            
            //if Aluminum is selected 
            if(i.getItem() == "Aluminum"){
                
                //enable screen color selection
                ScreenGUI.this.screenColor.setEnabled(true);
                
            }//END of Aluminum IF 
            
            //if Fiberglass is selected
            if(i.getItem() == "Fiberglass"){
                
                //enable screen color selection
                ScreenGUI.this.screenColor.setEnabled(true);
                
            }//END of Fiberglass IF
            
            //if Pet Screen is selected
            if(i.getItem() == "Pet Screen"){
                
                //set selection index to 2 
                ScreenGUI.this.screenColor.setSelectedIndex(2);
                
                //disable screen color selection
                ScreenGUI.this.screenColor.setEnabled(false); 
                
            }//END of Pet Screen IF
            
        }//END of itemStateChange listener
        
    }//END of ItemHandler()
    
    //Check box handler NOT BEING USED
    private class CheckBoxHandler implements ItemListener{
        
        @Override
        public void itemStateChanged(ItemEvent i){
            
            //Not being used 
            
        }//END of itemStateChanged listener
        
    }//END of CheckBoxHandler
    
    //get details 
    public String getDetails(){
        
        return this.currentEstimate.toString();
        
    }//END of getDetails()
    
    //get estimate 
    public void getEstimate(){
       
        this.currentEstimate.calculateEstimate();
        //ScreenEstimate currScreen = (ScreenEstimate) this.currentEstimate;
        
        updateTotalRow();// update total row that is being worked on 
     
    }//END of getEstimate()
    
    //resets for next estimate 
    private void reset(){
        
        this.lengthTF.setText("");
        this.widthTF.setText("");
        this.quantityTF.setText("");
        this.totalTF.setText("");
        this.screenType.setSelectedIndex(0);
        this.screenColor.setSelectedIndex(0);
        this.woodFrameC.setSelected(false);
       
    }//END of reset()
    
    //update total row
     private void updateTotalRow(){
         
        //if totalsTable is empty or estimate has been added
        if(this.iTable.getRowCount() == 0 || this.isAdded == true){//this.totalsTable.getRowCount() == 0 || this.isAdded == true){
           
            //add estimate to table 
           this.iTable.addRow(this.currentEstimate.toObjectArray()); 
           this.isAdded = false;//reset to not added 
        }//END of empty table or has been add IF
        
        //if totalsTable not empty or not added 
        else{
          
            //remove the most recent estimate 
           this.iTable.tableModel().removeRow(this.iTable.getRowCount() - 1);
           
           //add updated estimate
           this.iTable.addRow(this.currentEstimate.toObjectArray());
          
        }//END of ELSE
        
    }//END of updateTotalRow()
     
     //set estimate mode
    public void EstimateMode(){
        
        //lockout the following buttons 
        this.submitB.setEnabled(false);
        this.addB.setEnabled(false);
        
    }//END of EstimateMode()
        
}//END of ScreenGUI Class 
