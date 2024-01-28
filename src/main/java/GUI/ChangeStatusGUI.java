package  GUI;

import Utilities.ClearTextFocusListener;
import Utilities.PanelListCellRenderer;
import Models.ICSFile;
import Models.Project;
import Utilities.SingleCalendarSelect;
import net.fortuna.ical4j.model.property.Status;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChangeStatusGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private JList<JPanel> projectList;
    private final DefaultListModel<JPanel> listModel;
    private final JTextField enterTitle;
    private final JButton searchTitle;
    private final SingleCalendarSelect calendarSelect;
    private ArrayList<Project> projects;


    public ChangeStatusGUI(ArrayList<ICSFile> allFiles) {
        setPreferredSize(new Dimension(300, 460));

        this.allFiles = allFiles;

        listModel = new DefaultListModel<>();
        searchTitle = new JButton("Search");
        enterTitle = new JTextField("Enter Project Title", 10);
        enterTitle.addFocusListener(new ClearTextFocusListener("Enter Project Title", enterTitle));
        calendarSelect = new SingleCalendarSelect(allFiles);
        setupUI();
    }

    public void setupUI() {
        if(enterTitle.getText().equals("Enter Project Title"))
            searchTitle.setEnabled(false);
        if (calendarSelect.isEmpty()) {
            searchTitle.setEnabled(false);
        } else {
            calendarSelect.addActionListener(e -> fillProjects());
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

        projectList = new JList<>(listModel);
        projectList.setPreferredSize(new Dimension(230, 1));
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.setCellRenderer(new PanelListCellRenderer());
        projectList.setFixedCellHeight(100);
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (projectList.getSelectedIndex() != -1) {
                    FindSelectedProject();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(projectList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        add(scrollPane, BorderLayout.CENTER);
    }



    private void performLiveSearch() {
        if (projectList == null) return;

        searchTitle.setEnabled(true);
        String searchText = enterTitle.getText().toLowerCase();
        listModel.clear();
        if (searchText.isEmpty()) {
            projectList.setPreferredSize(new Dimension(230, 1));
            searchTitle.setEnabled(false);
            return;
        }

        for (Project project : projects) {
            if (project.getTitle().toLowerCase().startsWith(searchText)) {
                listModel.addElement(project.getPanel());
            }
        }
        projectList.setPreferredSize(new Dimension(230, listModel.size() * 100));

    }

    private void fillProjects() {
        projects = allFiles.get(calendarSelect.getSelectedIndex()).getCalendar().getProjects();
        performLiveSearch();
    }

    private void FindSelectedProject() {
        JPanel selectedPanel = listModel.getElementAt(projectList.getSelectedIndex());

        String uid = (String) selectedPanel.getClientProperty("uid");

        for (Project project : projects) {
            if (project.getUuid().equals(uid)) {
                handleSelection(project);
                break;
            }
        }
    }

    private void handleSelection(Project project) {
        // Toggle the status of the project
        if (project.getStatus() == Status.VTODO_IN_PROCESS) {
            project.setStatus(Status.VTODO_COMPLETED);
        } else {
            project.setStatus(Status.VTODO_IN_PROCESS);
        }
        project.setPanel();
        performLiveSearch();
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.clear();
            for (Project project : projects) {
                if (project.getTitle().equalsIgnoreCase(enterTitle.getText())) {
                    listModel.addElement(project.getPanel());
                }
            }

            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "There are no Projects with title " + enterTitle.getText(), "Error", JOptionPane.ERROR_MESSAGE);
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
