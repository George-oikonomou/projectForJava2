package  GUI;


import Models.ICSFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class OurMenuGUI extends JMenuBar {
    private final JMenuItem addCalendar;
    private final JMenuItem saveCalendar;
    private final ArrayList<ICSFile> allFiles;

    public OurMenuGUI() {
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

    public ArrayList<ICSFile> getAllFiles() { return allFiles; }

    public void addCalendar() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);


            if (fileChooser.showOpenDialog(OurMenuGUI.this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                if (calendarAlreadyAdded(selectedFile)) {
                    JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "This calendar has already been added.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedFile.exists())
                    loadIcsFile(selectedFile);
                else
                    createNewIcsFile(selectedFile);

                MainPageGUI.getPrintPanel().removeAll();
                MainPageGUI.getPrintPanel().revalidate();
                MainPageGUI.getPrintPanel().repaint();

            }

        });
    }

    private void createNewIcsFile(File selectedFile) {
        File newFile = new File(selectedFile.getAbsolutePath());
        String filePath = newFile.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".ics")) {
             filePath += ".ics";
        }
        ICSFile icsFile = new ICSFile(filePath);

        this.allFiles.add(icsFile);
        icsFile.storeEvents(icsFile.getCalendar().getEvents());
    }

    private void loadIcsFile(File selectedFile) {
        ICSFile icsFile = new ICSFile(selectedFile.getAbsolutePath());
        this.allFiles.add(icsFile);
        icsFile.loadEvents();
    }

    private boolean calendarAlreadyAdded(File selectedFile) {
        for (ICSFile existingFile : allFiles) {
            if (existingFile.getFilePath().equals(selectedFile.getAbsolutePath())) {
                return true;
            }
        }
        return false;
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