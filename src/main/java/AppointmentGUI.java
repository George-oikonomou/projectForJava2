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
import java.util.GregorianCalendar;

public class AppointmentGUI extends JPanel {
    private JPanel printPanel;
    private JDateChooser startDateChooser;
    private final JSpinner startTimeSpinner;
    private JDateChooser endDateChooser;
    private JSpinner endTimeSpinner;
    private JTextField title;
    private JTextArea description;
    private JScrollPane descriptionScrollPane;
    private JButton create;
    private ArrayList<Event> events;

    public AppointmentGUI(ArrayList<Event> events, JPanel printPanel) {

        this.events = events;
        this.printPanel = printPanel;

        setLayout(new FlowLayout(FlowLayout.LEFT));// Layout and Size Settings
        setPreferredSize(new Dimension(420, 250));
        
        this.title = new JTextField("Appointment Name", 10);
        this.title.addFocusListener(new ClearTextFocusListener("Appointment Name", title));

        this.description = new JTextArea("Appointment Description", 5, 20);
        this.description.addFocusListener(new ClearTextFocusListener("Appointment Description", description));
        this.descriptionScrollPane = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));


        startDateChooser = configureDate();// Start Date Configuration
        startDateChooser.addPropertyChangeListener("date", new StartDateChangeListener());
        startTimeSpinner = configureTime();// Start Time Configuration

        endDateChooser = configureDate();// End Date Configuration
        endTimeSpinner = configureTime();// End Time Configuration

        this.create = new JButton("Create");
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
        add(create);
    }


    private JSpinner configureTime() {
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
                 timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

        JFormattedTextField timeTextField = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
                            timeTextField.setEditable(false);
                            timeTextField.setPreferredSize(new Dimension(40, 20));

        return timeSpinner;
    }

    private JDateChooser configureDate() {
        JDateChooser DateChooser = new JDateChooser();

        DateChooser.setPreferredSize(new Dimension(100, 20));
        DateChooser.setMinSelectableDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());

        JTextField startDateTextField = ((JTextField) DateChooser.getDateEditor().getUiComponent());
                   startDateTextField.setEditable(false);

        return DateChooser;
    }

    public void createAppointment() {
        Date startTime = (Date) startTimeSpinner.getValue();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = startCalendar.get(Calendar.MINUTE);


        if ( startDateChooser.getDate() == null || endDateChooser.getDate() == null ||title.getText().equals("Appointment Name") || description.getText().equals("Appointment Description")) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Extracting date and time components for start and end
        int startYear = startDateChooser.getCalendar().get(Calendar.YEAR);
        int startMonth = startDateChooser.getCalendar().get(Calendar.MONTH) + 1; // Month is zero-based
        int startDay = startDateChooser.getCalendar().get(Calendar.DAY_OF_MONTH);

        int endYear = endDateChooser.getCalendar().get(Calendar.YEAR);
        int endMonth = endDateChooser.getCalendar().get(Calendar.MONTH) + 1;
        int endDay = endDateChooser.getCalendar().get(Calendar.DAY_OF_MONTH);

        Date endTime = (Date) endTimeSpinner.getValue();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endTime);
        int endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        int endMinute = endCalendar.get(Calendar.MINUTE);


        // Creating OurDateTime objects for start and end
        OurDateTime startDateTime = new OurDateTime(startYear, startMonth, startDay, startHour, startMinute);
        OurDateTime endDateTime = new OurDateTime(endYear, endMonth, endDay, endHour, endMinute);

        if (startDateTime.getCalculationFormat() > endDateTime.getCalculationFormat()){
            JOptionPane.showMessageDialog(null, "Start date cant be after end date","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        printPanel.removeAll();
        printPanel.revalidate();
        printPanel.repaint();
        // Now, create an Appointment object
        events.add(new Appointment(startDateTime, endDateTime,title.getText(),description.getText()));
        JOptionPane.showMessageDialog(null,"created new Appointment " + title.getText() );
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            createAppointment();

        }
    }

    private class ClearTextFocusListener implements FocusListener {
        private final String defaultText;
        private final JTextComponent textComponent;

        public ClearTextFocusListener(String defaultText, JTextComponent textComponent) {
            this.defaultText = defaultText;
            this.textComponent = textComponent;
        }

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
