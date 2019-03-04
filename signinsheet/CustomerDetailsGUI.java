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
import java.awt.print.*;

//Customer Detail GUI Class
public class CustomerDetailsGUI extends JFrame {
    
    private Customer currentCustomer;//current customer object  
    
    //JLabels 
    private static JLabel nameL = new JLabel("Name: ");
    private static JLabel phoneL = new JLabel("Phone: ");
    private static JLabel requestL = new JLabel("Requests: ");
    private static JLabel descriptionL = new JLabel("Description: ");
    private static JLabel totalL = new JLabel("Total Estimate: ");
    
    //JTextFields 
    private static JTextField nameTF = new JTextField(15);
    private static JTextField phoneTF = new JTextField(15);
    private static JTextField descriptionTF = new JTextField(40);
    private static JTextField totalTF = new JTextField(5);
    
    //request table for listing request
    private static InfoTable requestTable;
    
    //constructor with provided customer data
    public CustomerDetailsGUI(Customer c){
        
        super("Customer Details");//set window title to "Customer Details"
        
        this.currentCustomer = c;// set current customer
        
        this.nameTF.setText(c.getName());// set nameTF to customer's name 
        this.phoneTF.setText(c.getPhoneNumber());// set phoneTF to customer's phone number
        this.descriptionTF.setText(c.getDescription());// set descriptionTF to request description
        
        this.totalTF.setText(String.format("$%.2f",addTotalEstimate()));// set totalTF to total estimate after adding
        
        //set textfield to not editable 
        this.nameTF.setEditable(false);
        this.phoneTF.setEditable(false);
        this.descriptionTF.setEditable(false);
       
        //set column IDs and editable columns 
        String[] columnID = new String[]{"Request","Quantity","Details", "Estimate"};
        final Class[] columnClass = new Class[]{String.class, Integer.class, String.class, String.class};
        boolean[] editable = new boolean[]{false,false,false,false};
        
        //create table from InfoTable
        this.requestTable = new InfoTable(columnID,columnClass,editable,"center");
        
        JScrollPane scrollPane = new JScrollPane(this.requestTable);//make requestTable a scrolling pane 
        scrollPane.setPreferredSize(new Dimension(1000,600));//set scroll pane window size
        
        addRequest();// add request to request table
        
        //JPanel containers for organizing elements 
        JPanel firstRow = new JPanel();
        JPanel secondRow = new JPanel();
        JPanel thirdRow = new JPanel();
        
        //adding labels and text fields to JPanel containers 
        firstRow.add(this.nameL);
        firstRow.add(this.nameTF);
        firstRow.add(this.phoneL);
        firstRow.add(this.phoneTF); 
        firstRow.add(this.descriptionL);
        firstRow.add(this.descriptionTF);
        secondRow.add(scrollPane);
        thirdRow.add(this.totalL);
        thirdRow.add(this.totalTF);
        
        //add and format JPanels to CustomerDetailsGUI parent JFrame class  
        super.add(firstRow, BorderLayout.PAGE_START);
        super.add(secondRow, BorderLayout.CENTER);
        super.add(thirdRow, BorderLayout.PAGE_END);
        
        //set requestTable request and details column preferred size to 300
        requestTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        requestTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        
        //final JFrame settings 
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//when close button is pushed, exit out 
        super.setSize(400, 600);// set JFrame size
        super.pack();
        super.setVisible(true);// display JFrame
        super.setLocationRelativeTo(null);// no preferred location 
    }//END of CustomerDetailGUI construtor 
    
    //--- methods ---
    
    //adds requests to requestTable
    private void addRequest(){
        
        //cycle through each estimate in list
        for(int i = 0; i < this.currentCustomer.getEstimateList().size(); i++){
            
            //add estimate to requestTable in a coverted object array 
            this.requestTable.addRow(this.currentCustomer.getEstimate(i).toObjectArrayShort());
            
        }//END of for loop
        
    }//END of addRequest()
    
    //find total estimate cost 
    private double addTotalEstimate(){
        
        double total = 0.0;
        
        //cycle through Estimate list 
        for(int i = 0; i < this.currentCustomer.getEstimateList().size(); i++){
            
            //add up totals of estimate for a grand total 
            total += this.currentCustomer.getEstimate(i).getTotal(); 
            
        }//END of for loop
        
        return total;
    }//END of addTotalEstimate()
    
    
}//END of CustomerDetailsGUI Class
