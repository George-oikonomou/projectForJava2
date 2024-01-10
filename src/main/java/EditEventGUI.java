import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditEventGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private final DefaultListModel<JPanel> listModel;
    private final JTextField enterTitle;
    private final JButton searchTitle;
    private final SingleCalendarSelect calendarSelect;
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
                JOptionPane.showMessageDialog(null, "There are no events with title " + enterTitle.getText(),
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

        return panel;
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
}