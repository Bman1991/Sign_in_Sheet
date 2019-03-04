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

import java.io.*;
import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.ArrayList;
import java.nio.file.*;
import java.awt.print.*;
import java.awt.Graphics.*;



public class SheetTable extends JFrame {
    
    private static InfoTable signInTable;
    private static ArrayList customerList;
    private ArrayList<Estimate> estimateList;
    private static String url;
    private static boolean isUpdating = false;
    private static String requestStr = "";
    
    private static JTextField nameTF;
    private static JTextField phoneTF;
    private static JTextField descriptionTF;
    private static JComboBox<String> requestCB; 
    private static String[] requestList = new String[]{" ","Screen Repair", "Glass Cut", "Pexiglass Cut", 
        "Knife Sharpening", "Scissor Sharpening", "Chainsaw Blade Sharpening", "Mower Blade Sharpening", "Other"};
    
    public SheetTable(){
        
        super("Service Desk Sign-In Sheet"); //pass variables to JFrame(superclass)
        
        customerList = new ArrayList<Customer>(); //create ArrayList of Customers
        estimateList = new ArrayList<Estimate>(); //create ArrayList of Estimates
        Handler handler = new Handler(); //create Handler for menuItems
        
        //-------------------------- Menu Bar ----------------------------------
        
        //--- File Menu ---
        JMenu fileM = new JMenu("File"); //create JMenu "File"
        fileM.setMnemonic('F'); // set Nemonic to 'F' in File
        
        //File Menu Items
        JMenuItem newMI = new JMenuItem("New"); // create JMenuItem "New" 
        newMI.setMnemonic('N'); //set Nemonic to 'N' in New
        newMI.addActionListener(handler); //add Handler ActionListener 
        fileM.add(newMI);// add to File Menu 
        
        JMenuItem openMI = new JMenuItem("Open"); // create JMenuItem "Open"
        openMI.setMnemonic('O'); //set Nemonic to 'O' Open
        openMI.addActionListener(handler); // add Handler ActionListener
        fileM.add(openMI); // add to File Menu 
        
        fileM.addSeparator(); // add Separator to separate "Exit" Menu Item
        
        JMenuItem exitMI = new JMenuItem("Exit"); //create JMenuItem "Exit"
        exitMI.setMnemonic('x'); // set Nemonic 'x' Exit
        exitMI.addActionListener(handler); // add Handler ActionListener
        fileM.add(exitMI); // add to File Menu
        
        //--- Estimate Menu ---
        JMenu estimateM = new JMenu("Estimate"); // creates JMenu "Estimate"
        estimateM.setMnemonic('E'); // set Nemonic 'E' Estimate
        
        //Estimate MenuItems
        JMenuItem screenMI = new JMenuItem("Screen Repair"); // creates JMenuItem "Screen Repair"
        screenMI.setMnemonic('S'); // set Nemonic 'S' Screen Repair
        screenMI.addActionListener(handler); // add Handler Action Listener
        estimateM.add(screenMI); // add to Estimate Menu
        
        JMenuItem pexiMI = new JMenuItem("Pexiglass Cut"); // create JMenuItem "Pexiglass Cut"
        pexiMI.setMnemonic('P'); // set Nemonic 'P' Pexiglass Cut
        pexiMI.addActionListener(handler); // add Handler Action Listener
        estimateM.add(pexiMI); // add to Estimate Menu
        
        JMenuItem glassMI = new JMenuItem("Glass Cut"); // create JMenuItem "Glass Cut"
        glassMI.setMnemonic('G'); // set Nemonic 'G' Glass Cut
        glassMI.addActionListener(handler); // add Handler Action Listener
        estimateM.add(glassMI); // add to Estimate Menu
        
        JMenuItem knifeMI = new JMenuItem("Knife Sharpening"); // create JMenuItem "Knife Sharpening"
        knifeMI.setMnemonic('K'); // set Nemonic 'K' Knif Sharpening
        knifeMI.addActionListener(handler); // add Handler Action Listener
        estimateM.add(knifeMI); // add to Estimate Menu
        
        JMenuItem chainsawMI = new JMenuItem("Chainsaw Sharpening"); //create JMenuItem "Chainsaw Sharpening"
        chainsawMI.setMnemonic('C'); // set Nemonic 'C' Chainsaw Sharpening
        chainsawMI.addActionListener(handler); // add Handler Action Listener
        estimateM.add(chainsawMI); // add to Estimate Menu
        
        JMenuItem scissorMI = new JMenuItem("Scissor Sharpening"); // create JMenuItem "Scissor Sharpening"
        scissorMI.setMnemonic('i'); // set Nemonic 'i' Scissor Sharpening
        scissorMI.addActionListener(handler); // add Handler Action Listener
        estimateM.add(scissorMI); // add to Estimate Menu
        
        JMenuItem mowerMI = new JMenuItem("Mower Blade Sharpening"); //create JMenuItem "Mower Blade Sharpening"
        mowerMI.setMnemonic('M'); // set Nemonic 'M' Mower Blade Sharpening
        mowerMI.addActionListener(handler); // add Handler Action Listener
        estimateM.add(mowerMI); // add to Estimate Menu
        
        
        JMenuBar menuBar = new JMenuBar(); // create Menu Bar 
        setJMenuBar(menuBar); // add Menu Bar to JFrame 
        menuBar.add(fileM); // add File Menu to Menu Bar
        menuBar.add(estimateM);// add Estimate Menu to Menu Bar
        
        //-------------------------- END of Menu Bar ---------------------------
        
        //------------------------ Customer insertion --------------------------
        
        JPanel customerEntryPanel = new JPanel();//container for customer insertion
        
        //--- labels ---
        JLabel nameLabel = new JLabel("Name:");// name
        JLabel phoneLabel = new JLabel("Phone Number: ");// phone number
        JLabel requestLabel = new JLabel("Request: "); // request 
        JLabel descriptionLabel = new JLabel("Description: ");// description
       
        //--- Text Field, ComboBox, and button ---
        nameTF = new JTextField(15);// name text field
        phoneTF = new JTextField(15);// phone number text field 
        requestCB = new JComboBox<String>(requestList); // request combo box 
        descriptionTF = new JTextField(15); // description text field 
        JButton addCustomerB = new JButton("Add Customer");// add buttion 
        JButton deleteCustomerB = new JButton("Delete Customer");// add buttion
        JButton detailsB = new JButton("Get Customer Details");// add buttion
        
        //--- button handler  ---
        ButtonHandler buttonHandler = new ButtonHandler();// create buttonHandler
        
        //--- button action listener ---
        addCustomerB.addActionListener(buttonHandler);// add buttonHandler to "Add Customer" button
        deleteCustomerB.addActionListener(buttonHandler);// add buttonHandler to "Delete Customer" button
        detailsB.addActionListener(buttonHandler);// add buttonHandler to "Get Customer Details" button
        
        //--- Request Combo Box Listener ---
        requestCB.addItemListener(
                
                // new ItemListener
                new ItemListener(){
                    
                    @Override // override item State Changed
                    public void itemStateChanged(ItemEvent i){
                        
                        //if state changed to selecting state
                        if(i.getStateChange() == ItemEvent.SELECTED){
                            
                            //if selecting "Screen Repair"
                            if(i.getItem() == "Screen Repair"){
                                
                                //create and display Screen repair Estimate window
                                ScreenGUI screenGUI = new ScreenGUI(SheetTable.this);
                                
                            }// END of Screen Repair IF
                            
                            //if selecting "Pexiglass Cut"
                            if(i.getItem() == "Pexiglass Cut"){
                                
                                //create and display Pexiglass Estimate window
                                PexiGlassGUI pgGUI = new PexiGlassGUI(SheetTable.this);
                                
                            }//END of Pexglass Cut IF
                            
                            //if selecting "Glass Cut"
                            if(i.getItem() == "Glass Cut"){
                                
                                //create and display Glass cut Estimate window
                                GlassGUI gGUI = new GlassGUI(SheetTable.this);
                            }// END of Glass Cut IF
                            
                            //if selecting anything with word "Sharpening"
                            if(String.valueOf(i.getItem()).contains("Sharpening") == true){ 
                                
                                //create and dispaly sharpening Estimate window
                                SharpeningGUI sharpGUI = new SharpeningGUI(SheetTable.this,String.valueOf(i.getItem())); 
                                
                            }//END of Sharpening IF
                            
                            //set requested string for customer records
                            setRequestString(String.valueOf(i.getItem()));
                            
                        }//END of ItemStateChange IF
                        
                    }//END of ItemStateChange()
        });//END of requestCB ItemListener
        
        //--- phone number key listener ---
        //prevents phone number from being to long 
        phoneTF.addKeyListener(
                new KeyAdapter(){
                
                    @Override //overrides Key Typed 
                    public void keyTyped(KeyEvent k){
                       
                        //if char count is above 11
                        if(phoneTF.getText().length() >= 10){
                            
                            k.consume();// consume future character input 
                             
                        }//END of phone number char length IF 
                        
                    }//END of keyTyped()
                
                });//END of phoneTF KeyListener
        
       //--- adding components in the JPanel container --- 
        
        //add label and text field for customer name
        customerEntryPanel.add(nameLabel);
        customerEntryPanel.add(nameTF);
        
        //add label and text field for phone number
        customerEntryPanel.add(phoneLabel);
        customerEntryPanel.add(phoneTF);
        
        //add label and combo box for service request 
        customerEntryPanel.add(requestLabel);
        customerEntryPanel.add(requestCB);
        
        //add label and text field for description 
        customerEntryPanel.add(descriptionLabel);
        customerEntryPanel.add(descriptionTF);
        
        //add buttons 
        customerEntryPanel.add(addCustomerB);//add customer
        customerEntryPanel.add(deleteCustomerB);//delete customer
        customerEntryPanel.add(detailsB);//details about customer
        
        // add to JFrame and make it fit above table
        add(customerEntryPanel, BorderLayout.PAGE_START);
        
        //------------- END of adding components to JPanel ---------------------
        
        //------------------------- JTable -------------------------------------
        
         //--- sets header ID ---
        String[] columnID = new String[]{"Date", "Name", "Phone","Request", "Description", "Done", "Called"};
        
        //sets column class, so "Done" and "Called" fields can have checkmarks  
        final Class[] columnClass = new Class[]{String.class, String.class, String.class, String.class, String.class, Boolean.class, Boolean.class};// list of column classes
        boolean[] editable = new boolean[]{false,false,false,false,false,true,true};//columns that are editable
        System.out.println("creating Table");
        
        //creates table to be formated correctly using InfoTable Class
        this.signInTable = new InfoTable(columnID,columnClass,editable,"center");
        
        //adds table model listener for ID-ing cells
        //if cell is selected, Detail of customer can be displayed when "Get Customer Details" is pressed
        signInTable.getModel().addTableModelListener(new TableModelListener(){
            
            //listen for any changes in table
            public void tableChanged(TableModelEvent e){
               System.out.println(e.getLastRow());
                
                //ignore following commands if adding customer to table 
                if(isUpdating != true){
                    
                    // get customer from customerList array
                    Customer currentCustomer = (Customer)SheetTable.this.customerList.get(e.getLastRow());  
                    
                    if(e.getColumn() == 5){
                        
                        // set done to new boolean value
                        currentCustomer.setDone((boolean)signInTable.tableModel().getValueAt(e.getLastRow(), e.getColumn()));
                        
                    }//END of Column 5 or "Done" IF
                    
                    if(e.getColumn()== 6){
                        
                        // set called to new boolen value
                        currentCustomer.alreadyCalled((boolean)signInTable.tableModel().getValueAt(e.getLastRow(), e.getColumn()));
                        
                    }//END of Column 6 or "Called" IF
                    
                    
                    // update customer file to new boolean values 
                    try{
                        
                        writeFile(SheetTable.this.url);
                        
                    }//END of "update customer" try
                    catch(IOException ioe){
                        
                        System.out.println(ioe);
                        
                    }//END of "update customer" Catch
                    
                }//END of Table Change IF
                
            }//END of TableChanged()
            
        });//END of TableModelListener 
        
        //set request and description Text Fields preferred width (500)
        signInTable.getColumnModel().getColumn(3).setPreferredWidth(500);
        signInTable.getColumnModel().getColumn(4).setPreferredWidth(500);
        
        //--- Final JTable settings ---
        add(new JScrollPane(this.signInTable));// add JTable into a scrollpane with that is added to the JFrame 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//when close button is pushed, exit out 
        pack();
        setSize(1600, 600);// set JFrame size 
        setVisible(true);// display JFrame
        setLocationRelativeTo(null);//no set location
        
        //------------------------ END of JTable--------------------------------
      
          
    }//END of SheetTable()
    
