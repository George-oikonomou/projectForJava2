package  GUI;


import Utilities.ClearTextFocusListener;
import Utilities.PanelListCellRenderer;
import Models.ICSFile;
import Models.Project;
import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChangeStatusGui extends JPanel{
    private final ArrayList<ICSFile> allFiles;
    private final JList<JPanel> projectList;
    private final DefaultListModel<JPanel> listModel;
    private final JTextField enterTitle;
    private final JButton searchTitle;
    private final SingleCalendarSelect calendarSelect;
    private ArrayList<Project> projects;


    public ChangeStatusGui(ArrayList<ICSFile> allFiles) {

        this.allFiles = allFiles;
        setPreferredSize(new Dimension(300, 450));
        listModel = new DefaultListModel<>();
        this.searchTitle = new JButton("Search");
        this.searchTitle.addActionListener(new ButtonListener());
        this.enterTitle = new JTextField("Enter Project Title", 10);
        this.enterTitle.setMaximumSize(new Dimension(300, enterTitle.getPreferredSize().height));
        this.enterTitle.addFocusListener(new ClearTextFocusListener("Enter Project Title", enterTitle));
        this.calendarSelect = new SingleCalendarSelect(allFiles);

        if (calendarSelect.isEmpty()){
            searchTitle.setEnabled(false);
        }else {
            calendarSelect.addActionListener(e -> fillProjects());
            calendarSelect.setSelectedIndex(0);
        }

        add(calendarSelect);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(4096, 1));
        separator.setForeground(Color.BLACK);
        add(enterTitle);
        add(searchTitle);

        projectList = new JList<>(listModel);
        projectList.setPreferredSize(new Dimension(230,1500));
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.setCellRenderer(new PanelListCellRenderer());
        projectList.setFixedCellHeight(100);
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = projectList.getSelectedIndex();
                if (selectedIndex != -1) {
                    handleSelection( projects.get(selectedIndex));
                }
            }
        });

        JScrollPane scrollPane =  new JScrollPane(projectList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        add(scrollPane, BorderLayout.CENTER);

    }

    private void fillProjects(){
        projects = allFiles.get(calendarSelect.getSelectedIndex()).getCalendar().getProjects();
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.clear();
            for (Project project : projects) {
                if (project.getTitle().equalsIgnoreCase(enterTitle.getText())){
                    JPanel panel = createPanelForProject(project);
                    listModel.addElement(panel);
                }
            }
        }
    }

    private JPanel createPanelForProject(Project project) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(100, 300)); // Adjust the size as needed
        JRadioButton radioButton = new JRadioButton();
        radioButton.setFocusable(true); // To prevent the radio button from gaining focus on mouse click
        panel.add(radioButton);
        panel.add(new JLabel("Title: " + project.getTitle()));
        panel.add(new JLabel("Description: " + project.getDescription()));
        panel.add(new JLabel("Due Date: " + project.getDue()));
        panel.add(new JLabel("Status: " + project.getStatus().getValue()));

        return panel;
    }
    private void handleSelection(Project project) {
        // Toggle the status of the project
        if (project.getStatus() == Status.VTODO_IN_PROCESS) {
            project.setStatus(Status.VTODO_COMPLETED);
        } else {
            project.setStatus(Status.VTODO_IN_PROCESS);
        }
        int selectedIndex = projects.indexOf(project);
        listModel.remove(selectedIndex);
        JPanel updatedPanel = createPanelForProject(project);
        listModel.add(selectedIndex, updatedPanel);
    }
}