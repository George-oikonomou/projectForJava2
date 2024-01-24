package Main;

import GUI.MainPageGUI;
import gr.hua.dit.oop2.calendar.TimeService;
import javax.swing.*;

public class App {

    public enum AppChoices{day, week, month, pastday, pastweek, pastmonth, todo, due}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPageGUI::new);
        TimeService.stop();
    }
}