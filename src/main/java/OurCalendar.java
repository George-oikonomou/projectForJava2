import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class OurCalendar {

    private ArrayList<Event> events;//has all the events

    //if the file does not exist the new calendar will have these values
    private String version = "2.0";
    private ProdId prodId = new ProdId("-//java project team//java calendar//EN");
    private CalScale calScale = new CalScale(CalScale.VALUE_GREGORIAN);

    public OurCalendar() { this.events = new ArrayList<>(); }

    public ArrayList<Event> getEvents() { return events; }
    public void setEvents(ArrayList<Event> events) { this.events = events; }

    public String getVersion() { return version;}
    public void setVersion(String version) {this.version = version;}

    public ProdId getProdId() { return prodId; }
    public void setProdId(ProdId prodId) { this.prodId = prodId; }

    public CalScale getCalScale() { return calScale; }
    public void setCalScale(CalScale calScale) { this.calScale = calScale; }

    public void addEvents(JPanel printPanel) {
        printPanel.removeAll();
        JButton createAppointment = new JButton("Create Appointment");
        JButton createProject = new JButton("Create Project");
        printPanel.add(createAppointment);
        printPanel.add(createProject);

        createAppointment.addActionListener(e -> {
            printPanel.removeAll();
            AppointmentGUI appointmentGui = new AppointmentGUI(events);
            printPanel.add(appointmentGui);
            printPanel.revalidate();
            printPanel.repaint();
            createAppointment.setEnabled(true);
        });

        createProject.addActionListener(e -> {
            printPanel.removeAll();
            ProjectGui projectGui = new ProjectGui();
            printPanel.add(projectGui);
            printPanel.revalidate();
            printPanel.repaint();
            createProject.setEnabled(true);
        });
    }



    public static void sortList(ArrayList<Event> events) {events.sort((event1, event2) -> (int) (compareEvents(event1, event2)));}

    protected static long compareEvents(Event event1, Event event2) {
        OurDateTime startDate1 = (event1 instanceof Project) ? ((Project) event1).getDue() : event1.getStartDate();
        OurDateTime startDate2 = (event2 instanceof Project) ? ((Project) event2).getDue() : event2.getStartDate();

        return startDate1.getCalculationFormat() - startDate2.getCalculationFormat();
    }

    private ArrayList<Event> eventsBetween(long minTime, long maxTime) {     //code 2 is for upcoming events this week, code 3 is for old events this week, code 1 is for the other prints
        long eventTime;
        ArrayList<Event> eventsToBePrinted = new ArrayList<>();

        for (Event event : events) {
            if (event instanceof Project) {
                eventTime = ((Project) event).getDue().getCalculationFormat();
            } else{
                eventTime = event.getStartDate().getCalculationFormat();
            }
            if (eventTime >= minTime && eventTime <= maxTime) { //if the event is between minimum and maximum time
                eventsToBePrinted.add(event);
            }
        }
        return eventsToBePrinted;
    }

    public void printEvents(JPanel printPanel) {
        printPanel.removeAll();

        JButton day = new JButton("Day");
        JButton week = new JButton("Week");
        JButton month = new JButton("Month");
        JButton pastDay = new JButton("PastDay");
        JButton pastWeek = new JButton("PastWeek");
        JButton pastMonth = new JButton("PastMonth");
        JButton todo = new JButton("Todo");
        JButton due = new JButton("Due");

        printPanel.add(day);
        printPanel.add(week);
        printPanel.add(month);
        printPanel.add(pastDay);
        printPanel.add(pastWeek);
        printPanel.add(pastMonth);
        printPanel.add(todo);
        printPanel.add(due);

        addActionListener(day, App.AppChoices.day, printPanel);
        addActionListener(week, App.AppChoices.week, printPanel);
        addActionListener(month, App.AppChoices.month, printPanel);
        addActionListener(pastDay, App.AppChoices.pastday, printPanel);
        addActionListener(pastWeek, App.AppChoices.pastweek, printPanel);
        addActionListener(pastMonth, App.AppChoices.pastmonth, printPanel);
        addActionListener(todo, App.AppChoices.todo, printPanel);
        addActionListener(due, App.AppChoices.due, printPanel);
    }

    private void addActionListener(JButton button, App.AppChoices choice, JPanel printPanel) {
        button.addActionListener(e -> performAction(choice, printPanel));
    }

    private void performAction(App.AppChoices choice, JPanel printPanel) {
        printPanel.removeAll();

        switch (choice) {
            case day, week, month -> printPanel.add(new PrintGUI(printUpcomingEvents(choice)));
            case pastday, pastweek, pastmonth ->  printPanel.add(new PrintGUI(printOldEvents(choice)));
            case todo, due -> printPanel.add(new PrintGUI(printUnfinishedProject(choice)));
        }

        printPanel.revalidate();
        printPanel.repaint();
    }

    public ArrayList<Event> printUpcomingEvents(App.AppChoices choice) {
        OurDateTime minTime = new OurDateTime(); //if we want to print an upcoming event the min time should be the current time
        OurDateTime maxTime = new OurDateTime(); //this value will always change based on the choice
        LocalDateTime currentTime = LocalDateTime.of(minTime.getYear(),minTime.getMonth()
                ,minTime.getDay(),minTime.getHour(),minTime.getMinute());
        sortList(events);
        switch (choice) {
            case day -> {
                //LocalTime.MAX sets the time of a date time object to 23:59 basically giving us the end of the day
                LocalDateTime endOfDay = currentTime.with(LocalTime.MAX);
                maxTime = new OurDateTime(endOfDay.getYear(),endOfDay.getMonthValue()
                        ,endOfDay.getDayOfMonth(),endOfDay.getHour(),endOfDay.getMinute());
            }
            case week -> {
                //the temporalAdjuster sets advances the current time to next sunday and the sets the time to 23:59
                LocalDateTime endOfSunday = currentTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                        .with(LocalTime.MAX);
                maxTime = new OurDateTime(endOfSunday.getYear(),endOfSunday.getMonthValue(),
                        endOfSunday.getDayOfMonth(),endOfSunday.getHour(),endOfSunday.getMinute());
            }
            case month -> {
                /*getMonth gets the month from current time
                inputDateTime.toLocalDate().isLeapYear() this checks if the year is a leap year
                then we set the length of the month considering if it is a leap year.
                Then at the end we put back the time, so we don't lose it */
                LocalDateTime endOfMonth = currentTime.withDayOfMonth(currentTime.getMonth().
                                length(currentTime.toLocalDate().isLeapYear())).with(LocalDateTime.now().toLocalTime());
                maxTime = new OurDateTime(endOfMonth.getYear(),endOfMonth.getMonthValue(),
                        endOfMonth.getDayOfMonth(),endOfMonth.getHour(),endOfMonth.getMinute());
            }
        }
        return eventsBetween(minTime.getCalculationFormat(), maxTime.getCalculationFormat());
    }
    public ArrayList<Event> printOldEvents(App.AppChoices choice) {
        OurDateTime maxTime = new OurDateTime(); //if we want to print an old event the max time should be the current time
        OurDateTime minTime = new OurDateTime(); //this value will always change based on the choice
        LocalDateTime currentTime = LocalDateTime.of(maxTime.getYear(),maxTime.getMonth()
                ,maxTime.getDay(),maxTime.getHour(),maxTime.getMinute());
        sortList(events);
        switch (choice) {
            case pastday -> {
                //LocalTime.MIN sets the time of a date time object to 00:00 basically giving us the start of the day
                LocalDateTime startOfDay = currentTime.with(LocalTime.MIN);
                minTime = new OurDateTime(startOfDay.getYear(),startOfDay.getMonthValue()
                        ,startOfDay.getDayOfMonth(),startOfDay.getHour(),startOfDay.getMinute());
            }
            case pastweek -> {
                //the temporalAdjuster sets the current time to monday (start of week) and the sets the time to 00:00
                LocalDateTime startOfWeek = currentTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                        .with(LocalTime.MIN);
                minTime = new OurDateTime(startOfWeek.getYear(),startOfWeek.getMonthValue(),
                        startOfWeek.getDayOfMonth(),startOfWeek.getHour(),startOfWeek.getMinute());
            }
            case pastmonth -> {
                // set the current month day to 1 and time to 00:00
                LocalDateTime startOfMonth = currentTime.withDayOfMonth(1).with(LocalTime.MIN);
                minTime = new OurDateTime(startOfMonth.getYear(),startOfMonth.getMonthValue(),
                        startOfMonth.getDayOfMonth(),startOfMonth.getHour(),startOfMonth.getMinute());
            }
        }
        return eventsBetween(minTime.getCalculationFormat(), maxTime.getCalculationFormat());
    }

    public ArrayList<Event> printUnfinishedProject(App.AppChoices choice) {
        sortList(events);
        OurDateTime realDateTime = new OurDateTime(); // Current date & time
        long format = realDateTime.getCalculationFormat();
        ArrayList<Event> eventsToBePrinted = new ArrayList<>();

        for (Event event : events) {
            if (event instanceof Project && !((Project) event).getIsFinished()) {
                long dueFormat = ((Project) event).getDue().getCalculationFormat(); //making a DueFormat to check if it surpasses the realDateTime
                if ((choice == App.AppChoices.todo && format < dueFormat) || (choice == App.AppChoices.due && format >= dueFormat))
                    eventsToBePrinted.add(event);
            }
        }
        return eventsToBePrinted;
    }
}
