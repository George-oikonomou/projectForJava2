import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PrintGUI extends JPanel {

    private JList<JPanel> printList;
    private  DefaultListModel<JPanel> listModel;
    private static final ArrayList<ICSFile> selectedFiles = new ArrayList<>();
    private static final ArrayList <Event> AllSelectedCalendarEvents = new ArrayList<>();
    public PrintGUI(ArrayList<Event> eventList) {
        setPreferredSize(new Dimension(250,400));
        listModel = new DefaultListModel<>();

        for (Event event : eventList) {
            JPanel panel = createPanelForEvent(event);
            listModel.addElement(panel);
        }

        printList = new JList<>(listModel);

        printList.setPreferredSize(new Dimension(230, (eventList.size() == 0 ?  1 : eventList.size()) *78 ));
        printList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        printList.setCellRenderer(new PanelListCellRenderer());

        printList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = printList.getSelectedIndex();
                if (selectedIndex != -1) {
                    JPanel selectedPanel = listModel.getElementAt(selectedIndex);
                    handleSelection(selectedPanel);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(printList);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createPanelForEvent(Event event) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(100, 80)); // Adjust the size as needed
        if (event instanceof Appointment appointment){
            panel.add(new JLabel("Title: " + appointment.getTitle()));
            panel.add(new JLabel("Description: " + appointment.getDescription()));
            panel.add(new JLabel("Start Date: " + appointment.getStartDate().toString()));
            panel.add(new JLabel("End Date: " + ((Appointment) event).getEndDate().toString()));
        }else if (event instanceof Project project) {
            panel.add(new JLabel("Title: " + project.getTitle()));
            panel.add(new JLabel("Description: " + project.getDescription()));
            panel.add(new JLabel("Due Date: " + project.getDue()));
            panel.add(new JLabel("Status: " + project.getStatus().toString()));
        }

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleSelection(panel);
                }
            }
        });

        return panel;
    }
    public static void printEvents(JPanel printPanel) {
        printPanel.removeAll();

        JButton day = new JButton("Day");
        JButton week = new JButton("Week");
        JButton month = new JButton("Month");
        JButton pastDay = new JButton("PastDay");
        JButton pastWeek = new JButton("PastWeek");
        JButton pastMonth = new JButton("PastMonth");
        JButton todo = new JButton("Todo");
        JButton due = new JButton("Due");
        JButton selectFiles = createStyledButton();


        printPanel.add(selectFiles);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(4096, 1));
        separator.setForeground(Color.BLACK);
        printPanel.add(separator);
        printPanel.add(day);
        printPanel.add(week);
        printPanel.add(month);
        printPanel.add(pastDay);
        printPanel.add(pastWeek);
        printPanel.add(pastMonth);
        printPanel.add(todo);
        printPanel.add(due);


        addActionListener(day, App.AppChoices.day, printPanel);
        addActionListener(week, App.AppChoices.week, printPanel);
        addActionListener(month, App.AppChoices.month, printPanel);
        addActionListener(pastDay, App.AppChoices.pastday, printPanel);
        addActionListener(pastWeek, App.AppChoices.pastweek, printPanel);
        addActionListener(pastMonth, App.AppChoices.pastmonth, printPanel);
        addActionListener(todo, App.AppChoices.todo, printPanel);
        addActionListener(due, App.AppChoices.due, printPanel);
        selectFiles.addActionListener(e -> selectMultipleFiles(printPanel));
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
    private static void selectMultipleFiles(JPanel printPanel) {
        selectedFiles.clear();

        DefaultListModel<ICSFile> allIcsFileListModel = new DefaultListModel<>();

        for (ICSFile icsFile : App.getAllIcsFiles()) {
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
    }

    private static void addActionListener(JButton button, App.AppChoices choice, JPanel printPanel) {
        button.addActionListener(e -> performAction(choice, printPanel));
    }

    private static void performAction(App.AppChoices choice, JPanel printPanel) {
        AllSelectedCalendarEvents.clear();
        printPanel.removeAll();
        for ( ICSFile icsFile : selectedFiles){
            AllSelectedCalendarEvents.addAll(icsFile.getCalendar().getEvents());
        }
        switch (choice) {
               case day, week, month -> printPanel.add(new PrintGUI(OurCalendar.printUpcomingEvents(choice, AllSelectedCalendarEvents)));
               case pastday, pastweek, pastmonth ->  printPanel.add(new PrintGUI(OurCalendar.printOldEvents(choice, AllSelectedCalendarEvents)));
               case todo, due -> printPanel.add(new PrintGUI(OurCalendar.printUnfinishedProject(choice, AllSelectedCalendarEvents)));
        }

        printPanel.revalidate();
        printPanel.repaint();
    }
    private void handleSelection(JPanel selectedPanel) {
        // Implement your logic when a panel is selected
    }

    private static class PanelListCellRenderer implements ListCellRenderer<JPanel> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
            value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return value;
        }
    }
}
