package Utilities;

import Models.ICSFile;

import javax.swing.*;
import java.util.ArrayList;

public class SingleCalendarSelect extends JComboBox<String> {
    private final ArrayList<ICSFile> allFiles;

    /**
     * Returns the ICSFile that corresponds to the selected item in the JComboBox.
     */
    public SingleCalendarSelect(ArrayList<ICSFile> allFiles) {
        this.allFiles = allFiles;
        for (ICSFile icsFile : allFiles) addItem(icsFile.getFileName());
        if (allFiles.isEmpty()) addItem("No ICS Files");
    }
    public boolean isEmpty(){return allFiles.isEmpty();}
}