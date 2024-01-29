package  GUI;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class MainPageGUI extends JFrame {
    OurMenuGUI menu;
    JButton newEventButton;
    JButton editEventButton;
    JButton changeProjectStatusButton;
    JButton printEventButton;
    JPanel menuPanel;
    JPanel reminderPanel;
    boolean isReminder = false;
    public Pages currentPage;
    private static JPanel printPanel;

    public enum Pages {
        NEW_APPOINTMENT,
        NEW_PROJECT,
        EDIT_EVENT,
        CHANGE_STATUS,
        PRINT_EVENT,
    }

    public void getBackToPage() {
        if (currentPage == null) return;

        printPanel.removeAll();
        printPanel.revalidate();
        printPanel.repaint();

        switch (currentPage) {
            case NEW_APPOINTMENT, NEW_PROJECT -> new Functionality().handleNewEvent();
            case EDIT_EVENT -> new Functionality().handleEditEvent();
            case CHANGE_STATUS -> new Functionality().handleChangeStatus();
            case PRINT_EVENT -> new Functionality().handlePrint();
        }
    }


    public MainPageGUI() {
        createOptionsPanel();
        createPrintPanel();
        createReminderPanel();
        initializeFrame();
    }

    public static JPanel getPrintPanel(){
        return printPanel;
    }

    private void initializeFrame() {
        this.currentPage = null;
        this.setTitle("Calendar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(new BorderLayout(10, 10));
        this.setSize(1000, 520);
        this.setVisible(true);
        this.add(menuPanel, BorderLayout.WEST);
        this.add(printPanel, BorderLayout.CENTER);
        this.add(reminderPanel, BorderLayout.EAST);
        this.menu = new OurMenuGUI(this);
        reminderPanel.add(new ReminderGUI());
        this.setJMenuBar(menu);
    }

    public void refreshReminderPanel() {
        reminderPanel.add(new ReminderGUI());
        reminderPanel.revalidate();
        reminderPanel.repaint();
    }

    private void createOptionsPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(new Color(255, 190, 235));
        menuPanel.setPreferredSize(new Dimension(250, 500));
        createButtons();
        menuPanel.add(newEventButton);
        menuPanel.add(editEventButton);
        menuPanel.add(changeProjectStatusButton);
        menuPanel.add(printEventButton);
    }

    private void createPrintPanel() {
        printPanel = new JPanel();
        printPanel.setBackground(new Color(100, 226, 223));
        printPanel.setPreferredSize(new Dimension(450, 500));
    }

    private void createReminderPanel(){
        reminderPanel = new JPanel();
        reminderPanel.setLayout(new FlowLayout());
        reminderPanel.setBackground(new Color(255, 190, 235));
        reminderPanel.setPreferredSize(new Dimension(250,500));
        reminderPanel.add(new CurrentTimeGUI());
        JLabel reminders = new JLabel("REMINDERS");
        Font font = reminders.getFont().deriveFont(Font.PLAIN, 30);
        reminders.setFont(font);
        reminderPanel.add(reminders);
    }

    private void createButtons(){

        this.newEventButton = createButton("New Event", "buttonsForMenuPanel/Add.jpg", "add an event to a calendar");
        this.editEventButton = createButton("Edit Event", "buttonsForMenuPanel/Edit.jpg","Edit an event from a calendar");
        this.changeProjectStatusButton = createButton("<html>Change Projects<br /><center>Status</center></html>", "buttonsForMenuPanel/Status.jpg", " Change the condition of a project from a calendar");
        this.printEventButton = createButton("Print Event", "buttonsForMenuPanel/Print.jpg","See events from calendars");
    }

    private JButton createButton(String text, String image, String toolTip) {
        JButton button = new JButton();
        try {
            Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/" + image)));
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            button.setText(text);
        }
        button.setPreferredSize(new Dimension(170, 60));

        button.addActionListener(new Functionality());
        button.setFont(new Font("Comic Sans", Font.BOLD, 17));
        button.setToolTipText(toolTip);
        return button;
    }

    private class Functionality implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            printPanel.removeAll();
            currentPage = null;

            if (e.getSource() == newEventButton)
                handleNewEvent();
            else if (e.getSource() == editEventButton)
                handleEditEvent();
            else if (e.getSource() == changeProjectStatusButton)
                handleChangeStatus();
            else if (e.getSource() == printEventButton)
                handlePrint();

            printPanel.revalidate();
            printPanel.repaint();
        }

        private void handlePrint() {
            printPanel.add(new PrintGUI(OurMenuGUI.getAllFiles()));
            printEventButton.setEnabled(true);
            currentPage = Pages.PRINT_EVENT;
        }

        private void handleChangeStatus() {
            ChangeStatusGUI changeStatusGui = new ChangeStatusGUI(OurMenuGUI.getAllFiles());
            printPanel.add(changeStatusGui);
            changeProjectStatusButton.setEnabled(true);
            currentPage = Pages.CHANGE_STATUS;
        }
        private void handleEditEvent() {
            EditEventGUI editEventGUI = new EditEventGUI(OurMenuGUI.getAllFiles());
            printPanel.add(editEventGUI);
            editEventButton.setEnabled(true);
            currentPage = Pages.EDIT_EVENT;
        }
        public void handleNewEvent(){
            JButton createAppointment = new JButton("Create Appointment");
            JButton createProject = new JButton("Create Project");
            printPanel.add(createAppointment);
            printPanel.add(createProject);

            if (currentPage == Pages.NEW_APPOINTMENT){
                executeCreateAppointmentListener();
            } else if (currentPage == Pages.NEW_PROJECT){
                executeCreateProjectListener();
            }

            createAppointment.addActionListener(e1 -> executeCreateAppointmentListener());

            createProject.addActionListener(e1 -> executeCreateProjectListener());
        }

        private void executeCreateAppointmentListener() {
            printPanel.removeAll();
            AppointmentGUI appointmentGui = new AppointmentGUI(OurMenuGUI.getAllFiles());
            printPanel.add(appointmentGui);
            printPanel.revalidate();
            printPanel.repaint();
            currentPage = Pages.NEW_APPOINTMENT;
        }

        private void executeCreateProjectListener() {
            printPanel.removeAll();
            ProjectGUI projectGui = new ProjectGUI(OurMenuGUI.getAllFiles());
            printPanel.add(projectGui);
            printPanel.revalidate();
            printPanel.repaint();
            currentPage = Pages.NEW_PROJECT;
        }

    }

}