import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChangeStatusGui extends JPanel{
    private final ArrayList<ICSFile> allFiles;
    private final JList<JPanel> projectList;
    private final DefaultListModel<JPanel> listModel;
    private final JTextField enterTitle;
    private final JButton searchTitle;
    private static final ArrayList<ICSFile> selectedFiles = new ArrayList<>();
    private static final ArrayList <Event> AllSelectedCalendarEvents = new ArrayList<>();

    public ChangeStatusGui(ArrayList<ICSFile> allFiles) {

        this.allFiles = allFiles;
        setPreferredSize(new Dimension(300, 450));
        listModel = new DefaultListModel<>();
        this.searchTitle = new JButton("Search");
        this.searchTitle.addActionListener(new ButtonListener());
        this.enterTitle = new JTextField("Enter Project Title", 10);
        this.enterTitle.setMaximumSize(new Dimension(300, enterTitle.getPreferredSize().height));
        this.enterTitle.addFocusListener(new ClearTextFocusListener("Enter Project Title", enterTitle));
        JButton selectFiles = createStyledButton();


        add(selectFiles);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(4096, 1));
        separator.setForeground(Color.BLACK);
        add(enterTitle);
        add(searchTitle);
        selectFiles.addActionListener(e -> selectMultipleFiles(this));

        projectList = new JList<>(listModel);
        projectList.setPreferredSize(new Dimension(230,1500));
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.setCellRenderer(new PanelListCellRenderer());
        projectList.setFixedCellHeight(100);
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = projectList.getSelectedIndex();
                if (selectedIndex != -1) {
                    handleSelection((Project) AllSelectedCalendarEvents.get(selectedIndex));
                }
            }
        });

        JScrollPane scrollPane =  new JScrollPane(projectList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        add(scrollPane, BorderLayout.CENTER);

    }
    private static JButton createStyledButton() {
        JButton button = new JButton("Select calendars");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLUE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }
    private void selectMultipleFiles(JPanel printPanel) {
        selectedFiles.clear();
        AllSelectedCalendarEvents.clear();

        DefaultListModel<ICSFile> allIcsFileListModel = new DefaultListModel<>();

        for (ICSFile icsFile : allFiles) {
            allIcsFileListModel.addElement(icsFile);
        }

        JList<ICSFile> fileList = new JList<>(allIcsFileListModel);
        fileList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);

        int result = JOptionPane.showConfirmDialog(printPanel, scrollPane, "Select ICS Files", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int[] selectedIcsFilesIndices = fileList.getSelectedIndices();

            for (int i : selectedIcsFilesIndices) {
                selectedFiles.add(allIcsFileListModel.getElementAt(i));
            }
        }
        for ( ICSFile icsFile : selectedFiles){
            for (Event event : icsFile.getCalendar().getEvents()){
                if (event instanceof Project project){
                    AllSelectedCalendarEvents.add(project);
                }
            }

        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.clear();
            for (Event event : AllSelectedCalendarEvents) {
                if (event instanceof Project project) {
                    if (event.getTitle().equalsIgnoreCase(enterTitle.getText())){
                        JPanel panel = createPanelForProject(project);
                        listModel.addElement(panel);
                    }
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

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    handleSelection(project);
            }
        });

        return panel;


    }
    private void handleSelection(Project project) {
        // Toggle the status of the project
        if (project.getStatus() == Status.VTODO_IN_PROCESS) {
            project.setStatus(Status.VTODO_COMPLETED);
        } else {
            project.setStatus(Status.VTODO_IN_PROCESS);
        }

        // Find the index of the selected project in the listModel
        int selectedIndex = AllSelectedCalendarEvents.indexOf(project);

        // Remove the existing panel from the listModel
        listModel.remove(selectedIndex);

        // Create a new panel for the updated project
        JPanel updatedPanel = createPanelForProject(project);

        // Add the updated panel back to the listModel at the same index
        listModel.add(selectedIndex, updatedPanel);
    }
    private static class PanelListCellRenderer implements ListCellRenderer<JPanel> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
            value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return value;
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