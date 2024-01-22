package  GUI;


import Utilities.ClearTextFocusListener;
import Models.ICSFile;
import Models.Event;
import Models.OurDateTime;
import Models.Appointment;
import Utilities.Validate;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AppointmentGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private final JDateChooser startDateChooser;
    private final JSpinner startTimeSpinner;
    private final JDateChooser endDateChooser;
    private final JSpinner endTimeSpinner;
    private final JTextField title;
    private final JTextArea description;
    private final JScrollPane descriptionScrollPane;
    private final JButton create;
    private final SingleCalendarSelect calendarSelect;

    public AppointmentGUI(ArrayList<ICSFile> allFiles) {

        this.allFiles = allFiles;
        setLayout(new FlowLayout(FlowLayout.LEFT));// Layout and Size Settings
        setPreferredSize(new Dimension(435, 300));
        
        this.title = new JTextField("Appointment Name", 10);
        this.title.addFocusListener(new ClearTextFocusListener("Appointment Name", title));

        this.description = new JTextArea("Appointment Description", 5, 20);
        this.description.addFocusListener(new ClearTextFocusListener("Appointment Description", description));
        this.descriptionScrollPane = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));

        startDateChooser = DateTimeManager.configureDate(100,20);// Start Date Configuration
        startTimeSpinner = DateTimeManager.configureTime(40,20);// Start Time Configuration

        endDateChooser =  DateTimeManager.configureDate(100,20);// End Date Configuration
        endTimeSpinner = DateTimeManager.configureTime(40,20);// End Time Configuration

        startDateChooser.addPropertyChangeListener("date", new DateTimeManager.StartDateChangeListener(endDateChooser));

        this.calendarSelect = new SingleCalendarSelect(allFiles);
        this.create = new JButton("Create");
        if (calendarSelect.isEmpty()) create.setEnabled(false);
        this.create.addActionListener(new ButtonListener());
        addComponents();// GUI Components Placement
    }

    private void addComponents() {
        add(new JLabel("Enter the start date of the appointment"));
        add(startDateChooser);
        add(startTimeSpinner);
        add(new JLabel("Enter the  end  date of the appointment"));
        add(endDateChooser);
        add(endTimeSpinner);
        add(title);
        add(descriptionScrollPane);
        add(new JLabel("Select ICS File:"));
        add(calendarSelect);
        add(create);
    }

    public void createAppointment() {
        if(startDateChooser.getDate() == null  || title.getText().equals("Appointment Name") || description.getText().equals("Appointment Description") || endDateChooser.getDate() == null || calendarSelect.isEmpty() ){
            JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Event> events =  allFiles.get(calendarSelect.getSelectedIndex()).getCalendar().getEvents();

        OurDateTime startDateTime = DateTimeManager.extractDateTime(startDateChooser, startTimeSpinner);
        OurDateTime endDateTime = DateTimeManager.extractDateTime(endDateChooser, endTimeSpinner);

        if (Validate.Dates(startDateTime, endDateTime)) return;

        events.add(new Appointment(startDateTime, endDateTime,title.getText(),description.getText()));

        JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "Appointment created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        title.setText("Appointment Name");
        description.setText("Appointment Description");
        startDateChooser.setDate(null);
        endDateChooser.setDate(null);
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            createAppointment();
        }
    }
}