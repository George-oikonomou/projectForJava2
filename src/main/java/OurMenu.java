import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OurMenu extends JMenuBar {
    private final JMenuItem addCalendar;
    private final JMenuItem saveCalendar;
    private final ArrayList<ICSFile> allFiles;

    public OurMenu() {
        this.allFiles = new ArrayList<>();
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
        JMenu myMenu = new JMenu("FILES");
        myMenu.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
        addCalendar = new JMenuItem("Add Calendar");
        addCalendar.addActionListener(new Functionality());
        saveCalendar = new JMenuItem("Save Calendar");
        saveCalendar.addActionListener(new Functionality());
        myMenu.add(addCalendar);
        myMenu.addSeparator();
        myMenu.add(saveCalendar);
        this.add(myMenu);
    }

    public void addCalendar() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();

            // Set the initial directory to the user's home directory
            fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (fileChooser.showOpenDialog(OurMenu.this) == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                ICSFile icsFile = new ICSFile(selectedFile.getAbsolutePath());
                this.allFiles.add(icsFile);
                icsFile.loadEvents();
            }
        });
    }

    public ArrayList<ICSFile> getAllFiles() {
        return allFiles;
    }

    private class Functionality implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addCalendar) {
                addCalendar();
            }else if (e.getSource() == saveCalendar) {
                for (ICSFile icsFile : getAllFiles()) {
                    icsFile.storeEvents(icsFile.getCalendar().getEvents());
                }
            }
        }
    }
}