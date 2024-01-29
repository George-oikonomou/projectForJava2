package  GUI;

import Models.Event;
import Models.Project;
import Utilities.ClearTextFocusListener;
import Utilities.DateTimeManager;
import Utilities.SingleCalendarSelect;
import Models.ICSFile;
import Models.OurDateTime;
import com.toedter.calendar.JDateChooser;
import net.fortuna.ical4j.model.property.Status;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private final JTextField title;
    private final JTextArea description;
    private final JDateChooser due;
    private final JSpinner dueTimeSpinner;
    private final JButton create;
    private final JScrollPane descriptionScrollPane;
    private final SingleCalendarSelect calendarSelect;

    public ProjectGUI(ArrayList<ICSFile> allFiles) {

        this.allFiles = allFiles;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(410, 300));

        // Creating a JDateChooser object
        this.due = new JDateChooser();
        JTextField dateTextField = ((JTextField) due.getDateEditor().getUiComponent());
        dateTextField.setEditable(false);
        due.setPreferredSize(new Dimension(100, 20));


        // title and description
        this.title = new JTextField("Project Name", 10);
        this.title.setMaximumSize(new Dimension(300, title.getPreferredSize().height));
        this.title.addFocusListener(new ClearTextFocusListener("Project Name", title));

        this.description = new JTextArea("Project Description", 5, 20);
        this.description.addFocusListener(new ClearTextFocusListener("Project Description", description));
        this.create = new JButton("Create");
        this.create.addActionListener(new ProjectGUI.ButtonListener());

        // Use a JScrollPane for the JTextArea to enable scrolling if needed
        descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));

        // Creating a JSpinner object
        this.dueTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dueTimeEditor = new JSpinner.DateEditor(dueTimeSpinner, "HH:mm");
        dueTimeSpinner.setEditor(dueTimeEditor);
        JFormattedTextField spinnerEditor = ((JSpinner.DefaultEditor) dueTimeSpinner.getEditor()).getTextField();
        spinnerEditor.setEditable(false);
        spinnerEditor.setPreferredSize(new Dimension(40, 20));

        this.calendarSelect = new SingleCalendarSelect(allFiles);

        // If there are no calendars, disable the create button
        if (calendarSelect.isEmpty()) create.setEnabled(false);

        addComponents();
    }

    /**
     * This method is used to place the GUI components in the correct position
     */
    private void addComponents(){
        add(new JLabel("Enter the due date of the project"));
        add(due);
        add(dueTimeSpinner);
        add(title);
        add(descriptionScrollPane);
        add(calendarSelect);
        add(create);
    }


    /**
     * This method is used to create a new project
     */
    private void createProject() {

        if(due.getDate() == null  || title.getText().equals("Project Name") || description.getText().equals("Project Description")){
            JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList<Event> events = allFiles.get(calendarSelect.getSelectedIndex()).getCalendar().getEvents();
        // Creating OurDateTime objects for due
        OurDateTime dueDateTime = DateTimeManager.extractDateTime(due, dueTimeSpinner);
        // Now, create a Project object
        events.add(new Project(title.getText(),description.getText(),dueDateTime, Status.VTODO_IN_PROCESS, allFiles.get(calendarSelect.getSelectedIndex()).getFileName()));

        JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "Project created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
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