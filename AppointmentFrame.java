//Danri He
//Student Number 500765982

//import all the java libraries necessary
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.io.*;


public class AppointmentFrame extends JFrame
{
    //declare all the instance variables that are required for the running of this program
    private Calendar calendar;
    private SimpleDateFormat sdf, sdfMonth;
    private ArrayList<Appointment> appointments;
    private JPanel panel, controlPanel, dateSubPanel, npPanel, displayPanel,
                    dayMonthYearPanel, showPanel, actionPanel, hourMinutePanel, actionButtonPanel, 
                    descriptionPanel, monthPanel, contactPanel, controlPanelExtension, framePanel, panel2;
    private JPanel infoPanel, findClearPanel, addressPanel, nextPreviousMonth;
    private JButton previousDay, nextDay, showButton, createButton, 
                    cancelButton, findButton, clearButton, recallButton, nextMonth, previousMonth;
    private JButton[] buttonArray = new JButton[42];
    private JLabel dateLabel, dayLabel, monthLabel, yearLabel, hourLabel, minuteLabel, lastNameLabel, firstNameLabel, telLabel, emailLabel, addressLabel, dateLabelMonth;
    private JLabel[] calendarHeader = new JLabel[7];
    private JTextArea centerText, descriptionBox;
    private JScrollPane centerScroll;
    private JTextField dayField, monthField, yearField, hourField, minuteField, lastNameField, firstNameField, telField, emailField, addressTextArea;
    private ActionListener yesterday, tomorrow, create, show, cancel, clear, find, recall, calendarDay, previousMonthListener, nextMonthListener;
    private static final int WIDTH = 740;
    private static final int HEIGHT = 612;
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private static final int DISPLAY_ROWS = 15;
    private static final int DISPLAY_COLUMNS = 30;
    private int minutes, hours, day, month, year;
    private Contacts contact;
    private Stack<Appointment> appointmentStack = new Stack<Appointment>();
    private boolean[] appointmentOnDate = new boolean[42];
    private Appointment appointmentDay = new Appointment();

    public AppointmentFrame()
    {
        calendar = Calendar.getInstance(); //declare calendar variable
        sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");    //declare simpledateformat object
        sdfMonth = new SimpleDateFormat("MMM");
        appointments = new ArrayList<Appointment>();    //create ArrayList of type Appointment
        contact = new Contacts();

        framePanel = new JPanel();
        framePanel.setLayout(new GridLayout());
        this.add(framePanel);

        createMainPanel();  //call function to create the main panel of the JFrame
        createPanel2();

        dateLabel = new JLabel(sdf.format(this.calendar.getTime()));    //set label
        panel.add(dateLabel, BorderLayout.NORTH);  //add label to panel at NORTH position

        dateLabelMonth = new JLabel(sdfMonth.format(this.calendar.getTime()));
        panel2.add(dateLabelMonth, BorderLayout.NORTH);

        createControlPanelExtension();

        createDisplayPanel();   //call function to create the display panel
        createControlPanel();   //call function to create the control panel
        
        setSize(WIDTH, HEIGHT);

        try
        {
            contact.readContactsFile(); //try to call the readContactsFile method
            //System.out.println("DONE");
        }
        catch(IOException e)  //catch any FileNotFoundException that is thrown
        {
            System.out.println("DONE");
            descriptionBox.setText("Invalid contacts file.");  //display error message to the user in the descriptionbox
        }
    }

    public void createMainPanel()   //declare method to create main panel
    {
        panel = new JPanel();    //declare main panel
        panel.setLayout(new BorderLayout());    //set BorderLayout for the main panel
        framePanel.add(panel);    //add panel to the frame
    }

    public void createPanel2()  //declare a method to create panel2
    {
        panel2 = new JPanel();  //initialize panel2
        panel2.setLayout(new BorderLayout());   //set BorderLayout for panel2
        framePanel.add(panel2); //add panel2 to the framePanel
    }

    public void createDisplayPanel()    //declare method to create display panel
    {
        displayPanel = new JPanel();    //initialize displayPanel object
        displayPanel.setLayout(new BorderLayout());
        centerText = new JTextArea(); //create textArea
        //displayPanel.setPreferredSize(new Dimension(DISPLAY_ROWS, DISPLAY_COLUMNS));
        //displayPanel.setBorder(new TitledBorder(new EtchedBorder(), "TEST"));
        centerText.setEditable(false);  //make centerText area non-editable by users
        centerScroll = new JScrollPane(centerText); //add scroll bar to the textArea
        displayPanel.add(centerScroll);   //add the scroll bar and the textArea to the panel at the CentrePosition
        panel.add(displayPanel);
    }

    public void createControlPanel()    //declare method to create control panel
    {
        controlPanel = new JPanel(); //create control panel
        controlPanel.setLayout(new BorderLayout()); //set borderLayout of control panel
        panel.add(controlPanel, BorderLayout.SOUTH);    //set the control panel at location SOUTH
        createDatePanel();  //call method to create and add date subpanel
        createActionPanel();    //call method to create and add action subpanel
    createDescriptionPanel();       //call method to create and add description subpanel
    }

