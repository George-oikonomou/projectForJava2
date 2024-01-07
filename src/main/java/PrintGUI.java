import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PrintGUI extends JPanel {

    private JList<JPanel> printList;
    private DefaultListModel<JPanel> listModel;

    public PrintGUI(ArrayList<Event> eventList) {
        setPreferredSize(new Dimension(250,400));
        listModel = new DefaultListModel<>();

        for (Event event : eventList) {
            JPanel panel = createPanelForEvent(event);
            listModel.addElement(panel);
        }

        printList = new JList<>(listModel);
        printList.setPreferredSize(new Dimension(230,500));
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
            panel.add(new JLabel("Status: " + project.getStatus().getValue()));
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
