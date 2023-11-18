import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OurDateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String date;
    private String time;

    public OurDateTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.date = Validate.date(year, month, day);
        this.time = Validate.time(minute, hour);
    }

    /**
     * constructor that creates a OurDateTime but for events that do not have time
     *
     * @param year  year of event
     * @param month month of event
     * @param day   day of event
     */
    public OurDateTime(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.date = Validate.date(year, month, day);
    }

    /**
     * constructor that creates a OurDateTime object that contains the current
     */
    public OurDateTime() {
        TimeTeller teller = TimeService.getTeller();
        this.year = teller.now().getYear();
        this.month = teller.now().getMonthValue();
        this.day = teller.now().getDayOfMonth();
        this.hour = teller.now().getHour();
        this.minute = teller.now().getMinute();
        this.date = Validate.date(year, month, day);
        this.time = Validate.time(minute, hour);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        if (getTime() == null){
            return getDate();
        }else {
            return getDate() + " " +getTime();
        }
    }


    public static class Functionality{
        public static OurDateTime dateAndTime(boolean choice) {
            int year, month, day;
            //Year:
            System.out.print("\nDATE\nYear:\t");
            year =  Validate.checkAndReturnIntBetween(2023,2024);
            //Month:
            System.out.print("\tMonth:\t");
            month = Validate.checkAndReturnIntBetween(1, 12);
            //Day:
            System.out.print("\tDay:\t");
            day = Validate.day(month ,year, 1);

            if(choice) {
                int hour,minute;
                //hour
                System.out.print("\nTIME\nHour:\t");
                hour = Validate.checkAndReturnIntBetween(0, 23);
                //minute
                System.out.print("\tMinute:\t");
                minute = Validate.checkAndReturnIntBetween(0, 59);
                return new OurDateTime(year, month, day, hour, minute);
            }
            //return object
            return new OurDateTime(year, month, day);
        }

        /*
         * this is a method that will take the string of OurDateTime
         * and will split it up and create new OurDateTime objects.
         * I made it so we can extract the DateTime from the files
         * and create a OurDateTime object with it.
         */
        public static OurDateTime ICSFormatToOurDateTime(String string) {
            int year, month, day, hour, minutes;
            if(string.length() == 8){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
                year = dateTime.getYear();
                month = dateTime.getMonthValue();
                day = dateTime.getDayOfMonth();
                return new OurDateTime(year,month,day);
            }else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
                LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
                year = dateTime.getYear();
                month = dateTime.getMonthValue();
                day = dateTime.getDayOfMonth();
                hour = dateTime.getHour();
                minutes = dateTime.getMinute();
                return new OurDateTime(year,month,day,hour,minutes);
            }
        }
    }
}
