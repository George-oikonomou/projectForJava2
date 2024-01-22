import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrintGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private JList<JPanel> printList;
    private  DefaultListModel<JPanel> listModel;
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

        listModel = new DefaultListModel<>();

        for (Event event : selectedEvents) {
            JPanel panel = event.getPanel();
            listModel.addElement(panel);
        }

        printList = new JList<>(listModel);

        printList.setPreferredSize(new Dimension(230, (selectedEvents.isEmpty() ?  1 : selectedEvents.size()) * 78 ));
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

    private void performAction(App.AppChoices choice) {
        if (selectedFiles.isEmpty()) {
            JOptionPane.showMessageDialog(MainPage.getPrintPanel(), "Please select at least one calendar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectedEvents.clear();
        if (scrollPane != null)
            remove(scrollPane);
        switch (choice) {
            case day, week, month -> {
                for (ICSFile icsFile : selectedFiles) {
                    selectedEvents.addAll(icsFile.getCalendar().printUpcomingEvents(choice));
                }
            }
            case pastday, pastweek, pastmonth -> {
                for (ICSFile icsFile : selectedFiles) {
                    selectedEvents.addAll(icsFile.getCalendar().printOldEvents(choice));
                }
            }
            case todo, due -> {
                for (ICSFile icsFile : selectedFiles) {
                    selectedEvents.addAll(icsFile.getCalendar().printUnfinishedProject(choice));
                }
            }
        }
        printEvents();
    }
}