    // ActionListener for File menu 
     private class Handler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent a){
            
            // if "New" is selected 
            if(a.getActionCommand() == "New"){
                
                File file = SheetTable.this.getFile(2); // get file URL from filechooser  
                //JOptionPane.showMessageDialog(SheetTable.this, file.toString(),file.toString(),-1); // display file URL in dialog box 
                //SheetTable.this.url = file.toString(); // set url variable to file URL
                
                file.toPath();
                
                SheetTable.this.url = file.toString(); // set url variable to file URL
                
                //write new customer file 
                try{
                    writeEmptyFile(SheetTable.this.url);
                }//END of "write new customer file" try
                catch(IOException e){
                    
                    System.out.println(e);
                    
                }//END of "write new customer file" catch
                
            }//END of "New" action command IF
            
            // if "Open" is selected 
            if(a.getActionCommand() == "Open"){
                
                File file = SheetTable.this.getFile(1); // get file URL from filechooser  
               
                //if file exists 
                if(file.exists()){
                    
                    //JOptionPane.showMessageDialog(SheetTable.this, file.toString(),file.getParent(),-1); // show file URL in dialog box 
                    SheetTable.this.url = file.toString(); // set url varable to file URL
                   
                    // open file, get customer info, add to table 
                    try{
                        //if table is displaying customers
                        if(signInTable.tableModel().getRowCount() > 0 ){
                            
                            clearTable(); // clear table 
                            
                        }//END of table row count IF
                        
                        // read customer info and add to customer list 
                        readFile(SheetTable.this.url);
                        
                    }//END of "Open custoemr file" try
                    catch(IOException e){
                        
                        System.out.println(e);
                        addCustomer(); // adds customers to table from customer list 
                    
                    }//END of "Open customer file" IO catch
                    catch (ClassNotFoundException cnfe){
                        
                        System.out.println(cnfe);
                        
                    }//END of "Open customer file" ClassNotFound catch
                    
                }//END of file exists IF
                
            }//END of "Open" action command IF
            
            // if "Exit" is selected
            if(a.getActionCommand() == "Exit"){
                
                // exit out of program
                System.exit(0); 
                
            }//END of "Exit" IF
            
            //--- Estimate action commands ---
            
            //if "Screen Repair" is selected
            if(a.getActionCommand() == "Screen Repair"){
                
                //create and display screen repair estimate window
                ScreenGUI sr = new ScreenGUI(SheetTable.this);
                sr.EstimateMode();//enter estimate mode
            }
            if(a.getActionCommand() == "Pexiglass Cut"){
                
                //create and display pexiglass cut estimate window
                PexiGlassGUI pg = new PexiGlassGUI(SheetTable.this);
                pg.EstimateMode();//enter estimate mode
            }
            if(a.getActionCommand() == "Glass Cut"){
                
                //create and display glass cut estimate window
                GlassGUI g = new GlassGUI(SheetTable.this);
                g.EstimateMode();//enter estimate mode
            }
            if(a.getActionCommand().contains("Sharpening") == true){
                
                //create and display sharpening estimate window
                SharpeningGUI s = new SharpeningGUI(SheetTable.this,a.getActionCommand());
                s.EstimateMode();//enter estimate mode
            }
            
        }
        
    }
     
    //Button Handler 
    private class ButtonHandler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent a){
           
            //if "Add Customer" button is pressed
           if(a.getActionCommand() == "Add Customer"){
                try{
                //get info from text field and combo box 
                String cName = nameTF.getText();
                String cPhone = formatPhoneNumber(phoneTF.getText());// format phone number 
                String cRequest = SheetTable.this.requestStr;
                String cDescription = descriptionTF.getText(); 
                   
                //create new customer 
                Customer c = new Customer(cName,cPhone,cRequest,cDescription,SheetTable.this.estimateList);
                
                //add new customer to list 
                SheetTable.this.customerList.add(c);
                
                //reset text fields and combo box 
                reset();
                
                // update table and file 
                SheetTable.this.writeFile(SheetTable.this.url);
                SheetTable.this.clearTable();
                SheetTable.this.readFile(SheetTable.this.url);
                
                
            }//END of "Add Customer" try
                
            catch(IOException e){
                
                System.out.println(e);
                addCustomer();// add customer to table 
            }//END of "Add Customer" IO catch
            catch(ClassNotFoundException cnfe){
                
                System.out.println(cnfe);
                
            }//END of "Add Customer" ClassNotFound catch
            
            //clear estimate list for next customer 
            SheetTable.this.estimateList.clear();
            
           }//END of "Add Customer" action command IF
           
           //if "Delete Customer" is pressed and a customer is selected
           if(a.getActionCommand() == "Delete Customer" && signInTable.getSelectedRow() != -1){
               
               deleteCustomer();//delete customer 
               
           }//END of "Delete Customer" action command IF
           
           //if "Get Customer Details" pressed
           if(a.getActionCommand() == "Get Customer Details"){
               
               //get details of selected customer
                detailsOfCustomer((Customer)SheetTable.this.customerList.get(signInTable.getSelectedRow()));
           }//END of "Get Customer Details" action command IF
           
        }//END of button action listener 
        
}//END of ButtonHandler()
    
    // adds customer info in the table dynamically 
    public void addCustomer(){
        
        System.out.println(customerList.size());
        
        isUpdating = true; // enable ignore tableChange commands 
        
        //add customers to table 
        for(int i = 0; i < customerList.size(); ++i){
            
            Customer c = (Customer)customerList.get(i); // get customer from list 
            
            signInTable.addRow(c.toObjectArray()); // add customer to table in new row
        }//END of for loop
        
        isUpdating = false;// disable ignore tableChange commands
        
    }//END of addCustomer()
    
    // delete customer info in table dynamically 
    public void deleteCustomer(){
        
        isUpdating = true;// enable ignore tableChange commands
       
        this.customerList.remove(signInTable.deleteRow()); // remove customer from customer list and table 
        
        //update customer file 
        try{
            writeFile(this.url);// write customer file 
            
        }//END of deleteCustomer() try
        
        catch(IOException e){
            
            System.out.println(e);
        }//END of deleteCustomer() IO catch 
        
        isUpdating = false;// disable ignore tableChange commands
    }//END of deleteCusotmer()
    
    //get customer details 
    public void detailsOfCustomer(Customer c){
        
        //create and display customer details window
        CustomerDetailsGUI cdGUI = new CustomerDetailsGUI(c); 
       
    }//END of detailofCustomer()
    
    // read from file 
    public void readFile(String s) throws IOException, ClassNotFoundException{
        
        this.customerList.clear(); // get new customer list 
        System.out.println(customerList.size());
        FileInputStream f = new FileInputStream(s); // create file input stream 
        ObjectInputStream in = new ObjectInputStream(f);// create object input stream 
        
        Customer c = null;// make customer object null 
        
        //while their is still more to read 
        while(in != null){
            
            c = (Customer)in.readObject(); // read object from file 
            System.out.println(c);
            this.customerList.add(c); // add customer object to customer list 
            
        }//END of while loop
        
    }//END of readFile()
    
    //write to file 
    public void writeFile(String s) throws IOException{
        
        System.out.println(s);
        FileOutputStream f = new FileOutputStream(s);// create file input stream //"C:/Users/Brian/Documents/NetBeansProjects/SignInSheet/Worksheet.txt"
        ObjectOutputStream out = new ObjectOutputStream(f); // create object input stream 
        
        // write to file from customer list 
        for(int i = 0; i < customerList.size(); ++i){
               
            out.writeObject(customerList.get(i)); // write customer objects to file  
               
        }//END of for loop
           
           out.close();// close file 
        
        
    }//END of writeFile()
    
    //create empty file
    public void writeEmptyFile(String s) throws IOException{
        
        ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(s)));
        
        out.writeObject(new Customer());//write in a empty customer object
        
        out.close();//close file
        
    }//END of writeEmptyFile()
    
    //clear table 
    public void clearTable(){
        
        isUpdating = true;// enable ignore tableChange commands
        System.out.println("Clearing table");
        
        //while there is still customers in table
        while(signInTable.tableModel().getRowCount() != 0){
            
            //remove customer
            signInTable.tableModel().removeRow(0);
            
        }//END of while loop
        System.out.println("Cleared");
        
        isUpdating = false;// disable ignore tableChange commands
    }//END of clearTable()
   
    // get file URL from filechooser 
    public File getFile(int n){
        
        File file;// file object 
        JFileChooser fileChooser = new JFileChooser();// create JFileChooser
        fileChooser.setFileSelectionMode(0);// set file chooser mode to files only
        
        //displays open dialog box if n = 1 or save dialog box if n = 2 
        int n2 = n == 1 ? fileChooser.showOpenDialog(this) : fileChooser.showSaveDialog(this);
        
        //if n2 = 1, exit program
        if(n2 == 1){
            
            System.exit(1);
            
        }//END of n2 IF
        
        //if file is null or filename is blank
        if((file = fileChooser.getSelectedFile()) == null || file.getName().equals("")){
            
            //display error message
            JOptionPane.showMessageDialog(this, "Invalid File Name", "Invalid File Name", 0);
            System.exit(1);
            
        }//END of fileChooser error IF
        
        return file; 
    }//END of getFile()
    
    //format phone number
    public String formatPhoneNumber(String p){
        
        //create empty string 
        String s = ""; 
        
        //add dash where its needed
        s = p.substring(0,3) + "-" + p.substring(3, 6) + "-" + p.substring(6, 10);
        
        return s; // return formmatted phone number. 
    }//END of formatPhoneNumber
    
    //pass estimates details to estimateList
    public void passDetails(Estimate e){
        
        System.out.println(e);
        
        //if estimate is not yet entered
        if(this.estimateList.contains(e) == false){
            
            this.estimateList.add(e);//add estimate
            
        }//END of IF
        
        
    }//END of passDetails()
    
    //sets requests in a single string
    private void setRequestString(String s){
        
        //if requestStr is not blank 
        if(this.requestStr != ""){
           
            //if request is not yet in requestStr
            if(this.requestStr.contains(s) == false){ 
              
                this.requestStr += ", " + s;//add request to end of string
                
            }//END of requestSrt.contains IF
        }//END of requestStr blank IF
        else{
            
            this.requestStr = s;//add string to already blank string
            
        }//END of else
        
    }//END of setRequestString()
    
    //rest text fields and combo boxs 
    private void reset(){
        
        //set everything to blank state
        this.nameTF.setText("");
        this.phoneTF.setText("");
        this.requestCB.setSelectedIndex(0);
        this.descriptionTF.setText("");
        this.requestStr = ""; 
        
    }//END of reset()
    
}//END of SheetTable Class