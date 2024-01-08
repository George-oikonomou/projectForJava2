import com.toedter.calendar.JDateChooser;
import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProjectGui extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private final JTextField title;
    private final JTextArea description;
    private final JDateChooser due;
    private final JSpinner dueTimeSpinner;
    private final JButton create;
    private final JComboBox<String> icsFilesDropdown;

    public ProjectGui(ArrayList<ICSFile> allFiles) {

        this.allFiles = allFiles;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(410, 350));

        this.due = new JDateChooser();
        JTextField dateTextField = ((JTextField) due.getDateEditor().getUiComponent());
        dateTextField.setEditable(false);
        due.setPreferredSize(new Dimension(100, 20));

        // Set the minimum date for the dueDateChooser to January 1, 2024
        Calendar minDateCalendar = Calendar.getInstance();
        minDateCalendar.set(2024, Calendar.JANUARY, 1);
        due.setMinSelectableDate(minDateCalendar.getTime());

        this.title = new JTextField("Project Name", 10);
        this.title.setMaximumSize(new Dimension(300, title.getPreferredSize().height));
        this.title.addFocusListener(new ClearTextFocusListener("Project Name", title));

        this.description = new JTextArea("Project Description", 5, 20);
        this.description.addFocusListener(new ClearTextFocusListener("Project Description", description));
        this.create = new JButton("Create");
        this.create.addActionListener(new ProjectGui.ButtonListener());

        // Use a JScrollPane for the JTextArea to enable scrolling if needed
        JScrollPane descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));

        this.dueTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dueTimeEditor = new JSpinner.DateEditor(dueTimeSpinner, "HH:mm");
        dueTimeSpinner.setEditor(dueTimeEditor);
        JFormattedTextField spinnerEditor = ((JSpinner.DefaultEditor) dueTimeSpinner.getEditor()).getTextField();
        spinnerEditor.setEditable(false);
        spinnerEditor.setPreferredSize(new Dimension(40, 20));

        this.icsFilesDropdown = new JComboBox<>();

        for (ICSFile icsFile : allFiles) {
            icsFilesDropdown.addItem(icsFile.getFileName());
        }
        if (allFiles.isEmpty()) {
            icsFilesDropdown.addItem("No ICS Files");
            create.setEnabled(false);
        }

        add(new JLabel("Enter the due date of the project"));
        add(due);
        add(dueTimeSpinner);
        add(title);
        add(descriptionScrollPane);
        add(icsFilesDropdown);
        add(create);
    }

    public void createProject() {
        Date dueTime = (Date) dueTimeSpinner.getValue();
        Calendar dueCalendar = Calendar.getInstance();
        dueCalendar.setTime(dueTime);
        int dueHour = dueCalendar.get(Calendar.HOUR_OF_DAY);
        int dueMinute = dueCalendar.get(Calendar.MINUTE);

        Validate.Input(due, title, description);
        if (allFiles.isEmpty() ){
            JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList<Event> events = allFiles.get(icsFilesDropdown.getSelectedIndex()).getCalendar().getEvents();

        // Extracting date and time components for due and end
        int dueYear = due.getCalendar().get(Calendar.YEAR);
        int dueMonth = due.getCalendar().get(Calendar.MONTH) + 1; // Month is zero-based
        int dueDay = due.getCalendar().get(Calendar.DAY_OF_MONTH);
        // Creating OurDateTime objects for due
        OurDateTime dueDateTime = new OurDateTime(dueYear, dueMonth, dueDay, dueHour, dueMinute);
        // Now, create an Appointment object
        events.add(new Project(title.getText(),description.getText(),dueDateTime, Status.VTODO_IN_PROCESS));
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createProject();
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
}