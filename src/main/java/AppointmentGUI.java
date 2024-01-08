import com.toedter.calendar.JDateChooser;
import org.apache.commons.lang3.time.DateUtils;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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
    private final JComboBox<String> icsFilesDropdown;

    public AppointmentGUI(ArrayList<ICSFile> allFiles) {
        this.allFiles = allFiles;
        setLayout(new FlowLayout(FlowLayout.LEFT));// Layout and Size Settings
        setPreferredSize(new Dimension(420, 250));
        
        this.title = new JTextField("Appointment Name", 10);
        this.title.addFocusListener(new ClearTextFocusListener("Appointment Name", title));

        this.description = new JTextArea("Appointment Description", 5, 20);
        this.description.addFocusListener(new ClearTextFocusListener("Appointment Description", description));
        this.descriptionScrollPane = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));


        startDateChooser = DateTimeManager.configureDate(100,20);// Start Date Configuration
        startDateChooser.addPropertyChangeListener("date", new StartDateChangeListener());
        startTimeSpinner = DateTimeManager.configureTime(40,20);// Start Time Configuration

        endDateChooser =  DateTimeManager.configureDate(100,20);// End Date Configuration
        endTimeSpinner = DateTimeManager.configureTime(40,20);// End Time Configuration
        this.icsFilesDropdown = new JComboBox<>();

        for (ICSFile icsFile : allFiles) {
          icsFilesDropdown.addItem(icsFile.getFileName());
        }

        this.create = new JButton("Create");

        if (allFiles.isEmpty()) {
            icsFilesDropdown.addItem("No ICS Files");
            create.setEnabled(false);
        }


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
        add(icsFilesDropdown);
        add(create);
    }

    public void createAppointment() {
        if(startDateChooser.getDate() == null  || title.getText().equals("Appointment Name") || description.getText().equals("Appointment Description") || endDateChooser.getDate() == null || allFiles.isEmpty() ){
            JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Event> events =  allFiles.get(icsFilesDropdown.getSelectedIndex()).getCalendar().getEvents();

        OurDateTime startDateTime = DateTimeManager.extractDateTime(startDateChooser, startTimeSpinner);
        OurDateTime endDateTime = DateTimeManager.extractDateTime(endDateChooser, endTimeSpinner);

        if (Validate.Dates(startDateTime, endDateTime)) return;

        events.add(new Appointment(startDateTime, endDateTime,title.getText(),description.getText()));

        JOptionPane.showMessageDialog(null, "Appointment created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        //restart the fields
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

    private record ClearTextFocusListener(String defaultText, JTextComponent textComponent) implements FocusListener {

        @Override
            public void focusGained(FocusEvent e) {
                if (textComponent.getText().equals(defaultText)) {
                    textComponent.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textComponent.getText().isEmpty()) {
                    textComponent.setText(defaultText);
                }
            }
        }

    private class StartDateChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Date newStartDate = (Date) evt.getNewValue();

            if (newStartDate == null) return;

            // Extract only the date part from newStartDate
            Date startDate = DateUtils.truncate(newStartDate, Calendar.DATE);

            // Check if both start and end dates have been selected
            if (startDate != null && endDateChooser.getDate() != null) {
                Date endDate = DateUtils.truncate(endDateChooser.getDate(), Calendar.DATE);

                if (endDate.before(startDate))
                    endDateChooser.setDate(null);
            }

            // Update the minimum date of endDateChooser when the start date changes
            endDateChooser.setMinSelectableDate(newStartDate);
        }
    }
}