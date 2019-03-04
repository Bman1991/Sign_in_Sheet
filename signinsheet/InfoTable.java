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

//InfoTable Class
public class InfoTable extends JTable{
    
    private DefaultTableModel tableModel;// table model 
    private DefaultTableCellRenderer cellRenderer;//renders cells in table 
    private String[] columnID;//list of column IDs or headers 
    private Class[] columnClass;//list of column class
    private boolean[] editable;// list of editable columns 
    
    //constructor with provided data 
    public InfoTable(String[] cID, Class[] cClass, boolean[] cEdit, String cAlign){
        
        super();//initialize parent class 
        
        this.columnID = cID;// list of column IDs or headers 
        this.columnClass = cClass;//list of column class
        this.editable = cEdit;// list of editable columns 
        this.cellRenderer = new DefaultTableCellRenderer();// cell render using default 
        System.out.println("setting model");
        
        //set table model 
        this.tableModel = new DefaultTableModel(0,0){
            
            //set editable columns 
            @Override
            public boolean isCellEditable(int row, int column){
                
                return InfoTable.this.editable[column];
                
            }//END of isCellEditable() 
            
            //get column classes 
            @Override
            public Class<?> getColumnClass(int columnIndex){
                
                return InfoTable.this.columnClass[columnIndex];
                
            }//END of getColumnClass()
        };//END of tableModel()
       
        System.out.println("setting headers");
        
        //set column IDs or headers 
        this.tableModel.setColumnIdentifiers(this.columnID);
        
        //set cell alignment
        this.cellRenderer.setHorizontalAlignment(alignment(cAlign));
        
        //cycle through column classes to set column classes 
        for(int i = 0; i < columnClass.length; i++){
            
            if(this.columnClass[i] != Boolean.class){
               
                super.setDefaultRenderer(this.columnClass[i], this.cellRenderer);
                
            }//END of IF
            
            
        }//END of for loop 
        
        super.setModel(this.tableModel);// set parent class to use table model
        System.out.println("Table Ready");
    }// END of InfoTable Class 
    
    //converts string to int for alignment setting
    public int alignment(String s){
        
        int x = 0; 
        
        if(s == "center"){
            x = 0;
        }//END of center IF
        
        if(s == "left"){
            x = 2;
        }//END of left IF
        
        if(s == "right"){
            x = 4; 
        }//END of right IF
        
        return x;
    }//END of alignment 
    
    //addes row to table 
    public void addRow(Object[] objArr){
        
        this.tableModel.addRow(objArr);
        
    }//END of addRow()
    
    //delete row in from table 
    public int deleteRow(){
        int row = super.getSelectedRow();//get selected row 
        this.tableModel.removeRow(super.getSelectedRow());// remove selected row 
        return row;// return row 
    }//END of deleteRow()
    
    //update cell 
    public Object updateCell(int x, int y){
        
        //get cell location 
        return this.tableModel.getValueAt(x,y);
        
    }//END of updateCell()
    
    // get table model
    public DefaultTableModel tableModel(){
        
        return this.tableModel; 
        
    }//END of tableModel()
   
   
}//END of InfoTable()
