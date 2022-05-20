import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BikeHireGUI extends JFrame implements ActionListener{

    //arraylist for bike objects
    ArrayList<Bike> bikeList = new ArrayList<Bike>();

    //arraylist for customer objects
    ArrayList<Customer> customerList = new ArrayList<Customer>();

    //arraylist for rent
    ArrayList<Rent> rentList = new ArrayList<Rent>();

    private final String[] bikeTypes = new String[]{"", "Road Bike", "Mountain Bike", "City Bike", "BMX Bike", "eBike"};
    private final String[] motorTypes = new String[]{"Full Power", "Power Assist"};
    private String[] bikeIDType;
    private int bikeIndex, customerIndex;

    private DefaultTableModel bikeTableModel, custTableModel, rentTableModel;

    //GUI
    JTabbedPane jtab;
    private JLabel lbEmpty,lbHome, lbLeon;
    private JPanel panHome, panReadWrite;
    private JButton btnReadData, btnSaveData;
    //Bike Panel Variables
    private JPanel panBike, panBikeList, panAddBike, panSelectBike;
    private JLabel lbBikePrice, lbBikeType, lbBikeMotor, lbBikeDist, lbSelBikeType, lbSelBikePrice, lbSelBikeMotor, lbSelBikeDist, lbSelBikeID;
    private JTable bikeTable;
    private JTextField txtBikeDist, txtBikePrice, txtSelBikeDist, txtSelBikePrice, txtSelBikeMotor, txtSelBikeType;
    private JComboBox cbxBikeType, cbxBikeMotor, cbxSelBikeType;
    private JButton btnAddBike, btnSelectBike;
    private String strSelBikeDist, strSelBikePrice, strSelBikeMotor, strSelBikeType;
    //Customer Panel Variables
    private JPanel panCust, panCustList, panAddCust, panSelectCustomer;
    private JLabel lbCustID, lbCustName, lbCustPhone, lbSelCustID, lbSelCustName, lbSelCustPhone, lbSelCustRent;
    private JTable custTable;
    private JTextField txtCustID, txtCustName, txtCustPhone, txtSelCustID, txtSelCustName, txtSelCustPhone, txtSelCustRent;
    private JButton btnAddCust, btnSelectCust;
    private String strSelCustID, strSelCustName, strSelCustPhone, strSelCustRent;
    //Rental Panel Variables
    private JPanel panRent, panRentList, panAddRent, panRentVariables, panRentSelBike, panRentSelCust;
    private JLabel lbRentDate, lbRentDuration, lbSelCustID2, lbSelCustName2, lbSelCustPhone2, lbSelCustRent2,
            lbSelBikeType2, lbSelBikePrice2, lbSelBikeMotor2, lbSelBikeDist2, lbSelBikeID2;
    private JTable rentTable;
    private JTextField txtRentDate, txtRentDuration, txtSelCustID2, txtSelCustName2, txtSelCustPhone2, txtSelCustRent2,
            txtSelBikeDist2, txtSelBikePrice2, txtSelBikeMotor2, txtSelBikeType2;
    private JButton btnAddRent, btnReturnRent;
    private JCheckBox chkbxOverdueRent;

    //Bike Hire GUI
    private BikeHireGUI() {
        //JTabs
        jtab = new JTabbedPane(SwingConstants.TOP);
        add(jtab, BorderLayout.CENTER);
        panHome = new JPanel();
        jtab.add("Bike Hire", panHome);
        panBike = new JPanel();
        jtab.add("Bikes", panBike);
        panCust = new JPanel();
        jtab.add("Customer", panCust);
        panRent = new JPanel();
        jtab.add("Rent", panRent);


        //Initialises JPanels
        homePanel();
        bikePanel();
        customerPanel();
        rentPanel();
    }

    //Panel For Home JTab
    private void homePanel() {
        //Initialise Variables
        panReadWrite = new JPanel();
        btnReadData = new JButton("Read Data");
        btnSaveData = new JButton("Save Data");
        lbHome = new JLabel("Bike Hire", SwingConstants.CENTER);
        lbLeon = new JLabel("Leon Ngo - S3813566", SwingConstants.CENTER);
        lbHome.setFont(new Font("Arial",Font.BOLD,50));

        //Read and Write Buttons
        panReadWrite.add(btnReadData);
        btnReadData.addActionListener(this);
        panReadWrite.add(btnSaveData);
        btnSaveData.addActionListener(this);

        //Panel to layout home
        panHome.setLayout(new GridLayout(3,1));
        panHome.add(lbHome);
        panHome.add(panReadWrite);
        panHome.add(lbLeon);
    }

    //Panel For Bike JTab
    private void bikePanel() {
        //Table Variables
        String[] col = {"ID", "Type", "Price", "Available"};
        bikeTableModel = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Creates a panel to store the bike table
        panBikeList = new JPanel();
        panBikeList.setLayout(new BorderLayout());
        panBikeList.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnSelectBike = new JButton("Select Bike");
        btnSelectBike.addActionListener(this);

        bikeTable = new JTable(bikeTableModel); //Creates a table for bikes
        panBikeList.add(new JScrollPane(bikeTable), BorderLayout.CENTER); //Creates scrolling capabilities
        panBikeList.add(btnSelectBike, BorderLayout.SOUTH);
        bikeTableModel.setRowCount(0);

        updateBikeTable();


        //Select Bike Variables
        lbSelBikeType = new JLabel("Bike Type");
        lbSelBikePrice = new JLabel("Price Per Day");
        lbSelBikeMotor = new JLabel("Motor Type");
        lbSelBikeDist = new JLabel("Bike Distance");
        lbSelBikeID = new JLabel("Bike Type");
        txtSelBikeType = new JTextField(strSelBikeType);
        txtSelBikeDist = new JTextField(strSelBikeDist);
        txtSelBikePrice = new JTextField(strSelBikePrice);
        txtSelBikeMotor = new JTextField(strSelBikeMotor);
        lbEmpty = new JLabel("");

        //"Select Bike" Panel
        panSelectBike = new JPanel();

        createSelectPanel(panSelectBike,lbSelBikeType,lbSelBikePrice,lbSelBikeMotor,lbSelBikeDist,
                txtSelBikeType,txtSelBikePrice,txtSelBikeMotor,txtSelBikeDist, "Selected Bike");

        lbSelBikeMotor.setVisible(false);
        txtSelBikeMotor.setVisible(false);
        lbSelBikeDist.setVisible(false);
        txtSelBikeDist.setVisible(false);

        //"Add Bike" Variables
        lbBikeType = new JLabel("Bike Type");
        lbBikePrice = new JLabel("Price Per Day");
        lbBikeMotor = new JLabel("Motor Type");
        lbBikeDist = new JLabel("Bike Distance");
        lbEmpty = new JLabel("");
        txtBikeDist = new JTextField();
        txtBikePrice = new JTextField();
        cbxBikeType = new JComboBox<>(bikeTypes);
        cbxBikeMotor = new JComboBox<>(motorTypes);
        btnAddBike = new JButton("Add Bike");

        //"Add Bike" Panel
        panAddBike = new JPanel();

        TitledBorder title;
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 1), "Add Bike");
        title.setTitleJustification(TitledBorder.CENTER);
        panAddBike.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), title));
        panAddBike.setLayout(new GridLayout(5, 2, 10, 10));

        //Bike Type Combo Box And Label
        panAddBike.add(lbBikeType);
        panAddBike.add(cbxBikeType);
        cbxBikeType.addActionListener(this);

        //Bike Price Text Box and Label
        panAddBike.add(lbBikePrice);
        panAddBike.add(txtBikePrice);

        //Bike Motor Combo Box And Label
        panAddBike.add(lbBikeMotor).setVisible(false);
        panAddBike.add(cbxBikeMotor).setVisible(false);

        //Bike Distance Text Box and Label
        panAddBike.add(lbBikeDist).setVisible(false);
        panAddBike.add(txtBikeDist).setVisible(false);

        //Button To Add Bike
        panAddBike.add(lbEmpty);
        panAddBike.add(btnAddBike);
        btnAddBike.addActionListener(this);



        //Creates a layout to add individual panels in
        panBike.setLayout(new GridLayout(3, 1));
        panBike.add(panBikeList); //Adds bike table to the panel
        panBike.add(panSelectBike);
        panBike.add(panAddBike);

    }

    //Panel For Customer JTab
    private void customerPanel() {
        //Table Variables
        String[] col = {"ID", "Name", "Phone", "Currently Renting"};
        custTableModel = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Creates a panel to store the customer table
        panCustList = new JPanel();
        panCustList.setLayout(new BorderLayout());
        panCustList.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnSelectCust = new JButton("Select Customer");
        btnSelectCust.addActionListener(this);

        custTable = new JTable(custTableModel); //Creates a table for bikes
        panCustList.add(new JScrollPane(custTable), BorderLayout.CENTER); //Creates scrolling capabilities
        panCustList.add(btnSelectCust, BorderLayout.SOUTH);
        custTableModel.setRowCount(0);

        updateCustTable();

        //Select Customer Variables
        lbSelCustID = new JLabel("Customer ID");
        lbSelCustName = new JLabel("Name");
        lbSelCustPhone = new JLabel("Phone");
        lbSelCustRent = new JLabel("Currently Renting");
        txtSelCustID = new JTextField(strSelCustID);
        txtSelCustName = new JTextField(strSelCustName);
        txtSelCustPhone = new JTextField(strSelCustPhone);
        txtSelCustRent = new JTextField(strSelCustRent);

        //"Select Customer" Panel
        panSelectCustomer = new JPanel();
        TitledBorder title;

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 1), "Customer Chosen");
        title.setTitleJustification(TitledBorder.CENTER);
        panSelectCustomer.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), title));
        panSelectCustomer.setLayout(new GridLayout(4,2,10,10));


        createSelectPanel(panSelectCustomer,lbSelCustID,lbSelCustName,lbSelCustPhone,lbSelCustRent,
                txtSelCustID,txtSelCustName,txtSelCustPhone,txtSelCustRent, "Selected Customer");

        //"Add Customer" Variables
        lbCustName = new JLabel("Customer Name");
        lbCustPhone = new JLabel("Customer Phone");
        lbEmpty = new JLabel("");
        txtCustName = new JTextField();
        txtCustPhone = new JTextField();
        btnAddCust = new JButton("Add Customer");

        //"Add Customer" Panel
        panAddCust = new JPanel();

        createInputPanel("Add Customer", panAddCust, lbCustName, lbCustPhone, txtCustName, txtCustPhone, btnAddCust);

        //Creates a layout to add individual panels in
        panCust.setLayout(new GridLayout(3, 1));
        panCust.add(panCustList); //Adds bike table to the panel
        panCust.add(panSelectCustomer);
        panCust.add(panAddCust);

    }

    //Panel For Rent JTab
    private void rentPanel() {
        //Table Variables
        String[] col = {"ID", "Bike", "Customer", "Start Date", "End Date"};
        rentTableModel = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Creates a panel to store the rent table
        panRentList = new JPanel();
        panRentList.setLayout(new BorderLayout());
        panRentList.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnReturnRent = new JButton("Return Rent");
        btnReturnRent.addActionListener(this);

        chkbxOverdueRent = new JCheckBox("Show Overdue Rents");
        chkbxOverdueRent.addActionListener(this);

        rentTable = new JTable(rentTableModel); //Creates a table for bikes
        panRentList.add(chkbxOverdueRent, BorderLayout.NORTH);
        panRentList.add(new JScrollPane(rentTable), BorderLayout.CENTER); //Creates scrolling capabilities
        panRentList.add(btnReturnRent, BorderLayout.SOUTH);
        rentTableModel.setRowCount(0);

        updateRentTable();

        //Bike Variables
        lbSelBikeType2 = new JLabel("Bike Type");
        lbSelBikePrice2 = new JLabel("Price Per Day");
        lbSelBikeMotor2 = new JLabel("Motor Type");
        lbSelBikeDist2 = new JLabel("Bike Distance");
        lbSelBikeID2 = new JLabel("Bike Type");
        txtSelBikeType2 = new JTextField(strSelBikeType);
        txtSelBikeDist2 = new JTextField(strSelBikeDist);
        txtSelBikePrice2 = new JTextField(strSelBikePrice);
        txtSelBikeMotor2 = new JTextField(strSelBikeMotor);
        lbEmpty = new JLabel("");

        //Customer Variables
        lbSelCustID2 = new JLabel("Customer ID");
        lbSelCustName2 = new JLabel("Name");
        lbSelCustPhone2 = new JLabel("Phone");
        lbSelCustRent2 = new JLabel("Currently Renting");
        txtSelCustID2 = new JTextField(strSelCustID);
        txtSelCustName2 = new JTextField(strSelCustName);
        txtSelCustPhone2 = new JTextField(strSelCustPhone);
        txtSelCustRent2 = new JTextField(strSelCustRent);

        panRentSelBike = new JPanel();
        panRentSelCust = new JPanel();
        panRentVariables = new JPanel();

        //Panel for selected bike and customer
        panRentVariables.setLayout(new GridLayout(1,2,10,10));

        //Selected Customer Panel
        createSelectPanel(panRentSelBike,lbSelBikeType2,lbSelBikePrice2,lbSelBikeMotor2,lbSelBikeDist2,
                txtSelBikeType2,txtSelBikePrice2,txtSelBikeMotor2,txtSelBikeDist2, "Selected Bike");

        //Selected Bike Panel
        createSelectPanel(panRentSelCust,lbSelCustID2,lbSelCustName2,lbSelCustPhone2,lbSelCustRent2,
                txtSelCustID2,txtSelCustName2,txtSelCustPhone2,txtSelCustRent2, "Selected Customer");

        panRentVariables.add(panRentSelBike);
        panRentVariables.add(panRentSelCust);

        //"Add Rent" Variables
        panAddRent = new JPanel();
        lbRentDuration = new JLabel("Rent Duration (Max 30 Days)");
        lbRentDate = new JLabel("Date");
        txtRentDuration = new JTextField();
        txtRentDate = new JTextField();
        btnAddRent = new JButton("Add Rent");

        //Add Rent Panel
        createInputPanel("Add Rent", panAddRent, lbRentDuration, lbRentDate,
                txtRentDuration, txtRentDate, btnAddRent);
        txtRentDate.setEditable(false);
        txtRentDate.setVisible(false);
        lbRentDate.setVisible(false);

        panRent.setLayout(new GridLayout(3,1));
        panRent.add(panRentVariables);
        panRent.add(panAddRent);
        panRent.add(panRentList);
    }

    //Verifies integers only from String
    private boolean verifyInteger(String string){
        Pattern patternDigits = Pattern.compile("^\\d+$");
        Matcher matcherPhone = patternDigits.matcher(string);
        return matcherPhone.matches();
    }

    //Verifies double (integers and decimals only) from String
    private boolean verifyDouble(String string){
        Pattern patternDigits = Pattern.compile("^[0-9]*\\.?[0-9]+$");
        Matcher matcherPhone = patternDigits.matcher(string);
        return matcherPhone.matches();
    }

    //Verifies characters only from String
    private boolean verifyChar(String string){
        Pattern patternChar = Pattern.compile("[a-zA-Z]+");
        Matcher matcherName = patternChar.matcher(string);
        return matcherName.matches();
    }

    //Method to create input panel with two variables
    public void createInputPanel(String borderTitle, JPanel panel, JLabel label1, JLabel label2,
                                 JTextField textField, JTextField textField2, JButton button) {

        //Empty space placeholder
        JLabel empty1 = new JLabel("");
        JLabel empty2 = new JLabel("");
        JLabel empty3 = new JLabel("");
        JLabel empty4 = new JLabel("");
        JLabel empty5 = new JLabel("");

        //Border
        TitledBorder title;
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 1), borderTitle);
        title.setTitleJustification(TitledBorder.CENTER);
        panel.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), title));
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        //Variable 1 Text Box and Label
        panel.add(label1);
        panel.add(textField);

        //Variable 2 Text Box and Label
        panel.add(label2);
        panel.add(textField2);

        panel.add(empty1);
        panel.add(empty2);
        panel.add(empty3);

        //Button
        panel.add(empty4);
        panel.add(empty5);
        panel.add(button);
        button.addActionListener(this);
    }

    //Method to create selected item panel with four variables
    private void createSelectPanel(JPanel panel, JLabel label1, JLabel label2, JLabel label3, JLabel label4,
                                   JTextField txtField1, JTextField txtField2, JTextField txtField3,
                                   JTextField txtField4, String borderTitle) {

        //Title
        TitledBorder title;
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 1), borderTitle);
        title.setTitleJustification(TitledBorder.CENTER);

        panel.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), title));
        panel.setLayout(new GridLayout(4,1,10,10));

        //Variable 1 Label and TextBox
        panel.add(label1);
        panel.add(txtField1);
        txtField1.setEditable(false);

        //Variable 2 Label and TextBox
        panel.add(label2);
        panel.add(txtField2);
        txtField2.setEditable(false);

        //Variable 3 Label and TextBox
        panel.add(label3);
        panel.add(txtField3);
        txtField3.setEditable(false);

        //Variable 4 Label and TextBox
        panel.add(label4);
        panel.add(txtField4);
        txtField4.setEditable(false);
    }

    //Method to add a bike
    private void addBike() {
        String bikeTypeSelectedItem = (String) cbxBikeType.getSelectedItem();
        String bikePrice = txtBikePrice.getText();
        String bikeDist = txtBikeDist.getText();
        double price = Double.parseDouble(bikePrice);

        //IF statement: Checks whether bike chosen is eBike
        if (bikeTypeSelectedItem.equals("eBike") && verifyDouble(bikePrice) && verifyInteger(bikeDist)) {

            String bikeMotorSelectedItem = (String) cbxBikeMotor.getSelectedItem();
            int dist = Integer.parseInt(bikeDist);
            eBike ebike;

            //IF statement: Checks motor strength of selected eBike, creates eBike object and adds to arraylist
            if (bikeMotorSelectedItem.equals("Full Power")) {
                ebike = new eBike(price, true, bikeTypeSelectedItem, dist, true);
            } else {
                ebike = new eBike(price, true, bikeTypeSelectedItem, dist, false);
            }
            bikeList.add(ebike);
            cbxSelBikeType.addItem(ebike.getBikeID() + " - " + ebike.getType());

            //IF Statement: If not eBike then create a bike object and adds to arraylist
        } else if (verifyDouble(bikePrice) && !bikeTypeSelectedItem.equals("eBike") && !bikeTypeSelectedItem.equals("")) {
            Bike bike = new Bike(price, true, bikeTypeSelectedItem);
            bikeList.add(bike);
            cbxSelBikeType.addItem(bike.getBikeID() + " - " + bike.getType());
        } else if (bikeTypeSelectedItem.equals("")){
            JOptionPane.showMessageDialog(null,"Please choose a bike type!");
        }
        updateBikeTable();
    }

    //Method to add a customer
    private void addCustomer() {
        String custName = txtCustName.getText();
        String custPhone = txtCustPhone.getText();

        //IF Statement: Checks for valid phone and customer name, creates customer object and inserts into arraylist
        if(verifyInteger(custPhone) && verifyChar(custName)) {
            Customer customer = new Customer(custName, custPhone);
            customerList.add(customer);
        } else if(verifyChar(custName)){
            JOptionPane.showMessageDialog(null,"Invalid Phone Number!");
        } else{
            JOptionPane.showMessageDialog(null,"Invalid Name!");
        }
        updateCustTable();
    }

    //Method to update textboxes with bike variables when selecting a bike
    private void selectBike(){
        int row = bikeTable.getSelectedRow();
        Object ob = bikeTable.getModel().getValueAt(row,0);
        int bikeID = (Integer) ob;
        for (Bike b: bikeList) {
            if ((b.getBikeID() == bikeID) && b.getAvailability()) {
                if (b instanceof eBike) {
                    strSelBikeType = b.getType();
                    strSelBikePrice = Double.toString(b.getPricePerDay());
                    strSelBikeMotor = ((eBike) b).getMotorStrengthString();
                    strSelBikeDist = Integer.toString(((eBike) b).getMaximumDistance());

                    txtSelBikeType.setText(strSelBikeType);
                    txtSelBikePrice.setText("$" + strSelBikePrice);
                    txtSelBikeMotor.setText(strSelBikeMotor);
                    txtSelBikeDist.setText(strSelBikeDist + " KM");

                    txtSelBikeType2.setText(strSelBikeType);
                    txtSelBikePrice2.setText("$" + strSelBikePrice);
                    txtSelBikeMotor2.setText(strSelBikeMotor);
                    txtSelBikeDist2.setText(strSelBikeDist + " KM");

                    txtSelBikeMotor.setVisible(true);
                    txtSelBikeDist.setVisible(true);
                    lbSelBikeMotor.setVisible(true);
                    lbSelBikeDist.setVisible(true);
                } else {
                    strSelBikeType = b.getType();
                    strSelBikePrice = Double.toString(b.getPricePerDay());
                    txtSelBikeType.setText(strSelBikeType);
                    txtSelBikePrice.setText(strSelBikePrice);
                    txtSelBikeType2.setText(strSelBikeType);
                    txtSelBikePrice2.setText(strSelBikePrice);

                    txtSelBikeMotor.setVisible(false);
                    txtSelBikeDist.setVisible(false);
                    lbSelBikeMotor.setVisible(false);
                    lbSelBikeDist.setVisible(false);
                }
            }
            bikeIndex = bikeList.indexOf(b);
        }
    }

    //Method to update textboxes with customer variables when selecting a bike
    private void selectCustomer(){
        int row = custTable.getSelectedRow();
        Object ob = custTable.getModel().getValueAt(row,0);
        int custID = (Integer) ob;
        for (Customer c: customerList) {
            if ((c.getID() == custID) && !c.isCurrentlyRenting()) {
                strSelCustID = Integer.toString(c.getID());
                strSelCustName = c.getName();
                strSelCustPhone = c.getPhoneNumber();
                strSelCustRent = c.isCurrentlyRentingStr();

                txtSelCustID.setText(strSelCustID);
                txtSelCustName.setText(strSelCustName);
                txtSelCustPhone.setText(strSelCustPhone);
                txtSelCustRent.setText(strSelCustRent);

                txtSelCustID2.setText(strSelCustID);
                txtSelCustName2.setText(strSelCustName);
                txtSelCustPhone2.setText(strSelCustPhone);
                txtSelCustRent2.setText(strSelCustRent);
            }
            customerIndex = customerList.indexOf(c);
        }
    }

    //Method to add a bike and customer to rent
    private void addRent() {
        String duration = txtRentDuration.getText();
        int durationInt = Integer.parseInt(duration);
        boolean bikeAvailable = bikeList.get(bikeIndex).getAvailability();
        boolean customerIsRenting = customerList.get(customerIndex).isCurrentlyRenting();

        //IF Statement: Validates rent duration, customer availability and bike availability, creates rent object
        //IF Statement: and adds to rent arraylist
        if(durationInt > 0 && durationInt < 31 && bikeAvailable && !customerIsRenting) {
            Rent rent = new Rent(LocalDate.now(), durationInt, customerIndex, bikeIndex);
            bikeList.get(bikeIndex).setAvailability(false);
            customerList.get(customerIndex).setCurrentlyRenting(true);
            rentList.add(rent);
        } else if(bikeAvailable && !customerIsRenting){
                JOptionPane.showMessageDialog(null,"Invalid Duration!");
        }
        updateRentTable();
        updateCustTable();
        updateBikeTable();
    }

    //Method to return a bike rental
    private void returnRent() {
        //Grabs selected rent ID variable on JTable
        int row = rentTable.getSelectedRow();
        Object ob = rentTable.getModel().getValueAt(row, 0);
        int rentID = (Integer) ob;
        int count = 0;
        int index = -1;

        //FOR loop: Iterates through rent arraylist for rent ID variable and resets customer and bike availability
        for (Rent r: rentList) {
            System.out.println(r.getRentID());
                if(r.getRentID() == rentID) {
                    customerList.get(r.getCustomerIndex()).setCurrentlyRenting(false);
                    bikeList.get(r.getBikeIndex()).setAvailability(true);
                    index = count;
            }
                count++;
        }
        //IF Statement: Removes rent object from arraylist and updates table
        if(index != -1) {
            rentList.remove(index);
            updateRentTable();
            updateBikeTable();
            updateCustTable();
        }
    }

    //Updates Bike Table by checking through bikeList arraylist and converting to an array
    private void updateBikeTable() {
        bikeTableModel.setRowCount(0);
        String availability;
        for (Bike b : bikeList) {
            if (b.getAvailability()) {
                availability = "Yes";
            } else {
                availability = "No";
            }
            Object[] bikeVariables;
            if (b instanceof eBike) {
                bikeVariables = new Object[]{b.getBikeID(), b.getType() + " (" + ((eBike) b).getMotorStrengthString()
                        + " - " + ((eBike) b).getMaximumDistance() + "km)", "$" + b.getPricePerDay(), availability};
            } else {
                bikeVariables = new Object[]{b.getBikeID(), b.getType(), "$" + b.getPricePerDay(), availability};
            }
            bikeTableModel.addRow(bikeVariables);
        }
    }

    //Updates Customer Table by checking through bikeList arraylist and converting to an array
    private void updateCustTable() {
        custTableModel.setRowCount(0);
        String isRenting;
        for (Customer c : customerList) {
            if (c.isCurrentlyRenting()) {
                isRenting = "Yes";
            } else {
                isRenting = "No";
            }
            Object[] custVariables;
            custVariables = new Object[]{c.getID(), c.getName(), c.getPhoneNumber(), isRenting};
            custTableModel.addRow(custVariables);
        }
    }

    //Updates Rent Table by checking through bikeList arraylist and converting to an array
    private void updateRentTable() {
        rentTableModel.setRowCount(0);
        for (Rent r : rentList) {
            if (r instanceof Rent) {
                Object[] rentVariables;
                rentVariables = new Object[]{r.getRentID(), bikeList.get(r.getBikeIndex()).getType(),
                        customerList.get(r.getCustomerIndex()).getName(), r.getDate(), r.getEndDate()};
                if (!chkbxOverdueRent.isSelected()) {
                    rentTableModel.addRow(rentVariables);
                } else {
                    if(r.getEndDate().isBefore(LocalDate.now())){
                        rentTableModel.addRow(rentVariables);
                    }
                }
            }
        }
    }

    //Writes rent, customer and bike arraylist information to file
    public void writeAll(){
        try{
            FileOutputStream fos = new FileOutputStream("resources/customers.dat");
            ObjectOutputStream oos =  new ObjectOutputStream(fos);
            for(Customer s: customerList){
                oos.writeObject(s);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try{
            FileOutputStream fos = new FileOutputStream("resources/bikes.dat");
            ObjectOutputStream oos =  new ObjectOutputStream(fos);
            for(Bike b: bikeList){
                oos.writeObject(b);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try{
            FileOutputStream fos = new FileOutputStream("resources/rent.dat");
            ObjectOutputStream oos =  new ObjectOutputStream(fos);
            for(Rent r: rentList){
                oos.writeObject(r);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        updateRentTable();
        updateCustTable();
        updateBikeTable();
    }

    //Reads rent, customer and bike arraylist information from file
    public void readAll(){
        customerList.clear();
        rentList.clear();
        bikeList.clear();

        try{
            FileInputStream fis = new FileInputStream("resources/customers.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(true){
                Object obj = ois.readObject();
                Customer c = (Customer)obj;
                customerList.add(c);
            }
        }catch(EOFException eof){}
        catch(Exception e){
            e.printStackTrace(); }

        try{
            FileInputStream fis = new FileInputStream("resources/bikes.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(true){
                Object obj = ois.readObject();
                Bike b = (Bike)obj;
                bikeList.add(b);
            }
        }catch(EOFException eof){}
        catch(Exception e){
            e.printStackTrace(); }

        try{
            FileInputStream fis = new FileInputStream("resources/rent.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(true){
                Object obj = ois.readObject();
                Rent r = (Rent)obj;
                rentList.add(r);
            }
        } catch(EOFException eof){}
        catch(Exception e){
            e.printStackTrace(); }

        updateRentTable();
        updateCustTable();
        updateBikeTable();
    }

//Commented out populating list of bikes, rents and customers as it is now read from resources folder.
//    private void dataEntry() {
//        Bike bike = new Bike(60, false, "Road Bike");
//        bikeList.add(bike);
//        bike = new Bike(61, true, "Mountain Bike");
//        bikeList.add(bike);
//        bike = new Bike(42, true, "City Bike");
//        bikeList.add(bike);
//        bike = new Bike(55, true, "BMX Bike");
//        bikeList.add(bike);
//        bike = new Bike(452, true, "Road Bike");
//        bikeList.add(bike);
//        bike = new Bike(73, true, "Mountain Bike");
//        bikeList.add(bike);
//        eBike ebike = new eBike(150, false, "eBike", 1022, true);
//        bikeList.add(ebike);
//        ebike = new eBike(110, true, "eBike", 2000, true);
//        bikeList.add(ebike);
//        ebike = new eBike(120, true, "eBike", 1240, false);
//        bikeList.add(ebike);
//        ebike = new eBike(330, true, "eBike", 1020, false);
//        bikeList.add(ebike);
//        ebike = new eBike(130, true, "eBike", 1042, true);
//        bikeList.add(ebike);
//        ebike = new eBike(148, true, "eBike", 1420, false);
//        bikeList.add(ebike);
//        Customer customer = new Customer("Jack", "123132123", true);
//        customerList.add(customer);
//        customer = new Customer("James", "123132123", true);
//        customerList.add(customer);
//        customer = new Customer("Sean", "020202023", false);
//        customerList.add(customer);
//        Rent rent = new Rent(LocalDate.now(), 10,0,0);
//        rentList.add(rent);
//        rent = new Rent(LocalDate.of(2015,12,15),10,1,6);
//        rentList.add(rent);
//    }

    //Checks for GUI itemstate changes and invokes methods
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == cbxBikeType){
                String bikeTypeSelectedItem = (String) cbxBikeType.getSelectedItem();
                if(bikeTypeSelectedItem.equals("eBike")){
                    lbBikeMotor.setVisible(true);
                    cbxBikeMotor.setVisible(true);
                    lbBikeDist.setVisible(true);
                    txtBikeDist.setVisible(true);
                } else {
                    lbBikeMotor.setVisible(false);
                    cbxBikeMotor.setVisible(false);
                    lbBikeDist.setVisible(false);
                    txtBikeDist.setVisible(false);
                }
            } else if (e.getSource() == btnAddBike){
                addBike();
            } else if (e.getSource() == btnAddCust){
                addCustomer();
            } else if (e.getSource() == btnAddRent){
                addRent();
            } else if (e.getSource() == btnReturnRent){
                returnRent();
            } else if (e.getSource() == chkbxOverdueRent){
                updateRentTable();
            } else if (e.getSource() == btnReadData){
                readAll();
            } else if (e.getSource() == btnSaveData){
                writeAll();
            } else if (e.getSource() == btnSelectBike){
                selectBike();
            } else if (e.getSource() == btnSelectCust){
                selectCustomer();
            }
        } catch (ArrayIndexOutOfBoundsException exc){
            JOptionPane.showMessageDialog(null, "Please select an item!");
        } catch (NumberFormatException exc){
            JOptionPane.showMessageDialog(null, "Please insert a valid number!");
        } catch (Exception exc){
            JOptionPane.showMessageDialog(null, exc.toString());
        }
    }

    public static void main(String[] args) {
        BikeHireGUI bikeHireGUI = new BikeHireGUI();
        new BikeHireGUI();
        bikeHireGUI.readAll();
        bikeHireGUI.setLocationRelativeTo(null);
        bikeHireGUI.setBounds(0,0,1000,1000);
        bikeHireGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bikeHireGUI.setVisible(true);
    }
}

