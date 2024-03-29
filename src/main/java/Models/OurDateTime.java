package  Models;

import GUI.MainPageGUI;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import net.fortuna.ical4j.model.DateTime;

import javax.swing.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class OurDateTime {
    private final int year,month,day,hour,minute,second;
    private final String date,time;
    private DateTime icsFormat;
    private long calculationFormat ,calculationFormat30;
    private Date dateFormat;
    //constructor that creates an OurDateTime object with the given values from the user, for the new events
    public OurDateTime (int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = 0;
        this.date = date(year,month,day,true);
        this.time = time(second,minute,hour,true);
        setCalculationFormat();
        setCalculationFormat30();
        setIcsFormat();
        setDateFormat();
    }
    //constructor that's used in ReminderGUI
    public OurDateTime(LocalDateTime realTime) {
        this.year = realTime.getYear();
        this.month = realTime.getMonthValue();
        this.day = realTime.getDayOfMonth();
        this.hour = realTime.getHour();
        this.minute = realTime.getMinute();
        this.second = realTime.getSecond();
        this.date = date(year,month,day,true);
        this.time = time(second,minute,hour,true);
        setCalculationFormat();
        setCalculationFormat30();
        setIcsFormat();
        setDateFormat();
    }
//constructor that's used in the OurCalendar class to print the events, based on the liveDateTime
    public OurDateTime() {
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime realTime = teller.now();
        this.year = realTime.getYear();
        this.month = realTime.getMonthValue();
        this.day = realTime.getDayOfMonth();
        this.hour = realTime.getHour();
        this.minute = realTime.getMinute();
        this.second =realTime.getSecond();
        this.date = date(year,month,day,true);
        this.time = time(second,minute,hour,true);
        setCalculationFormat();
        setCalculationFormat30();
        setIcsFormat();
        setDateFormat();
    }
    /**
     * creates a format version of time, so it is nicely printed
     */
    public static String time(int second,int minute, int hour, boolean includeSeparators) {
        return includeSeparators ? String.format("%02d:%02d:%02d", hour, minute,second)
                                 : String.format("%02d%02d%02d" , hour, minute,second);
    }
    //same but for date
    public static String date(int year, int month, int day, boolean includeSeparators) {
        return includeSeparators ? String.format("%02d/%02d/%d", day, month, year)
                                 : String.format("%d%02d%02d"  , year, month, day);
    }

    public DateTime getIcsFormat() { return icsFormat; }

    //this created the icsFormat of a date that is used in dtStart dtEnd due ...
    public void setIcsFormat() {
        String format = String.format("%04d%02d%02dT%02d%02d00", getYear(), getMonth(), getDay(), getHour(), getMinute());
        try {
            this.icsFormat = new DateTime(format);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(MainPageGUI.getPrintPanel(), "Error while parsing date", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getDate() { return date; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }

    public String getTime() { return time; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }

    public Long getCalculationFormat() { return calculationFormat; }
    //sets the calculation format which is used for figuring out which DateTime is first
    public void setCalculationFormat() {
        String date = date(year,month,day,false);
        String time = time(0,minute,hour,false);
        this.calculationFormat = Long.parseLong(date + time);
    }

    /**
     * description: sets the calculation format of the date time that is 30 minutes later than the current date time (for reminders)
     */
    public void setCalculationFormat30() {
        LocalDateTime time = LocalDateTime.of(getYear(), getMonth(), getDay(), getHour(), getMinute());
        LocalDateTime time30 = time.plusMinutes(30);

        // Calculate the calculationFormat30 directly
        this.calculationFormat30 = calculateCalculationFormat(time30.getYear(), time30.getMonthValue(), time30.getDayOfMonth(), time30.getHour(), time30.getMinute());
    }

    private long calculateCalculationFormat(int year, int month, int day, int hour, int minute) {
        String date = date(year, month, day, false);
        String time = time(0, minute, hour, false);
        return Long.parseLong(date + time);
    }

    public long getCalculationFormat30(){
        return calculationFormat30;
    }

    /**
     * description: sets the date format of the date time used for the jSpinner and jDatechooser
     */
    public void setDateFormat(){
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);
        this.dateFormat = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date getDateFormat(){
        return this.dateFormat;
    }

    @Override
    public String toString(){ return getDate() + " " + getTime(); }

}