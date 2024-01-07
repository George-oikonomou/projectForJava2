import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManager implements ActionListener {
    private JMenuItem addCalendar;
    private JMenuItem saveCalendar;
    private final MainPage mainPage;

    public MenuManager(MainPage mainPage) {
        this.mainPage = mainPage;
        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
        mainPage.setJMenuBar(menuBar);

        JMenu myMenu = new JMenu("FILES");
        myMenu.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));

        addCalendar = new JMenuItem("Add Calendar");
        addCalendar.addActionListener(this);
        myMenu.add(addCalendar);

        // Add a separator between sections
        myMenu.addSeparator();

        saveCalendar = new JMenuItem("Save Calendar");
        saveCalendar.addActionListener(this);
        myMenu.add(saveCalendar);

        menuBar.add(myMenu);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCalendar) {
            addCalendar(mainPage.getPrintPanel());
            mainPage.getPrintPanel().revalidate();
            mainPage.getPrintPanel().repaint();
            addCalendar.setEnabled(true);
        }else if (e.getSource() == saveCalendar) {
            for (ICSFile icsFile : App.getAllIcsFiles()) {
                icsFile.storeEvents(icsFile.getCalendar().getEvents());
            }
        }

    }
    public void addCalendar(JPanel printPanel) {
        printPanel.removeAll();
        JButton openFileButton = new JButton("Open File");
        printPanel.add(openFileButton);

        openFileButton.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();

            // Set the initial directory to the user's home directory
            fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (fileChooser.showOpenDialog(printPanel) == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                ICSFile icsFile = new ICSFile(selectedFile.getAbsolutePath());
                App.getAllIcsFiles().add(icsFile);
                icsFile.loadEvents();
            }
        });
    }
}
