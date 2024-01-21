import com.toedter.calendar.JDateChooser;
import org.apache.commons.lang3.time.DateUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditEventGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private final DefaultListModel<JPanel> listModel;
    private final JTextField enterTitle;
    private final JButton searchTitle;
    private final SingleCalendarSelect calendarSelect;
    private  JScrollPane descriptionScrollPane = null;
    JDateChooser endDateChooser = null;
    private JList<JPanel> eventList;
    private ArrayList<Event> events;

    public EditEventGUI(ArrayList<ICSFile> allFiles) {
        setPreferredSize(new Dimension(300, 460));

        this.allFiles = allFiles;

        listModel = new DefaultListModel<>();
        searchTitle = new JButton("Search");
        enterTitle = new JTextField("Enter Project Title", 10);
        enterTitle.addFocusListener(new ClearTextFocusListener("Enter Project Title", enterTitle,searchTitle));
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
                    listModel.addElement(createPanelForEvent(event));
                }
            }
            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(MainPage.getPrintPanel(), "There are no events with title " + enterTitle.getText(),
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
                int selectedIndex = eventList.getSelectedIndex();
                if (selectedIndex != -1) {
                    showEditPanel();
                }
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
                listModel.addElement(createPanelForEvent(event));
            }
        }
        eventList.setPreferredSize(new Dimension(230, listModel.size() * 100));
    }

    private void fillProjects() { events = allFiles.get(calendarSelect.getSelectedIndex()).getCalendar().getEvents(); }

    private JPanel createPanelForEvent(Event event) {

        JPanel panel = new JPanel();
        JRadioButton radioButton = new JRadioButton();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(100, 300));
        radioButton.setFocusable(true);
        panel.add(radioButton);
        panel.add(new JLabel("Title: " + event.getTitle()));
        panel.add(new JLabel("Description: " + event.getDescription()));

        if (event instanceof Appointment) {
            panel.add(new JLabel("Start Date: " + event.getStartDate().toString()));
            panel.add(new JLabel("End Date: " + ((Appointment) event).getEndDate().toString()));
        } else if (event instanceof Project){
            panel.add(new JLabel("Due Date: " +  ((Project) event).getDue()));
            panel.add(new JLabel("Status: " + ((Project) event).getStatus().getValue()));
        }
        panel.putClientProperty("uid", event.getUuid());

        return panel;
    }

    private void showEditPanel() {
        int selectedIndex = eventList.getSelectedIndex();

        if (selectedIndex != -1) {
            JPanel selectedPanel = listModel.getElementAt(selectedIndex);

            String uid = (String) selectedPanel.getClientProperty("uid");

            Event eventToEdit = null;
            for (Event event : events) {
                if (event.getUuid().equals(uid)) {
                    eventToEdit = event;
                    break;
                }
            }

            JDateChooser startDateChooser;
            JSpinner startTimeSpinner;

            JSpinner endTimeSpinner;
            if (eventToEdit != null) {
                JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                editPanel.setPreferredSize(new Dimension(400, 250));

                JTextArea titleField = new JTextArea(eventToEdit.getTitle(),2,10);
                JScrollPane titleScrollPane = new JScrollPane(titleField,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                titleScrollPane.setPreferredSize(new Dimension(250,40));

                JTextArea descriptionField = new JTextArea(eventToEdit.getDescription(), 5, 20);

                JScrollPane descriptionScrollPane = new JScrollPane(descriptionField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                descriptionScrollPane.setPreferredSize(new Dimension(400, 100));

                startDateChooser = DateTimeManager.configureDate(100, 20);// Start Date Configuration
                startDateChooser.addPropertyChangeListener("date", new StartDateChangeListener());
                startTimeSpinner = DateTimeManager.configureTime(40, 20);// Start Time Configuration

                endDateChooser = DateTimeManager.configureDate(100, 20);// End Date Configuration
                endTimeSpinner = DateTimeManager.configureTime(40, 20);// End Time Configuration

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
                int result = JOptionPane.showConfirmDialog(this, editPanel, "Edit Event",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    if((startDateChooser.getDate() == null && eventToEdit instanceof Appointment)   || endDateChooser.getDate() == null || titleField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(MainPage.getPrintPanel(), "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
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

                    performLiveSearch();
                }
            }
        }
    }



    private static class PanelListCellRenderer implements ListCellRenderer<JPanel> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return value;
        }
    }

    private record ClearTextFocusListener(String defaultText, JTextComponent textComponent,JButton searchTitle) implements FocusListener {
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
                this.searchTitle.setEnabled(false);
            }
        }
    }

    private class LiveSearchListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) { performLiveSearch(); }

        @Override
        public void removeUpdate(DocumentEvent e) { performLiveSearch(); }

        @Override
        public void changedUpdate(DocumentEvent e) {}
    }

    private class StartDateChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Date newStartDate = (Date) evt.getNewValue();

            if (newStartDate == null) return;

            Date startDate = DateUtils.truncate(newStartDate, Calendar.DATE);
            if (startDate != null && endDateChooser.getDate() != null) {
                Date endDate = DateUtils.truncate(endDateChooser.getDate(), Calendar.DATE);

                if (endDate.before(startDate))
                    endDateChooser.setDate(null);
            }
            endDateChooser.setMinSelectableDate(newStartDate);
        }
    }
}