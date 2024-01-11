import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReminderGUI extends JPanel {
    private final ArrayList<ICSFile> allFiles;
    private DefaultListModel<JPanel> listModel;
    private final ArrayList<Event> nextEvent;
    private JList<JPanel> printList;
    private final OurDateTime liveTime;
    private JScrollPane scrollPane;

    public ReminderGUI(ArrayList<ICSFile> allFiles) {
        this.nextEvent = new ArrayList<>();
        this.allFiles = allFiles;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(450,500));
        setBackground(Color.pink);
        liveTime = new OurDateTime();
        setNextEvent(this.allFiles);
        printEvents();
    }
    private void setNextEvent(ArrayList<ICSFile> allFiles){
        for (ICSFile file : allFiles){
            for (Event event : file.getCalendar().getEvents()){
                if (event instanceof Appointment appointment){
                    if (appointment.getStartDate().getCalculationFormat() >= liveTime.getCalculationFormat()) {
                        nextEvent.add(appointment);
                        break;
                    }
                }else if (event instanceof Project project){
                    if (project.getDue().getCalculationFormat() >= liveTime.getCalculationFormat()){
                        nextEvent.add(project);
                        break;
                    }
                }
            }
        }
    }

    public void printEvents(){

        listModel = new DefaultListModel<>();

        for (Event event : nextEvent) {
            JPanel panel = event.getPanel();
            listModel.addElement(panel);
        }
        printList = new JList<>(listModel);
        printList.setPreferredSize(new Dimension(230, (nextEvent.isEmpty() ? 1 : nextEvent.size() * 78)));
        printList.setCellRenderer(new PanelListCellRenderer());
        scrollPane = new JScrollPane(printList);
        scrollPane.setPreferredSize(new Dimension(250,350));
        add(scrollPane);
        revalidate();
        repaint();
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