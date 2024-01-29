package GUI;
import Models.ICSFile;
import Utilities.ClearTextFocusListener;
import Utilities.PanelListCellRenderer;
import Utilities.SingleCalendarSelect;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EventManagement extends JPanel {
    private final String message;
    private final ArrayList<ICSFile> allFiles;
    private JList<JPanel> eventList;
    private final DefaultListModel<JPanel> listModel;
    private final JTextField enterTitle;
    private final JButton searchTitle;
    private final SingleCalendarSelect calendarSelect;

    public EventManagement(ArrayList<ICSFile> allFiles, String message){
        setPreferredSize(new Dimension(300,460));
        this.allFiles = allFiles;
        this.message = message;
        listModel = new DefaultListModel<>();
        searchTitle = new JButton("Search");
        enterTitle = new JTextField(message);
        enterTitle.addFocusListener(new ClearTextFocusListener(message,enterTitle));
        calendarSelect = new SingleCalendarSelect(allFiles);
        setupUI();
    }

    public void setupUI(){
        if(enterTitle.getText().equals(message))
            searchTitle.setEnabled(false);
        if (calendarSelect.isEmpty()) {
            searchTitle.setEnabled(false);
        }else {
            calendarSelect.addActionListener(e -> fillEvents());
            calendarSelect.setSelectedIndex(0);
            enterTitle.getDocument().addDocumentListener(new LiveSearchListener());
        }
        searchTitle.addActionListener(new ButtonListener());
        addComponents();
    }

    public void addComponents() {
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
                if (eventList.getSelectedIndex() != -1) {
                    findSelectedEvent();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(eventList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void fillEvents(){}

    public void findSelectedEvent(){}

    public void performLiveSearch() {
        if (eventList == null) return;
        searchTitle.setEnabled(true);
        String searchText = enterTitle.getText().toLowerCase();
        listModel.clear();
        if (searchText.isEmpty()) {
            eventList.setPreferredSize(new Dimension(230, 1));
            searchTitle.setEnabled(false);
            return;
        }
        search(searchText);
    }

    public void search(String searchText){}

    public DefaultListModel<JPanel> getListModel() {return listModel;}
    public JList<JPanel> getEventList() {return eventList;}
    public ArrayList<ICSFile> getAllFiles() {return allFiles;}
    public SingleCalendarSelect getCalendarSelect() {return calendarSelect;}
    public JTextField getEnterTitle() {return enterTitle;}

    private class LiveSearchListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) { performLiveSearch(); }
        @Override
        public void removeUpdate(DocumentEvent e) { performLiveSearch(); }
        @Override
        public void changedUpdate(DocumentEvent e) {}
    }

    public static class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}