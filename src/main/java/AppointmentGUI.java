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
    private JTextField title;
    private JTextArea description;
    private JScrollPane descriptionScrollPane;
    private JButton create;

    public AppointmentGUI() {
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
