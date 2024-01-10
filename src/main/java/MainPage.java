import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPage extends JFrame {
    OurMenu menu;
    JButton newEventButton;
    JButton editEventButton;
    JButton changeProjectStatusButton;
    JButton printEventButton;
    JPanel menuPanel;
    JPanel printPanel;

    public MainPage() {
        createOptionsPanel();
        createPrintPanel();
        initializeFrame();
    }

    private void initializeFrame() {
        this.setTitle("Calendar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(new BorderLayout(10, 10));
        this.setSize(1000, 520);
        this.setVisible(true);
        this.add(menuPanel, BorderLayout.WEST);
        this.add(printPanel, BorderLayout.CENTER);
        this.menu = new OurMenu();
        this.setJMenuBar(menu);
    }

    private void createOptionsPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(Color.cyan);
        menuPanel.setPreferredSize(new Dimension(250, 500));
        createButtons();
        menuPanel.add(newEventButton);
        menuPanel.add(editEventButton);
        menuPanel.add(changeProjectStatusButton);
        menuPanel.add(printEventButton);
    }

    private void createPrintPanel() {
        printPanel = new JPanel();
        printPanel.setBackground(Color.pink);
        printPanel.setPreferredSize(new Dimension(450, 500));
    }

    private void createButtons() {
        this.newEventButton = createButton("New Event");
        this.editEventButton = createButton("Edit Event");
        this.changeProjectStatusButton = createButton("<html>Change Projects<br /><center>Status</center></html>");
        this.printEventButton = createButton("Print Event");
    }

    private JButton createButton(String text) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(170, 60));
        button.addActionListener(new Functionality());
        button.setText(text);
        button.setFont(new Font("Comic Sans", Font.BOLD, 17));
        return button;
    }

    private class Functionality implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            printPanel.removeAll();
            if (e.getSource() == newEventButton) {
                JButton createAppointment = new JButton("Create Appointment");
                JButton createProject = new JButton("Create Project");
                printPanel.add(createAppointment);
                printPanel.add(createProject);

                createAppointment.addActionListener(e1 -> {
                    printPanel.removeAll();
                    AppointmentGUI appointmentGui = new AppointmentGUI(menu.getAllFiles());
                    printPanel.add(appointmentGui);
                    printPanel.revalidate();
                    printPanel.repaint();
                    createAppointment.setEnabled(true);
                });

                createProject.addActionListener(e1 -> {
                    printPanel.removeAll();
                    ProjectGui projectGui = new ProjectGui(menu.getAllFiles());
                    printPanel.add(projectGui);
                    printPanel.revalidate();
                    printPanel.repaint();
                    createProject.setEnabled(true);
                });

            } else if (e.getSource() == editEventButton) {
                EditEventGUI editEventGUI = new EditEventGUI(menu.getAllFiles());
                printPanel.add(editEventGUI);
                editEventButton.setEnabled(true);

            } else if (e.getSource() == changeProjectStatusButton) {
                ChangeStatusGui changeStatusGui = new ChangeStatusGui(menu.getAllFiles());
                printPanel.add(changeStatusGui);
                changeProjectStatusButton.setEnabled(true);

            } else if (e.getSource() == printEventButton) {
                printPanel.add(new PrintGUI(menu.getAllFiles()));
                printEventButton.setEnabled(true);
            }
            printPanel.revalidate();
            printPanel.repaint();
        }
    }
}
