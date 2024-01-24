package GUI;

import Utilities.PanelListCellRenderer;
import Models.Appointment;
import Models.Event;
import Models.Project;
import Models.ICSFile;
import Models.OurDateTime;
import gr.hua.dit.oop2.calendar.TimeEvent;
import gr.hua.dit.oop2.calendar.TimeListener;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class ReminderGUI extends JPanel implements TimeListener {
    private DefaultListModel<JPanel> listModel;
    private final ArrayList<Event> nextEvent;
    private JList<JPanel> printList;
    private OurDateTime liveTime;
    private JScrollPane scrollPane;

    @Override
    public void timeChanged(TimeEvent timeEvent) {
        liveTime = new OurDateTime(timeEvent.getDateTime());
        System.out.println(liveTime);
        setNextEvent(OurMenuGUI.getAllFiles());
        printEvents();
    }

    public ReminderGUI() {
        this.nextEvent = new ArrayList<>();

        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(450, 500));
        setBackground(new Color(255, 190, 235));

        // Initialize and print events
        setNextEvent(OurMenuGUI.getAllFiles());
        printEvents();

        // Register this object as a listener with the TimeService
        registerAsTimeListener();
    }

    private void registerAsTimeListener() {
        TimeTeller teller = TimeService.getTeller();// Adjust this according to your actual implementation
        teller.addTimeListener(this);
    }

    private void setNextEvent(ArrayList<ICSFile> allFiles) {
        liveTime = new OurDateTime(); // Update liveTime before checking conditions
        nextEvent.clear();
        for (ICSFile file : allFiles) {
            for (Event event : file.getCalendar().getEvents()) {
                if (event instanceof Appointment appointment) {
                    if (appointment.getStartDate().getCalculationFormat() >= liveTime.getCalculationFormat() &&
                        appointment.getStartDate().getCalculationFormat() <= liveTime.getCalculationFormat30()) {
                        if (!appointment.isNotified()) {
                            appointment.setNotified(true);
                            playNotificationSound();
                        }
                        nextEvent.add(appointment);
                    }
                } else if (event instanceof Project project) {
                    if (project.getDue().getCalculationFormat() >= liveTime.getCalculationFormat() &&
                        project.getDue().getCalculationFormat() <= liveTime.getCalculationFormat30()) {
                        if (!project.isNotified()){
                            project.setNotified(true);
                            playNotificationSound();
                        }
                        nextEvent.add(project);
                    }
                }
            }
        }
    }


    public void printEvents() {
        removeAll();
        listModel = new DefaultListModel<>();

        for (Event event : nextEvent) {
            JPanel panel = event.getPanel();
            listModel.addElement(panel);
        }
        printList = new JList<>(listModel);
        printList.setPreferredSize(new Dimension(170, (nextEvent.isEmpty() ? 1 : nextEvent.size() * 78)));
        printList.setCellRenderer(new PanelListCellRenderer());
        scrollPane = new JScrollPane(printList);
        scrollPane.setPreferredSize(new Dimension(200, 350));
        add(scrollPane);
        revalidate();
        repaint();
    }

    private void playNotificationSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((Objects.requireNonNull(getClass().getResource("/reminder.wav"))));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ignored) {}
    }
}
