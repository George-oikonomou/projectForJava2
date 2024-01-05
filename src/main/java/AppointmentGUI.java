import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class AppointmentGUI extends JPanel {
    private final JDateChooser startDateChooser;
    private final JSpinner startTimeSpinner;
    private final JDateChooser endDateChooser;
    private final JSpinner endTimeSpinner;
    private final JTextField title;
    private final JTextArea description;
    private final JButton create;

    public AppointmentGUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(410, 250));

        this.startDateChooser = new JDateChooser();
        JTextField dateTextField = ((JTextField) startDateChooser.getDateEditor().getUiComponent());
        dateTextField.setEditable(false);
        startDateChooser.setPreferredSize(new Dimension(100, 20));

        // Set the minimum date for the startDateChooser to January 1, 2024
        Calendar minDateCalendar = Calendar.getInstance();
        minDateCalendar.set(2024, Calendar.JANUARY, 1);
        startDateChooser.setMinSelectableDate(minDateCalendar.getTime());

        this.startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);
        JFormattedTextField spinnerEditor = ((JSpinner.DefaultEditor) startTimeSpinner.getEditor()).getTextField();
        JFormattedTextField spinnerTextField = spinnerEditor;
        spinnerTextField.setEditable(false);
        spinnerTextField.setPreferredSize(new Dimension(40, 20));

        this.endDateChooser = new JDateChooser();
        dateTextField = ((JTextField) endDateChooser.getDateEditor().getUiComponent());
        dateTextField.setEditable(false);
        endDateChooser.setPreferredSize(new Dimension(100, 20));

        // Set the minimum date for the endDateChooser to January 1, 2024
        endDateChooser.setMinSelectableDate(minDateCalendar.getTime());

        this.endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);
        spinnerEditor = ((JSpinner.DefaultEditor) endTimeSpinner.getEditor()).getTextField();
        spinnerTextField = spinnerEditor;
        spinnerTextField.setEditable(false);
        spinnerTextField.setPreferredSize(new Dimension(40, 20));

        this.title = new JTextField("Appointment Name", 10);
        this.description = new JTextArea("Appointment Description", 5, 20);
        this.create = new JButton("Create");

        JScrollPane descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));

        this.title.addFocusListener(new ClearTextFocusListener("Appointment Name", title));
        this.description.addFocusListener(new ClearTextFocusListener("Appointment Description", description));
        this.create.addActionListener(new ButtonListener());

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
}