    public void createDatePanel()   //declare method to create date panel
    {
        dateSubPanel = new JPanel();    //initialize dateSubPanel 
        dateSubPanel.setLayout(new BorderLayout()); //set dateSubPanel to implement borderlayout
        //dateSubPanel.setPreferredSize(new Dimension(2, DISPLAY_COLUMNS));
        controlPanel.add(dateSubPanel, BorderLayout.NORTH); //add dateSubPanel to the controlPanel
        dateSubPanel.setBorder(new TitledBorder(new EtchedBorder(), "Date"));   //set the title border to display Date
        createDateButtonPanel();    //create and add buttonPanel
        createDayMonthYearPanel();  //create and add DayMonthYearPanel
        createShowPanel();          //create and add showPanel
    }

    public void createDateButtonPanel() //declare method to create the dateButtonPanel
    {
        npPanel = new JPanel(); //initialize npPanel
        npPanel.setLayout(new GridLayout());    //set npPanel to implement grid layout formatting
        previousDay = new JButton("<"); //initialize the previousday button
        yesterday = new previousDayButtonListener();    //initialize yesterday to be of type previousDayButtonListener
        previousDay.addActionListener(yesterday);   //add the yesterday actionlistener to previousday button
        nextDay = new JButton(">"); //initialize the nextDay button
        tomorrow = new nextDayButtonListener(); //initialize tomorrow actionListener to be of type nextDayButtonListener
        nextDay.addActionListener(tomorrow);    //add the tomorrow actionListener to nextDay button
        npPanel.add(previousDay);   //add previousDay button to the npPanel
        npPanel.add(nextDay);   //add nextDay button to the npPanel
        dateSubPanel.add(npPanel, BorderLayout.NORTH);  //add npPanel to the dateSubpanel at north position
    }

    public void createDayMonthYearPanel()   //declare a method to create the DayMonthYearPanel
    {
        dayMonthYearPanel = new JPanel();   //initialize the dayMontYearPanel as a JPanel object
        dayMonthYearPanel.setLayout(new GridLayout());  //set dayMonthYearPanel to implement gridLayout formatting
        
        //initialize the following JLabels
        dayLabel = new JLabel("Day");   
        monthLabel = new JLabel("Month");
        yearLabel = new JLabel("Year");
        //initialize the following JTextFields
        dayField = new JTextField();
        monthField = new JTextField();
        yearField = new JTextField();
        //add the initialized JLabels and JTextFields to the dayMonthYearPanel
        dayMonthYearPanel.add(dayLabel);
        dayMonthYearPanel.add(dayField);
        dayMonthYearPanel.add(monthLabel);
        dayMonthYearPanel.add(monthField);
        dayMonthYearPanel.add(yearLabel);
        dayMonthYearPanel.add(yearField);

        dateSubPanel.add(dayMonthYearPanel, BorderLayout.CENTER);   //add the dayMonthYearPanel to the dateSubpanel and center position
    }

    public void createShowPanel()   //declare a method to create the showPanel
    {
        showPanel = new JPanel();   //initialize showPanel to be a new JPanel object
        showButton = new JButton("Show");   //initialize showButton to be a new JButton object
        show = new showButtonListener();    //initialize the show actionlistener to be of type showButtonListener
        showButton.addActionListener(show); //add show to showButton
        showPanel.add(showButton);  //add showButton to showPanel
        dateSubPanel.add(showPanel, BorderLayout.SOUTH);    //add showPanel to dateSubPanel at the south position
    }

    public void createActionPanel() //declare a method to create the actionPanel
    {
        actionPanel = new JPanel(); //initialize actionPanel as a new JPanel object
        actionPanel.setLayout(new BorderLayout());  //set actionPanel to follow borderlayout formatting
        controlPanel.add(actionPanel, BorderLayout.CENTER); //add actionPanel to the controlPanel at the center position
        actionPanel.setBorder(new TitledBorder(new EtchedBorder(), "Appointment"));  //label the border of the panel
        createHourMinuteFields();   //method call to create textFields
        createActionButtons();  //method call to create ActionButtons
    }

    public void createHourMinuteFields()    //declare a method to create the hour and minute textfields/panel
    {
        hourMinutePanel = new JPanel(); //initialize hourMinutePanel as a new JPanel object
        hourMinutePanel.setLayout(new GridLayout());    //set the layout of hourMintePanel to follow grid layout formatting
        
        //create and add the following JLabels and JTextFields
        hourLabel = new JLabel("Hour");
        hourMinutePanel.add(hourLabel);
        hourField = new JTextField();
        hourMinutePanel.add(hourField);
        minuteLabel = new JLabel("Minute");
        hourMinutePanel.add(minuteLabel);
        minuteField = new JTextField();
        hourMinutePanel.add(minuteField);

        actionPanel.add(hourMinutePanel, BorderLayout.NORTH);   //add the hourMinutePanel to the ActionPanel at the north position
    }

