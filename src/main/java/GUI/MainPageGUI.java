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
    JButton reminder;
    JPanel menuPanel;
    public Pages currentPage;
    private static JPanel printPanel;

    public enum Pages {
        NEW_APPOINTMENT,
        NEW_PROJECT,
        EDIT_EVENT,
        CHANGE_STATUS,
        PRINT_EVENT,
        REMINDER
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
            case REMINDER -> new Functionality().handleReminder();
        }
    }


    public MainPageGUI() {
        createOptionsPanel();
        createPrintPanel();
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
        this.menu = new OurMenuGUI(this);
        this.setJMenuBar(menu);
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
        menuPanel.add(reminder);
    }

    private void createPrintPanel() {
        printPanel = new JPanel();
        printPanel.setBackground(new Color(100, 226, 223));
        printPanel.setPreferredSize(new Dimension(450, 500));
    }

    private void createButtons(){

        this.reminder = createButton("Reminder","reminder.jpg","See your reminders");
        this.newEventButton = createButton("New Event", "Add.jpg", "add an event to a calendar");
        this.editEventButton = createButton("Edit Event","Edit.jpg","Edit an event from a calendar");
        this.changeProjectStatusButton = createButton("<html>Change Projects<br /><center>Status</center></html>","Status.jpg", " Change the condition of a project from a calendar");
        this.printEventButton = createButton("Print Event","Print.jpg","See events from calendars");
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
            else if (e.getSource() == reminder)
                handleReminder();

            printPanel.revalidate();
            printPanel.repaint();
        }

        private void handleReminder() {
            printPanel.add(new ReminderGUI(menu.getAllFiles()));
            reminder.setEnabled(true);
            currentPage = Pages.REMINDER;
        }

        private void handlePrint() {
            printPanel.add(new PrintGUI(menu.getAllFiles()));
            printEventButton.setEnabled(true);
            currentPage = Pages.PRINT_EVENT;
        }

        private void handleChangeStatus() {
            ChangeStatusGUI changeStatusGui = new ChangeStatusGUI(menu.getAllFiles());
            printPanel.add(changeStatusGui);
            changeProjectStatusButton.setEnabled(true);
            currentPage = Pages.CHANGE_STATUS;
        }
        private void handleEditEvent() {
            EditEventGUI editEventGUI = new EditEventGUI(menu.getAllFiles());
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

            createAppointment.addActionListener(e1 -> {
                executeCreateAppointmentListener();
            });

            createProject.addActionListener(e1 -> {
                executeCreateProjectListener();
            });
        }

        private void executeCreateAppointmentListener() {
            printPanel.removeAll();
            AppointmentGUI appointmentGui = new AppointmentGUI(menu.getAllFiles());
            printPanel.add(appointmentGui);
            printPanel.revalidate();
            printPanel.repaint();
            currentPage = Pages.NEW_APPOINTMENT;
        }

        private void executeCreateProjectListener() {
            printPanel.removeAll();
            ProjectGUI projectGui = new ProjectGUI(menu.getAllFiles());
            printPanel.add(projectGui);
            printPanel.revalidate();
            printPanel.repaint();
            currentPage = Pages.NEW_PROJECT;
        }

    }

}