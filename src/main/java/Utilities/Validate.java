package Utilities;

import Models.OurDateTime;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public  class Validate {//General class for validating input

    public static boolean Dates(OurDateTime startDateTime, OurDateTime endDateTime) {
        if (startDateTime.getCalculationFormat() > endDateTime.getCalculationFormat()){
            JOptionPane.showMessageDialog(null, "Start date cant be after end date","Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
}