    public void createActionButtons()   //declare a method to create action buttons
    {
        actionButtonPanel = new JPanel();   //initialize actionButtonPanel as a new JPanel object
        actionButtonPanel.setLayout(new GridLayout());  //set actionButtonPanel to follow grid layout formatting
        createButton = new JButton("CREATE");   //initialize createButton as a new JButton
        create = new createListener();  //initialize create as object of type createListener
        createButton.addActionListener(create); //add actionLister create to createButton
        actionButtonPanel.add(createButton);    //add the createButton to the actionButtonPanel
        
        cancelButton = new JButton("CANCEL");   //initialize cancelButton as new JButton object
        cancel = new cancelListener();  //initialize cancel to be of type cancelListener
        cancelButton.addActionListener(cancel); //add cancel to cancelButton
        actionButtonPanel.add(cancelButton);    //add cancelButton to the actionButtonPanel

        recallButton = new JButton("RECALL");   //initialize recallButton as a new JButton object
        recall = new recallButtonListener();    //initialize recall to be of type recallButtonListener
        recallButton.addActionListener(recall); //add recall to recallButton
        actionButtonPanel.add(recallButton);    //add recallButton to the actionButtonPanel

        actionPanel.add(actionButtonPanel, BorderLayout.SOUTH); //add the actionButtonPanel to the actionPanel at south position
    }

    public void createDescriptionPanel()    //declare a method to create the description panel
    {
        descriptionPanel = new JPanel();    //initialize descriptionPanel as a new JPanel object
        descriptionPanel.setLayout(new BorderLayout()); //set descriptionPanel to follow border layout formatting
        descriptionPanel.setBorder(new TitledBorder(new EtchedBorder(), "Description"));    //set the title of the border
        descriptionBox = new JTextArea(ROWS, COLUMNS);  //initialize descriptionBox as a new JTextArea
        descriptionPanel.add(descriptionBox);   //add the descriptionBox to the descriptionPanel
        controlPanelExtension.add(descriptionPanel, BorderLayout.SOUTH); //add the descriptionPanel to the controlPanel
    }

    public void createControlPanelExtension()    //declare method to create control panel
    {
        controlPanelExtension = new JPanel(); //create control panel
        controlPanelExtension.setLayout(new BorderLayout()); //set borderLayout of control panel
        panel2.add(controlPanelExtension, BorderLayout.SOUTH);    //set the control panel at location SOUTH
        createMonthPanel(); //call the createMonthPanel method
        createContactPanel();   //call the createContactPanel method
        //createNextPreviousMonthPanel();
    }

    /*public void createNextPreviousMonthPanel()
    {
        nextPreviousMonth = new JPanel();
        nextPreviousMonth.setLayout(new GridLayout(0,2));

        nextMonth = new JButton(">");
        nextMonthListener = new nextMonthButtonListener();
        nextMonth.addActionListener(nextMonthListener);

        previousMonth = new JButton("<");
        previousMonthListener = new previousMonthButtonListener();
        previousMonth.addActionListener(previousMonthListener);

        nextPreviousMonth.add(previousMonth);
        nextPreviousMonth.add(nextMonth);

        panel2.add(nextPreviousMonth, BorderLayout.SOUTH);
    }*/

    public void createMonthPanel()  //method to create the month Panel
    {
        monthPanel = new JPanel();  //initialize monthPanel as a new JPanel object
        monthPanel.setLayout(new GridLayout(0, 7)); //set monthPanel to follow gridlayout with 7 columns
        calendarDay = new calendarButtonListener(); //initalize calendarDay to be of type calendarButtonListener

        //the following for loop creates 7 new JLabels stored in the calendarHeader array and adds them to the month panel
        for (int i = 0; i < 7; i++)
        {
            calendarHeader[i] = new JLabel();
            monthPanel.add(calendarHeader[i]);
        }

        //set the text for the 7 calendarHeader labels
        calendarHeader[0].setText("Sun");
        calendarHeader[1].setText("Mon");
        calendarHeader[2].setText("Tue");
        calendarHeader[3].setText("Wed");
        calendarHeader[4].setText("Thu");
        calendarHeader[5].setText("Fri");
        calendarHeader[6].setText("Sat");

        //the following for loop creates 42 new JButton objects stored in the buttonArray array and adds them to the month panel while also adding the calendarDay actionListener to each one
        for (int i = 0; i < 42; i++)
        {
            buttonArray[i] = new JButton();
            buttonArray[i].addActionListener(calendarDay);
            monthPanel.add(buttonArray[i]); 
        }

        nextMonth = new JButton(">");
        nextMonthListener = new nextMonthButtonListener();
        nextMonth.addActionListener(nextMonthListener);

        previousMonth = new JButton("<");
        previousMonthListener = new previousMonthButtonListener();
        previousMonth.addActionListener(previousMonthListener);

        monthPanel.add(previousMonth);
        monthPanel.add(nextMonth);


        highlightCalendarDay(); //call the highlightCalendarDay method to update the month panel
        panel2.add(monthPanel, BorderLayout.CENTER);    //add the monthPanel to panel2 at the center position
    }

    public void createContactPanel()    //method to create the contactPanel
    {
        contactPanel = new JPanel();    //initialize contactPanel to be of object JPanel
        contactPanel.setLayout(new BorderLayout()); //set contactPanel to abide to BorderLayout
        contactPanel.setBorder(new TitledBorder(new EtchedBorder(), "Contact"));    //set the border for contactPanel
        controlPanelExtension.add(contactPanel, BorderLayout.NORTH);    //add contactPanel to controlPanelExtension at the NORTH position
        createInfoPanel();  //call the createInfoPanel method to create the info fields
        createAddressPanel();   //call the addressPanel to create the address fields
        createFindClearPanel(); //call the createFindClearPanel method to create the find and clear buttons
    }

