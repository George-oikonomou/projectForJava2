import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPage extends JFrame implements ActionListener {

    JButton newEventButton;
    JButton editEventButton;
    JButton changeProjectStatusButton;
    JButton printEventButton;
    JPanel menuPanel;
    JPanel printPanel;

    public MainPage() {
        initializeFrame();
        createPanels();
        new MenuManager(this);
        setPanels();
        createButtons();
        addButtonsToPanel();
    }

    private void initializeFrame() {
        this.setTitle("Calendar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(new BorderLayout(10, 10));
        this.setSize(1000, 500);
        this.setVisible(true);
    }

    private void createPanels() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        printPanel = new JPanel();
        menuPanel.setBackground(Color.cyan);
        printPanel.setBackground(Color.pink);
        menuPanel.setPreferredSize(new Dimension(250, 500));
        printPanel.setPreferredSize(new Dimension(450, 500));
    }

    private void setPanels() {
        this.add(menuPanel, BorderLayout.WEST);
        this.add(printPanel, BorderLayout.CENTER);
    }

    private void createButtons() {
        createNewEventButton();
        createEditEventButton();
        createChangeProjectStatusButton();
        createPrintEventButton();
    }

    private void addButtonsToPanel() {
        menuPanel.add(newEventButton);
        menuPanel.add(editEventButton);
        menuPanel.add(changeProjectStatusButton);
        menuPanel.add(printEventButton);
    }

    private void createNewEventButton() {
        newEventButton = createButton("New Event");
    }

    private void createEditEventButton() {
        editEventButton = createButton("Edit Event");
    }

    private void createChangeProjectStatusButton() {
        changeProjectStatusButton = createButton("<html>Change Projects<br /> Status</html>");
    }

    private void createPrintEventButton() {
        printEventButton = createButton("Print Event");
    }

    private JButton createButton(String text) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(170,60));
        button.addActionListener(this);
        button.setText(text);
        button.setFont(new Font("Comic Sans", Font.BOLD, 17));
        return button;
    }

    public JPanel getPrintPanel() {
        return printPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newEventButton) {
            OurCalendar.addEvents(getPrintPanel());
            printPanel.revalidate();
            printPanel.repaint();
            newEventButton.setEnabled(true);

        } else if (e.getSource() == editEventButton)  {
            Validate.println("edit Event Button");
            editEventButton.setEnabled(true);
        } else if (e.getSource() == changeProjectStatusButton) {
            OurCalendar.changeStatus(getPrintPanel());
            printPanel.revalidate();
            printPanel.repaint();
            changeProjectStatusButton.setEnabled(true);
        } else if (e.getSource() == printEventButton) {
            PrintGUI.printEvents(getPrintPanel());
            printPanel.revalidate();
            printPanel.repaint();
            printEventButton.setEnabled(true);

        }
    }
}
