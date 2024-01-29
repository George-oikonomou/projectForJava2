package  GUI;


import Main.App;
import Models.ICSFile;
import Models.Event;
import Models.OurDateTime;
import Models.Project;
import Utilities.PanelListCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrintGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private static final ArrayList<ICSFile> selectedFiles = new ArrayList<>();
    private static final ArrayList <Event> selectedEvents = new ArrayList<>();
    private JScrollPane scrollPane;

    public PrintGUI(ArrayList <ICSFile> allFiles) {
        this.allFiles = allFiles;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(450,500));
        setBackground(new Color(180, 250, 190));
        JButton day = new JButton("Day");
        JButton week = new JButton("Week");
        JButton month = new JButton("Month");
        JButton pastDay = new JButton("PastDay");
        JButton pastWeek = new JButton("PastWeek");
        JButton pastMonth = new JButton("PastMonth");
        JButton todo = new JButton("Todo");
        JButton due = new JButton("Due");
        JButton selectFiles = createStyledButton();

        this.add(selectFiles);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(4096, 1));
        separator.setForeground(Color.BLACK);
        add(separator);
        add(day);
        add(week);
        add(month);
        add(pastDay);
        add(pastWeek);
        add(pastMonth);
        add(todo);
        add(due);

        day.addActionListener(e -> performAction(App.AppChoices.day));
        week.addActionListener(e -> performAction(App.AppChoices.week));
        month.addActionListener(e -> performAction(App.AppChoices.month));
        pastDay.addActionListener(e -> performAction(App.AppChoices.pastday));
        pastWeek.addActionListener(e -> performAction(App.AppChoices.pastweek));
        pastMonth.addActionListener(e -> performAction(App.AppChoices.pastmonth));
        todo.addActionListener(e -> performAction(App.AppChoices.todo));
        due.addActionListener(e -> performAction(App.AppChoices.due));

        selectFiles.addActionListener(e -> selectMultipleFiles());
    }

    public void printEvents() {

        DefaultListModel<JPanel> listModel = new DefaultListModel<>();

        for (Event event : selectedEvents) {
            listModel.addElement( event.getPanel());
            listModel.addElement(new JPanel() {{
                setPreferredSize(new Dimension(0, 10));
            }});
        }

        JList<JPanel> printList = new JList<>(listModel);

        printList.setPreferredSize(new Dimension(230, selectedEvents.size() *90 ));
        printList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        printList.setCellRenderer(new PanelListCellRenderer());

        scrollPane = new JScrollPane(printList);
        scrollPane.setPreferredSize(new Dimension(250,350));
        add(scrollPane);
        revalidate();
        repaint();
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

    private void selectMultipleFiles() {
        selectedFiles.clear();

        DefaultListModel<ICSFile> allIcsFileListModel = new DefaultListModel<>();

        for (ICSFile icsFile : allFiles) {
            allIcsFileListModel.addElement(icsFile);
        }

        JList<ICSFile> fileList = new JList<>(allIcsFileListModel);
        fileList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);

        int result = JOptionPane.showConfirmDialog(this,scrollPane, "Select ICS Files", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int[] selectedIcsFilesIndices = fileList.getSelectedIndices();

            for (int i : selectedIcsFilesIndices) {
                selectedFiles.add(allIcsFileListModel.getElementAt(i));
            }
        }
    }

    public void sortList(ArrayList<Event> events, boolean ascending) {
        events.sort((event1, event2) -> (int) (ascending ? compareEvents(event1, event2)
                                                         : compareEvents(event2, event1)));
    }

    public long compareEvents(Event event1, Event event2) {
        OurDateTime startDate1 = (event1 instanceof Project) ? ((Project) event1).getDue() : event1.getStartDate();
        OurDateTime startDate2 = (event2 instanceof Project) ? ((Project) event2).getDue() : event2.getStartDate();

        return startDate1.getCalculationFormat() - startDate2.getCalculationFormat();
    }

    private void performAction(App.AppChoices choice) {
        if (selectedFiles.isEmpty()) {// If no files are selected
            JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "Please select at least one calendar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Clear the list of events
        selectedEvents.clear();
        if (scrollPane != null)// If there is a scroll pane, remove it
            remove(scrollPane);
        switch (choice) {// Add the events to the list sorted based on the choice and required logic ascending or descending
            case day, week, month -> {
                for (ICSFile icsFile : selectedFiles) {
                    selectedEvents.addAll(icsFile.getCalendar().printUpcomingEvents(choice));
                }
                sortList(selectedEvents, true);

            }
            case pastday, pastweek, pastmonth -> {
                for (ICSFile icsFile : selectedFiles) {
                    selectedEvents.addAll(icsFile.getCalendar().printOldEvents(choice));
                }
                sortList(selectedEvents, false);

            }
            case todo, due -> {
                for (ICSFile icsFile : selectedFiles) {
                    selectedEvents.addAll(icsFile.getCalendar().printUnfinishedProject(choice));
                }
                sortList(selectedEvents, choice == App.AppChoices.todo);

            }
        }
        printEvents();// Print the events
    }
}