    public void createInfoPanel()   //method to create the info fields
    {
        infoPanel = new JPanel();   //initialize infoPanel to be a new JPanel object
        infoPanel.setLayout(new GridLayout(0, 2));  //instruct infoPanel to follow GridLayout with 2 columns
        
        //the following creates JTextField/JTextArea/JLabel objects
        lastNameField = new JTextField(); 
        firstNameField = new JTextField();
        telField = new JTextField();
        emailField = new JTextField();
        lastNameLabel = new JLabel("Last Name");
        firstNameLabel = new JLabel("First Name");
        telLabel = new JLabel("tel");
        emailLabel = new JLabel("email");

        //the following adds the fields, textAreasm and label to the infoPanel
        infoPanel.add(lastNameLabel);
        infoPanel.add(firstNameLabel);
        infoPanel.add(lastNameField);
        infoPanel.add(firstNameField);
        infoPanel.add(telLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(telField);
        infoPanel.add(emailField);

        contactPanel.add(infoPanel, BorderLayout.NORTH);    //add the infoPanel to contactPanel at the NORTH position
    }

    public void createAddressPanel()    //method to create the addressPanel
    {
        addressPanel = new JPanel();    //initialize addressPanel to be a new JPanel object
        addressPanel.setLayout(new GridLayout(0,1));    //set addressPanel to follow GridLayout with 1 column

        //initialize the addressLabel and TextArea
        addressLabel = new JLabel("address");
        addressTextArea = new JTextField();
        //add the addressLabel and TextArea to the addressPanel
        addressPanel.add(addressLabel);
        addressPanel.add(addressTextArea);

        contactPanel.add(addressPanel, BorderLayout.CENTER);    //add addressPanel to contactPanel at the CENTER position
    }

    public void createFindClearPanel()  //method to create the findClearPanel
    {
        findClearPanel = new JPanel();  //initialize findClearPanel as a new JPanel object
        findClearPanel.setLayout(new GridLayout(0,2));  //set findClearPanel to follow GridLayout with 2 columns

        findButton = new JButton("Find");   //initialize findButton as new JButton object
        find = new findButtonListener();    //initialize find as a new findButtonListener
        findButton.addActionListener(find); //add find to findButton

        clearButton = new JButton("Clear"); //initialize clearButton as a new JButton object
        clear = new clearButtonListener();  //initialize clear as a new clearButtonListener
        clearButton.addActionListener(clear);   //add clear to clearButton

        //add both buttons to the findClearPanel
        findClearPanel.add(findButton);
        findClearPanel.add(clearButton);

        contactPanel.add(findClearPanel, BorderLayout.SOUTH);   //add findClearPanel to contactPanel at the SOUTH position
    }

    class previousDayButtonListener implements ActionListener   //declare listener for the previousdaybutton implementing actionlistener
    {
        //when previous day button is clicked, set the date back one day.
        public void actionPerformed(ActionEvent event)  //inner class of the actionlistener
        {
            previousDay();  //call the previousDay method
            printAppointments();    //print the appointments
        }   
    }

    class createListener implements ActionListener  //declare listener for the createButton implementing actionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class of createListener
        {
            dateErrorCheck();    //call the createAppointment method
            //createAppointment();
            //reset the hour, minute, and description textfields
            hourField.setText("");
            minuteField.setText("");
            //descriptionBox.setText("");
        }
    }

    class cancelListener implements ActionListener  //declare listener for the cancelButton implementing actionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class of cancelListener
        {
            cancelAppointment();    //call the cancelAppointment method

