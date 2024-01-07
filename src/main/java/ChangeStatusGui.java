import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChangeStatusGui extends JPanel{
    private JList<JPanel> projectList;
    private DefaultListModel<JPanel> listModel;
    private ArrayList<Event> eventList;
    private JTextField enterTitle;
    private JButton searchTitle;

    public ChangeStatusGui(ArrayList<Event> events) {

        setPreferredSize(new Dimension(300, 450));
        listModel = new DefaultListModel<>();
        this.searchTitle = new JButton("Search");
        this.searchTitle.addActionListener(new ButtonListener());
        this.eventList = events;
        this.enterTitle = new JTextField("Enter Project Title", 10);
        this.enterTitle.setMaximumSize(new Dimension(300, enterTitle.getPreferredSize().height));
        this.enterTitle.addFocusListener(new ClearTextFocusListener("Enter Project Title", enterTitle));

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
                    handleSelection((Project) eventList.get(selectedIndex));
                }
            }
        });

        JScrollPane scrollPane =  new JScrollPane(projectList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        add(scrollPane, BorderLayout.CENTER);

    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.clear();
            for (Event event : eventList) {
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
        int selectedIndex = eventList.indexOf(project);

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

    private class ClearTextFocusListener implements FocusListener {
        private final String defaultText;
        private final JTextComponent textComponent;

        public ClearTextFocusListener(String defaultText, JTextComponent textComponent) {
            this.defaultText = defaultText;
            this.textComponent = textComponent;
        }

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