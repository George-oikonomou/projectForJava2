package  GUI;

import Utilities.*;
import Models.*;
import Models.Event;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class EditEventGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private final DefaultListModel<JPanel> listModel;
    private final JTextField enterTitle;
    private final JButton searchTitle;
    private final SingleCalendarSelect calendarSelect;
    private JTextArea titleField;
    private JTextArea descriptionField;
    private JDateChooser endDateChooser = null;
    private JDateChooser startDateChooser;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JScrollPane titleScrollPane;
    private JScrollPane descriptionScrollPane;
    private JList<JPanel> eventList;
    private Event eventToEdit;
    private ArrayList<Event> events;
    private JPanel editPanel;

    public EditEventGUI(ArrayList<ICSFile> allFiles) {
        setPreferredSize(new Dimension(300, 460));

        this.allFiles = allFiles;

        listModel = new DefaultListModel<>();
        searchTitle = new JButton("Search");
        enterTitle = new JTextField("Enter Project Title", 10);
        enterTitle.addFocusListener(new ClearTextFocusListener("Enter Project Title", enterTitle));
        calendarSelect = new SingleCalendarSelect(allFiles);
        setupUI();
    }

    private void setupUI() {
        if(enterTitle.getText().equals("Enter Project Title"))
            searchTitle.setEnabled(false);
        if (calendarSelect.isEmpty()) {
            searchTitle.setEnabled(false);
        } else {
            calendarSelect.addActionListener(e -> fillProjects());
            calendarSelect.setSelectedIndex(0);
            enterTitle.getDocument().addDocumentListener(new LiveSearchListener());
        }
        searchTitle.addActionListener(e -> {
            listModel.clear();
            for (Event event : events) {
                if (event.getTitle().equalsIgnoreCase(enterTitle.getText())) {
                    listModel.addElement(event.getPanel());
                }
            }
            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "There are no events with title " + enterTitle.getText(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addComponents();
    }

    private void addComponents() {
        add(calendarSelect);
        add(new JSeparator(SwingConstants.HORIZONTAL) {{
            setPreferredSize(new Dimension(4096, 1));
            setForeground(Color.BLACK);
        }});
        add(enterTitle);
        add(searchTitle);

        eventList = new JList<>(listModel);
        eventList.setPreferredSize(new Dimension(230, 1));
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventList.setCellRenderer(new PanelListCellRenderer());
        eventList.setFixedCellHeight(100);
        eventList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (eventList.getSelectedIndex() != -1)
                    FindSelectedEvent();
            }
        });

        JScrollPane scrollPane = new JScrollPane(eventList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void performLiveSearch() {
        searchTitle.setEnabled(true);
        String searchText = enterTitle.getText().toLowerCase();
        listModel.clear();
        if (searchText.isEmpty()){
            eventList.setPreferredSize(new Dimension(230, 1));
            searchTitle.setEnabled(false);
            return;
        }

        for (Event event : events) {
            if (event.getTitle().toLowerCase().startsWith(searchText)) {
                listModel.addElement(event.getPanel());
            }
        }
        eventList.setPreferredSize(new Dimension(230, listModel.size() * 100));
    }

    private void fillProjects() { events = allFiles.get(calendarSelect.getSelectedIndex()).getCalendar().getEvents(); }

    private void FindSelectedEvent() {
        JPanel selectedPanel = listModel.getElementAt(eventList.getSelectedIndex());

        String uid = (String) selectedPanel.getClientProperty("uid");

        for (Event event : events) {
            if (event.getUuid().equals(uid)) {
                    eventToEdit = event;
                    CreateEditModal();
                    break;
            }
        }
    }
    private void CreateEditModal(){
        editPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editPanel.setPreferredSize(new Dimension(400, 300));

        initializeFields();
    }
    private void initializeFields(){
        titleField = new JTextArea(eventToEdit.getTitle(),2,10);
        titleScrollPane = new JScrollPane(titleField,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        titleScrollPane.setPreferredSize(new Dimension(250,40));

        descriptionField = new JTextArea(eventToEdit.getDescription(), 5, 20);
        descriptionScrollPane = new JScrollPane(descriptionField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setPreferredSize(new Dimension(400, 100));

        startDateChooser = DateTimeManager.configureDate(100, 20);// Start Date Configuration
        startTimeSpinner = DateTimeManager.configureTime(40, 20);// Start Time Configuration

        endDateChooser = DateTimeManager.configureDate(100, 20);// End Date Configuration
        endTimeSpinner = DateTimeManager.configureTime(40, 20);// End Time Configuration

        startDateChooser.addPropertyChangeListener("date", new DateTimeManager.StartDateChangeListener(endDateChooser));

        if (eventToEdit instanceof Appointment) {
            startDateChooser.setDate(eventToEdit.getStartDate().getDateFormat());
            endDateChooser.setDate(((Appointment) eventToEdit).getEndDate().getDateFormat());
            startTimeSpinner.setValue(eventToEdit.getStartDate().getDateFormat());
            endTimeSpinner.setValue(((Appointment) eventToEdit).getEndDate().getDateFormat());
        } else if (eventToEdit instanceof Project) {
            endDateChooser.setDate(((Project) eventToEdit).getDue().getDateFormat());
            endTimeSpinner.setValue(((Project) eventToEdit).getDue().getDateFormat());
        }

        AddToPanel();
    }

    public void AddToPanel(){


        editPanel.add(new JLabel("NEW title of the event"));
        editPanel.add(titleScrollPane);
        editPanel.add(new JSeparator(SwingConstants.HORIZONTAL) {{
            setPreferredSize(new Dimension(380, 1));
        }});
        editPanel.add(new JLabel("NEW description of the event"));
        editPanel.add(descriptionScrollPane);
        if(eventToEdit instanceof Appointment) {
            editPanel.add(new JLabel("Enter the start date of the event"));
            editPanel.add(startDateChooser);
            editPanel.add(startTimeSpinner);
            editPanel.add(new JLabel("Enter the  end  date of the event"));
            editPanel.add(endDateChooser);
            editPanel.add(endTimeSpinner);
        }else {
            editPanel.add(new JLabel("Enter the due date of the event"));
            editPanel.add(endDateChooser);
            editPanel.add(endTimeSpinner);
        }
        int response = JOptionPane.showConfirmDialog(this, editPanel, "Edit Event",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (response == JOptionPane.OK_OPTION) {
           HandleOkResponse();
        }
        eventList.clearSelection();
    }

    private void HandleOkResponse(){
        if((startDateChooser.getDate() == null && eventToEdit instanceof Appointment)   || endDateChooser.getDate() == null || titleField.getText().isEmpty()){
            JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        OurDateTime endDateTime = DateTimeManager.extractDateTime(endDateChooser, endTimeSpinner);

        if (startDateChooser.getDate()!=null && eventToEdit instanceof Appointment) {
            OurDateTime startDateTime = DateTimeManager.extractDateTime(startDateChooser, startTimeSpinner);
            if (Validate.Dates(startDateTime, endDateTime)) return;
            ((Appointment) eventToEdit).setDurationWithDtend(startDateTime, endDateTime);
            eventToEdit.setStartDate(startDateTime);
            ((Appointment) eventToEdit).setEndDate(endDateTime);

        }
        // Update the event details based on the user's input
        eventToEdit.setTitle(titleField.getText());
        eventToEdit.setDescription(descriptionField.getText());
        //cast appointment to event
        if (eventToEdit instanceof Project)
            ((Project) eventToEdit).setDue(endDateTime);
        eventToEdit.setPanel();
        performLiveSearch();
    }

    private class LiveSearchListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) { performLiveSearch(); }

        @Override
        public void removeUpdate(DocumentEvent e) { performLiveSearch(); }

        @Override
        public void changedUpdate(DocumentEvent e) {}
    }
}