            //reset the hour and minute textfields
            hourField.setText("");
            minuteField.setText("");
        }
    }

    class nextDayButtonListener implements ActionListener   //declare a listener for the nextDayButton implementing ActionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for nextDayButtonListener
        {
            nextDay();  //call nextDay method to increase date
            printAppointments();    //call printAppointments method to update centerText display
        }
    }

    class showButtonListener implements ActionListener  //declare a listener for the showButton implementing ActionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for showButtonListener
        {
            if (yearField.getText().equals("") || monthField.getText().equals("") || dayField.getText().equals(""))
            {
                descriptionBox.setText("Invalid date entered.");
            }
            else
            {
                showCalendar(Integer.parseInt(yearField.getText()), (Integer.parseInt(monthField.getText())-1), Integer.parseInt(dayField.getText()));  //call the showCalendar method with parameters from the yearField, monthField and dayField
                highlightCalendarDay(); //call the highlightCalendarDay method to update the month panel
                dateLabelMonth.setText(sdfMonth.format(calendar.getTime()));    //update the dateLabelMonth to the current month
            }
        }
    }

    class clearButtonListener implements ActionListener //declare a listener for the clearButton implementing ACtionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for clearButtonListener
        {
            clearPersonInfo();  //call the clearPersonInfo to remove all content in the contact fields
        }
    }

    class findButtonListener implements ActionListener  //declare a listener for the findButton implementing ActionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for FindButtonListener
        {
            findPersonInfo();   //call the findPersonInfo method to find contacts within the contacts LinkedList
        }
    }

    class recallButtonListener implements ActionListener    //declare a listener for the recallButton implementing ActionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for recallButtonListener
        {
            recallAppointment();    //call the recallAppointment method to bring up the last appointment created
            highlightCalendarDay();
        }
    }

    class calendarButtonListener implements ActionListener  //declare a listener for the calendarButtons in the buttonArray implementing ActionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for calendarButtonListener
        {
            JButton tempButton = (JButton)event.getSource();    //create a tempButton referencing the JButton that is being clicked
            int tempDay = Integer.parseInt(tempButton.getText());   //create a tempDay integer to store the value of the text contained within tempButton
            calendar.set(Calendar.DAY_OF_MONTH, tempDay);   //set the calendar day to be the same as tempDay
            highlightCalendarDay(); //call the highlightCalendarDay method to update the month panel
            updateDateLabels(); //update the date labels

        }
    }

    class nextMonthButtonListener implements ActionListener //declare a listener for the nextMonthButton implementing actionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for nextMonthButtonListener
        {
            calendar.add(Calendar.MONTH, 1);    //increase the month value of calendar by 1
            updateDateLabels(); //update the date labels
            highlightCalendarDay(); //call the highlightCalendarDay function to update the month panel
        }
    }

    class previousMonthButtonListener implements ActionListener //declare a listener for the previousMonthButton implementing actionListener
    {
        public void actionPerformed(ActionEvent event)  //inner class for previousMonthButtonListener
        {
            calendar.add(Calendar.MONTH, -1);   //decrease the month value of calendar by 1
            updateDateLabels(); //update the date labels
            highlightCalendarDay(); //call the highlightCalendarDay function to update the month panel
        }
    }
    public void showCalendar(int year, int month, int day)
    {
        calendar.set(Calendar.YEAR, year); //set the year of calendar to equal the value entered in the yearField
        calendar.set(Calendar.MONTH, month); //set the month of calendar equal to the value of the monthField 
        calendar.set(Calendar.DAY_OF_MONTH, day);  //set the day of the month in calendar equal to the value of dayField
        updateDateLabels(); //update the dateLabels
        printAppointments();    //call printAppointments method to update the centerText
    }

    public void previousDay()   //declare a method to decrement date by 1
    {
        calendar.add(Calendar.DATE, -1);    //decrement calendar by 1 day
        updateDateLabels();
        highlightCalendarDay(); //call highlightCalendarDay method
    }
    
    public void nextDay()   //declare a method to increase calendar by 1 day
    {
        calendar.add(Calendar.DATE, 1); //increment calendar by 1 day
        dateLabel.setText(sdf.format(this.calendar.getTime())); //update the dateLabel
        dateLabelMonth.setText(sdfMonth.format(this.calendar.getTime()));   //update dateLabelMonth
        highlightCalendarDay(); //call highlightCalendarDay method
    }

    public void printAppointments() //declare a method to update the content of centerText
    {
        //create a temporary appointment object called today with parameters of current calendar
        Appointment today = new Appointment(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), "nothing");
        centerText.setText("");
        Collections.sort(appointments); //sort the appointments arrayList
        Person person;

        for (int i = 0; i < appointments.size(); i++)   //traverse the array
        {
            if (today.compareDate(appointments.get(i).calendarDate()) == true)  //if the appointment matches the date
            {
                    centerText.append(appointments.get(i).print() + "\n \n");  //append the appointment and create new line               
            }
        }
    }

    public Appointment findAppointment(int year, int month, int day, int hour, int minute)
    {
        Collections.sort(appointments);
        //Appointment today = new Appointment(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), "nothing");
        
        for (int i = 0; i < appointments.size(); i++)   //traverse the array
        {
            if (appointments.get(i).occursOn(year, month, day, hour, minute) == true)   //if the appointment matches the time specified
            {
                return appointments.get(i); //return the appointment object
            }
        }
        return null;    //if no matches are found, return null
    }

    public void updateDateLabels()
    {
        dateLabel.setText(sdf.format(this.calendar.getTime())); //update the dateLabel
        dateLabelMonth.setText(sdfMonth.format(this.calendar.getTime()));   //update dateLabelMonth
    }

    public void createAppointment() //declare a method to create an appointment 
    {
        int mins, hours;   //declare a mins variable of type integer
        if (minuteField.getText().equals(""))   //if the minuteField is blank
            mins = 0;   //set mins equal to 0
        else    //if the minuteField is not blank
            mins = Integer.parseInt(minuteField.getText()); //set mins equal to the integer value of the text in minuteField

        if (hourField.getText().equals("")) //if the hourField is blank
        {
            hours = 0;  //set hour to equal 0
        }
        else    //if the hourField is not blank
        {
            hours = Integer.parseInt(hourField.getText());  //set hours to equal to integer value of the text in the minuteField
        }
        if (mins > 59 || hours > 23)    //if either mins or hours exceed their limited value
        {
            descriptionBox.setText("ERROR! Invalid time specified.");   //display error message to user
        }
        else
        {
            if (findAppointment(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hours, mins) != null)   //if no appointments are already scheduled at the time specified
            {
                descriptionBox.setText("CONFLICT!!");   //display conflict message
            }
            else 
            {
                if (!(lastNameField.getText().equals("")) && !(firstNameField.getText().equals("")))    //if lastNameField and firstNameField are not blank
                {
                    Person person = new Person(lastNameField.getText(), firstNameField.getText(), telField.getText(), addressTextArea.getText(), emailField.getText()); //create a temporary person object with parameters specified in contact fields
                    String text = descriptionBox.getText() + " WITH: " + person.toString(); //append the person.toString to the appointment description
                    appointments.add(new Appointment(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hourField.getText()), mins, text));   //add the appointment to the appointment list
                    appointmentStack.push(new Appointment(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hourField.getText()), mins, text));  //add the appointment to the appointment stack
                    printAppointments();    //print appointments
                    for (int i = 0; i < 42; i++)
                    {
                        if (buttonArray[i].getText().equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))))
                        {
                            appointmentOnDate[i] = true;
                        }
                    }
                    descriptionBox.setText(""); //erase the descriptionbox content
                }
                else
                {
                    appointments.add(new Appointment(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hours, mins, descriptionBox.getText())); //add a new appointment to the appointments arrayList
                    appointmentStack.push(new Appointment(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hours, mins, descriptionBox.getText()));
                    printAppointments();    //update the centerText display
                    for (int i = 0; i < 42; i++)
                    {
                        if (buttonArray[i].getText().equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))))
                        {
                            appointmentOnDate[i] = true;
                        }
                    }
                    descriptionBox.setText(""); //clear descriptionBox
                }
            }
        }
    }

    public void cancelAppointment() //declare a method to cancel existing appointments
    {
        Collections.sort(appointments); //sort the appointments arrayList
        descriptionBox.setText("");     //reset the description box to be blank
        int mins;
        //Iterator<Appointment> stackIterator = appointmentStack.iterator();
        //Appointment tempApp;
        ArrayList<Appointment> tempAppointmentArray = new ArrayList<Appointment>();

        if (minuteField.getText().equals(""))   //if minuteField is empty
        {
            mins = 0;   //set minutes equal to 0
        }
        else    //if minuteField is not empty
        {
            mins = Integer.parseInt(minuteField.getText()); //set mins equal to the integer value of the text stored in minuteField
        }

        //the following for loop adds all the appointment objects in appointmentStack to the tempAppointmentArray, popping them from the stack
        for (int i = 0; i < appointmentStack.size(); i++) 
        {
            tempAppointmentArray.add(appointmentStack.pop());
        }

        for (int i = 0; i < tempAppointmentArray.size(); i++)   //traverse the tempAppointmentArray
        {
            //if the appointment occurs on the same date as the one we are trying to remove
            if (tempAppointmentArray.get(i).occursOn(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hourField.getText()), mins) == true)    //if an appointment exists at the specified time:
            {
                tempAppointmentArray.remove(i); //remove the appointment from tempAppointmentArray
            }
        }

        for (int i = tempAppointmentArray.size()-1; i > -1; i--)    //traverse the array backwards (from greatest to smallest)
        {
            appointmentStack.push(tempAppointmentArray.get(i)); //push contents back onto the appointmentStack
        }
        for (int i = 0; i < appointments.size(); i++)   //traverse the arrayList
        {
            if (appointments.get(i).occursOn(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hourField.getText()), mins) == true)    //if an appointment exists at the specified time:
            {
                appointments.remove(i); //remove the appointment from the arrayList
                for (int x = 0; x < 42; x++)
                {
                    if (buttonArray[x].getText().equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))))
                    {
                        appointmentOnDate[x] = false;
                        System.out.println("True");
                    }
                }   
            }
        }
        
        highlightCalendarDay();
        printAppointments();    //update the centerText display
    }

    public void recallAppointment() //create a method to recall Appointments from the appointmentstack
    {
        Appointment temp = appointmentStack.peek();  //set temp equal to the appointment that is 'popped' from the stack

        //set the descriptionbox, minuteField, and hourField depending on the appointment that was 'popped'
        descriptionBox.setText(temp.description);
        minuteField.setText(String.valueOf(temp.minute));
        hourField.setText(String.valueOf(temp.hour));

        showCalendar(temp.year, temp.month, temp.day);  //go to the proper date in the calendar based on temp
    }

    public void clearPersonInfo()   //create a method to clear all the info fields
    {
        //clear the email field, lastNameField, firstNameField, addressTextArea, and telField (set them to nothing)
        emailField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        addressTextArea.setText("");
        telField.setText("");
    }

    public void findPersonInfo()    //create a method to find person information based on user input
    {
        Person person;  //declare a person object

        if (!(emailField.getText().equals(""))) //if the email field is not blank:
        {
            person = new Person("", "", "", "", emailField.getText());  //initialize person to have all blank parameters except of the the email parameter
            
            for (Person p : contact.getList())  //for all Person p in linkedlist stored in contact,
            {
                if (person.compareEmail(p) == 0)    //if the compareEmail method returns 0,
                {
                    //update the contact fields with the proper information
                    lastNameField.setText(p.getLastName());
                    firstNameField.setText(p.getFirstName());
                    addressTextArea.setText(p.getAddress());
                    telField.setText(p.getTelephone());
                    emailField.setText(p.getEmail());
                }
            }
        }
        else if (!(lastNameField.getText().equals("")) && !(firstNameField.getText().equals("")))   //if both the last name field and first name field are not blank:
        {
            person = new Person(lastNameField.getText(), firstNameField.getText(), "", "", ""); //create a new person object with the firstname and lastname in the fields

            for (Person p : contact.getList())  //for all person objects in the linkedlist stored within contacts,
            {
                if (person.compareTo(p) == 0)   //if the firstname and lastname match according to the person.compareTo method,
                {
                    //update all the person information fields
                    lastNameField.setText(p.getLastName());
                    firstNameField.setText(p.getFirstName());
                    addressTextArea.setText(p.getAddress());
                    telField.setText(p.getTelephone());
                    emailField.setText(p.getEmail());
                }
            }
        }
        else if (!(telField.getText().equals("")))  //if the telephone field is not empty
        {
            person = new Person("","",telField.getText(), "","");   //initialize perosn object to have all blank parameters except for the telephone parameter

            for (Person p : contact.getList())  //for all Person p in contact linkedList
            {
                if (person.compareTelephone(p) == 0)    //if the compareTelephone method returns 0,
                {
                    //update the contact fields with appropriate information
                    lastNameField.setText(p.getLastName());
                    firstNameField.setText(p.getFirstName());
                    addressTextArea.setText(p.getAddress());
                    telField.setText(p.getTelephone());
                    emailField.setText(p.getEmail());
                }
            }
        }
    }

    public void dateErrorCheck()    //create a method to check for any errors in date entry
    {
        if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)  //if one of the months that contain 31 days
        {
            if (dayField.getText().equals(""))  //if dayField is empty
            {
                    createAppointment();    //call the createAppointment method to create appointments
            }
            else    //if dayField is not empty
            {
                if(Integer.parseInt(dayField.getText()) > 31)   //if dayField is greater than 31 
                {
                    descriptionBox.setText("ERROR! Cannot have more than 31 days in specified month."); //use descriptionBox to display error message to user
                }
                else    //if dayField is not greater than 31
                {
                    createAppointment();    //call the createAppointment method to create appointments
                }
            }
        }
        else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10) //if month is one that contains 30 days
        {
            if (dayField.getText().equals(""))  //if dayField is empty
            {
                    createAppointment();    //call the createAppointment method
            }
            else    //if dayField is not empty
            {
                if (Integer.parseInt(dayField.getText()) > 30)  //if dayField is greater than 30
                {
                    descriptionBox.setText("ERROR! Cannot have more than 31 days in specified month."); //display error message in descriptionBox
                }
                else    //if dayField is not greater than 30
                {
                    createAppointment();    //call the createAppointment method
                }
            }
        }
        else if (calendar.get(Calendar.MONTH) == 1) //if month is february
        {
            if (dayField.getText().equals(""))  //if dayfield is empty
            {
                    createAppointment();    //call the createAppointment method
            }
            else    //if dayField is not empty
            {   
                if(Integer.parseInt(dayField.getText()) > 28)   //if dayField is greater than 28
                {
                    descriptionBox.setText("ERROR! Cannot have more than 28 days in specified month."); //display error message
                }
                else    //if dayfield is not greater than 28
                {
                    createAppointment();    //call the createAppointment method
                }
            }
        }

    }

    public boolean leapYearTest()
    {
        if (calendar.get(Calendar.YEAR) % 4 == 0)
        {
            if (calendar.get(Calendar.YEAR) % 100 == 0)
            {
                if (calendar.get(Calendar.YEAR)%400 == 0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return true;
            }
        }
        else{
            return false;
        }

    }

    public void highlightCalendarDay()  //method to update the month panel
    {
        int temp = calendar.get(Calendar.DAY_OF_MONTH); //create integer temp to store the current day
        calendar.set(Calendar.DAY_OF_MONTH, 1); //set the calendar day to the first day of the month

        //the following for loop traverses through the buttonArray and sets all the buttons visible
        for (int i = 0; i < 42; i++)
        {
            buttonArray[i].setVisible(true);
        }
        
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1)    //if the first day of the month is on a sunday
        {
            /*the following if statements and nested loops create the calendar display based on the number of days in the month,
             altering the visibilities of buttons as well as the text values*/
            if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)
            {
                for (int i = 0; i < 31; i++)
                {
                    buttonArray[i].setText(String.valueOf(i+1));
                }
                for (int i = 31; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10)
            {
                for (int i = 0; i < 30; i++)
                {
                    buttonArray[i].setText(String.valueOf(i+1));
                }
                for (int i = 30; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 1)
            {
                if (leapYearTest() == false)
                {
                    for (int i = 0; i < 28; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i+1));
                    }
                    for (int i = 28; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
                else if (leapYearTest() == true)
                {
                    for (int i = 0; i < 29; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i+1));
                    }
                    for (int i = 29; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
            }
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 2)    //if the first day of the month is monday
        {
            /*the following if statements and nested loops create the calendar display based on the number of days in the month,
             altering the visibilities of buttons as well as the text values*/
            if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)
            {
                buttonArray[0].setVisible(false);
                for (int i = 1; i < 32; i++)
                {
                    buttonArray[i].setText(String.valueOf(i));
                }
                for (int i = 32; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10)
            {
                buttonArray[0].setVisible(false);
                for (int i = 1; i < 31; i++)
                {
                    buttonArray[i].setText(String.valueOf(i));
                }
                for (int i = 31; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 1)
            {
                buttonArray[0].setVisible(false);

                if (leapYearTest() == false)
                {
                    for (int i = 1; i < 29; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i));
                    }
                    for (int i = 29; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
                else if (leapYearTest() == true)
                {
                    for (int i = 1; i < 30; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i));
                    }
                    for (int i = 30; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
            }
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 3)    //if the first day of the month is tuesday
        {
            /*the following if statements and nested loops create the calendar display based on the number of days in the month,
             altering the visibilities of buttons as well as the text values*/
            if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)
            {
                buttonArray[0].setVisible(false);
                buttonArray[1].setVisible(false);

                for (int i = 2; i < 33; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-1));
                }
                for (int i = 33; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10)
            {
                buttonArray[0].setVisible(false);
                buttonArray[1].setVisible(false);

                for (int i = 2; i < 32; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-1));
                }
                for (int i = 32; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 1)
            {
                buttonArray[0].setVisible(false);
                buttonArray[1].setVisible(false);
                if (leapYearTest() == false)
                {
                    for (int i = 2; i < 30; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-1));
                    }
                    for (int i = 30; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
                else if (leapYearTest() == true)
                {
                    for (int i = 2; i < 31; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-1));
                    }
                    for (int i = 31; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
            }
        }
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 4)   //if the first day of the month is wednesday
        {
            /*the following if statements and nested loops create the calendar display based on the number of days in the month,
             altering the visibilities of buttons as well as the text values*/
            if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)
            {
                for (int i = 0; i < 3; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 3; i < 34; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-2));
                }
                for (int i = 34; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10)
            {
                for (int i = 0; i < 3; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 3; i < 33; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-2));
                }
                for (int i = 33; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 1)
            {
                for (int i = 0; i < 3; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                if (leapYearTest() == false)
                {
                    for (int i = 3; i < 31; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-2));
                    }
                    for (int i = 31; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
                else if (leapYearTest() == true)
                {
                    for (int i = 3; i < 32; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-2));
                    }
                    for (int i = 32; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
            }
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 5)    //if the first day of the month is thursday
        {
            /*the following if statements and nested loops create the calendar display based on the number of days in the month,
             altering the visibilities of buttons as well as the text values*/
            if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)
            {
                for (int i = 0; i < 4; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 4; i < 35; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-3));
                }
                for (int i = 35; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10)
            {
                for (int i = 0; i < 4; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 4; i < 34; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-3));
                }
                for (int i = 34; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 1)
            {
                for (int i = 0; i < 4; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                if (leapYearTest() == false)
                {
                    for (int i = 4; i < 32; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-3));
                    }
                    for (int i = 32; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
                else if (leapYearTest() == true)
                {
                    for (int i = 4; i < 33; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-3));
                    }
                    for (int i = 33; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
            }
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 6)    //if the first day of the month is friday
        {
            /*the following if statements and nested loops create the calendar display based on the number of days in the month,
             altering the visibilities of buttons as well as the text values*/
            if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)
            {
                for (int i = 0; i < 5; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 5; i < 36; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-4));
                }
                for (int i = 36; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10)
            {
                for (int i = 0; i < 5; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 5; i < 35; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-4));
                }
                for (int i = 35; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 1)
            {
                for (int i = 0; i < 5; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                if (leapYearTest() == false)
                {
                    for (int i = 5; i < 33; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-4));
                    }
                    for (int i = 33; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
                else if (leapYearTest() == true)
                {
                    for (int i = 5; i < 34; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-4));
                    }
                    for (int i = 34; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
            }
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 7)    //if the first day of the month is a saturday
        {
            /*the following if statements and nested loops create the calendar display based on the number of days in the month,
             altering the visibilities of buttons as well as the text values*/
            if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 2 || calendar.get(Calendar.MONTH) == 4 || calendar.get(Calendar.MONTH) == 6 || calendar.get(Calendar.MONTH) == 7 || calendar.get(Calendar.MONTH) == 9 || calendar.get(Calendar.MONTH) == 11)
            {
                for (int i = 0; i < 6; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 6; i < 37; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-5));
                }
                for (int i = 37; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 3 || calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 8 || calendar.get(Calendar.MONTH) == 10)
            {
                for (int i = 0; i < 6; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                for (int i = 6; i < 36; i++)
                {
                    buttonArray[i].setText(String.valueOf(i-5));
                }
                for (int i = 36; i < 42; i++)
                {
                    buttonArray[i].setVisible(false);
                }
            }
            else if (calendar.get(Calendar.MONTH) == 1)
            {
                for (int i = 0; i < 6; i++)
                {
                    buttonArray[i].setVisible(false);
                }
                if (leapYearTest() == false)
                {
                    for (int i = 6; i < 34; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-5));
                    }
                    for (int i = 34; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
                else if (leapYearTest() == true)
                {
                    for (int i = 6; i < 35; i++)
                    {
                        buttonArray[i].setText(String.valueOf(i-5));
                    }
                    for (int i = 35; i < 42; i++)
                    {
                        buttonArray[i].setVisible(false);
                    }
                }
            }
        }

        calendar.set(Calendar.DAY_OF_MONTH, temp);  //set the day of the calendar back to the current date
        for (int i = 0; i < 42; i++)    //traverse the buttonArray
        {
            buttonArray[i].setBackground(null); //set the background of the button to the default
            if (appointmentOnDate[i] == true)
            {
                buttonArray[i].setOpaque(true);
                buttonArray[i].setBackground(Color.BLUE);
            }
            if (buttonArray[i].getText().equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))))   //if the text on the buttonArray specified matches the value of the day of the month
            {
                buttonArray[i].setOpaque(true); //set the button opaque to equal true
                buttonArray[i].setBackground(Color.RED);    //set the background color to red
            }
        }
    }
}