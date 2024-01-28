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
    private static ArrayList<ICSFile> allFiles;
    private final MainPageGUI mainPageGUI;


    /*
    *constructor
    * @param mainPageGUI
    * this constructor is used to create the menu bar and add the menu items
    */
    public OurMenuGUI(MainPageGUI mainPageGUI) {
        this.mainPageGUI = mainPageGUI;
        allFiles = new ArrayList<>();
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


    public static ArrayList<ICSFile> getAllFiles() { return allFiles; }

    //this method is used to add a new calendar
    public void addCalendar() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // if approved, load the file
            if (fileChooser.showOpenDialog(OurMenuGUI.this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // check if the calendar has already been added
                if (calendarAlreadyAdded(selectedFile)) {
                    JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "This calendar has already been added.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // check if the file exists and load it or create a new one
                if (selectedFile.exists())
                    loadIcsFile(selectedFile);
                else
                    createNewIcsFile(selectedFile);

                mainPageGUI.getBackToPage();//refresh the page to show the added calendar in the calendar options live
                mainPageGUI.isReminder = true; //set the reminder to true to show the reminder panel
                mainPageGUI.refreshReminderPanel();//refresh the reminder panel

            }

        });
    }

    private void createNewIcsFile(File selectedFile) {//this method is used to create a new ics file
        File newFile = new File(selectedFile.getAbsolutePath());
        String filePath = newFile.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".ics")) {
             filePath += ".ics";
        }
        ICSFile icsFile = new ICSFile(filePath);

        allFiles.add(icsFile);
        icsFile.storeEvents(icsFile.getCalendar().getEvents());
    }

    private void loadIcsFile(File selectedFile) {//this method is used to load an existing ics file
        ICSFile icsFile = new ICSFile(selectedFile.getAbsolutePath());
        allFiles.add(icsFile);
        icsFile.loadEvents();
    }

    private boolean calendarAlreadyAdded(File selectedFile) {//this method is used to check if the calendar has already been added
        for (ICSFile existingFile : allFiles) {
            if (existingFile.getFilePath().equals(selectedFile.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    private class Functionality implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {//this method is used to perform the actions of the menu items
            if (e.getSource() == addCalendar) {
                addCalendar();
            }else if (e.getSource() == saveCalendar) {
                for (ICSFile icsFile : getAllFiles()) {//displays a message for all calendars one by one that they have been saved successfully
                    icsFile.storeEvents(icsFile.getCalendar().getEvents());
                    JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "calendar "+icsFile.getFileName() + " has been saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}