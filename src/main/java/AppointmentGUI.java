import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AppointmentGUI extends JPanel {
    private JDateChooser startDateChooser;
    private final JSpinner startTimeSpinner;
    private JDateChooser endDateChooser;
    private JSpinner endTimeSpinner;
    private final JTextField title;
    private final JTextArea description;
    private final JButton create;

    public AppointmentGUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT));        // Layout and Size Settings
        setPreferredSize(new Dimension(420, 250));

        startDateChooser = configureStartDate();// Start Date Configuration
        startTimeSpinner = configureTime();// Start Time Configuration


        endDateChooser = configureEndDate();// End Date Configuration
        endTimeSpinner = configureTime();// End Time Configuration


        // Appointment Details
        this.title = new JTextField("Appointment Name", 10);
        this.description = new JTextArea("Appointment Description", 5, 20);
        this.create = new JButton("Create");

        JScrollPane descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));

        // Event Listeners
        this.title.addFocusListener(new ClearTextFocusListener("Appointment Name", title));
        this.description.addFocusListener(new ClearTextFocusListener("Appointment Description", description));
        this.create.addActionListener(new ButtonListener());
        startDateChooser.addPropertyChangeListener("date", new StartDateChangeListener());

        // GUI Components Placement
        add(new JLabel("Enter the start date of the appointment"));

        add(startDateChooser);
        add(startTimeSpinner);
        add(new JLabel("Enter the end date of the appointment"));
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



    private JDateChooser configureEndDate() {
        final JDateChooser endDateChooser = new JDateChooser();
        this.endDateChooser = new JDateChooser();
        JTextField endDateTextField = ((JTextField) endDateChooser.getDateEditor().getUiComponent());
        endDateTextField.setEditable(false);
        endDateChooser.setPreferredSize(new Dimension(100, 20));

        endDateChooser.setMinSelectableDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());
        return endDateChooser;
    }



    private JDateChooser configureStartDate() {
        final JDateChooser startDateChooser = new JDateChooser();
        this.startDateChooser = new JDateChooser();
        startDateChooser.setPreferredSize(new Dimension(100, 20));
        JTextField startDateTextField = ((JTextField) startDateChooser.getDateEditor().getUiComponent());
        startDateTextField.setEditable(false);
        startDateChooser.setMinSelectableDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());
        return startDateChooser;
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {


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

            // Check if both start and end dates have been selected
            if (newStartDate != null && endDateChooser.getDate() != null) {

                if(endDateChooser.getDate().equals(newStartDate))
                    endDateChooser.setDate(newStartDate);
                else if (endDateChooser.getDate().before(newStartDate) && !endDateChooser.getDate().equals(newStartDate))
                    endDateChooser.setDate(null);

            }

            // Update the minimum date of endDateChooser when the start date changes
            endDateChooser.setMinSelectableDate(newStartDate);
        }
    }

}
