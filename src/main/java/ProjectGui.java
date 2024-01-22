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
    private final SingleCalendarSelect calendarSelect;

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

        this.calendarSelect = new SingleCalendarSelect(allFiles);

        if (calendarSelect.isEmpty()) create.setEnabled(false);


        add(new JLabel("Enter the due date of the project"));
        add(due);
        add(dueTimeSpinner);
        add(title);
        add(descriptionScrollPane);
        add(calendarSelect);
        add(create);
    }

    public void createProject() {

        Validate.Input(due, title, description);
        if (allFiles.isEmpty() ){
            JOptionPane.showMessageDialog(MainPage.getPrintPanel(), "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList<Event> events = allFiles.get(calendarSelect.getSelectedIndex()).getCalendar().getEvents();
        // Creating OurDateTime objects for due
        OurDateTime dueDateTime = DateTimeManager.extractDateTime(due, dueTimeSpinner);
        // Now, create a Project object
        events.add(new Project(title.getText(),description.getText(),dueDateTime, Status.VTODO_IN_PROCESS));

        JOptionPane.showMessageDialog(null, "Project created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        //restart the fields
        title.setText("Project Name");
        description.setText("Project Description");
        due.setDate(null);

    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createProject();
        }
    }
}