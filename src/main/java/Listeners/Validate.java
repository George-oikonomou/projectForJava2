package Listeners;

import Models.OurDateTime;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public  class Validate {//General class for validating input

    public static void Input(JDateChooser startDateChooser, JTextComponent title, JTextComponent description) {
        if (startDateChooser.getDate() == null  || title.getText().equals("Appointment Name") || description.getText().equals("Appointment Description")){
            JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean Dates(OurDateTime startDateTime, OurDateTime endDateTime) {
        if (startDateTime.getCalculationFormat() > endDateTime.getCalculationFormat()){
            JOptionPane.showMessageDialog(null, "Start date cant be after end date","Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
    //for stage 2
    public static void println(Object obj) { System.out.println(obj); }
    public static void print(Object obj) { System.out.print(obj); }
}