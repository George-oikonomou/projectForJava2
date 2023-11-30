import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import net.fortuna.ical4j.model.DateTime;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OurDateTime {
    private int year, month ,day ,hour ,minute;
    private String date, time;
    private DayOfWeek dayOfWeek;
    private DateTime icsFormat;
    private long CalculationFormat;

    public OurDateTime (int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.date = Validate.date(year,month,day);
        this.time = Validate.time(minute,hour);
        setCalculationFormat();
        setIcsFormat();
    }

    /**
     * constructor that creates a OurDateTime object that contains the current
     */
    public OurDateTime() {
        TimeTeller teller = TimeService.getTeller();
        this.dayOfWeek = teller.now().getDayOfWeek();
        this.year = teller.now().getYear();
        this.month = teller.now().getMonthValue();
        this.day = teller.now().getDayOfMonth();
        this.hour = teller.now().getHour();
        this.minute = teller.now().getMinute();
        this.date = Validate.date(year,month,day);
        this.time = Validate.time(minute,hour);
        setCalculationFormat();
        setIcsFormat();
    }

    public DateTime getIcsFormat() {
        return icsFormat;
    }
    public void setIcsFormat() {
        String format = String.format("%04d%02d%02dT%02d%02d00", getYear(), getMonth(), getDay(), getHour(), getMinute());
        try {
            this.icsFormat = new DateTime(format);
        } catch (ParseException e) {
            System.out.println("could not create icsDateTime format");
        }

    }

    public int getYear() { return year; }
    public void setYear(int year) {
        this.year = year;
        setDate();
    }

    public int getMonth() { return month; }
    public void setMonth(int month) {
        this.month = month;
        setDate();
    }

    public int getDay() { return day; }
    public void setDay(int day) {
        this.day = day;
        setDate();
    }

    public int getHour() { return hour; }
    public void setHour(int hour) {
        this.hour = hour;
        setTime();
    }

    public int getMinute() { return minute; }
    public void setMinute(int minute) {
        this.minute = minute;
        setTime();
    }

    public String getDate() { return date; }
    public void setDate() {
        this.date = Validate.date(year,month,day);
        setCalculationFormat();
    }

    public String getTime() { return time; }
    public void setTime() {
        this.time = Validate.time(minute,hour);
        setCalculationFormat();
    }

    public Long getCalculationFormat() { return CalculationFormat; }
    public void setCalculationFormat() {
        String day = String.format("%02d", getDay());
        String month = String.format("%02d", getMonth());
        String hour = String.format("%02d", getHour());
        String minute = String.format("%02d", getMinute());

        String date = getYear() + month + day;
        String time = hour + minute;
        this.CalculationFormat = Long.parseLong(date + time);
    }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    @Override
    public String toString(){ return getDate() + " " + getTime(); }

    public static class Functionality{
        public static OurDateTime dateAndTime() {
            int year, month, day,hour,minute;
            //Year:
            Validate.print("\nDATE\n\tYear:\t");
            year =  Validate.checkAndReturnIntBetween(2023,2024);
            //Month:
            Validate.print("\tMonth:\t");
            month = Validate.checkAndReturnIntBetween(1, 12);
            //Day:
            Validate.print("\tDay:\t");
            day = Validate.day(month ,year, 1);

            //hour
            Validate.print("\nTIME\n\tHour:\t");
            hour = Validate.checkAndReturnIntBetween(0, 23);
            //minute
            Validate.print("\tMinute:\t");
            minute = Validate.checkAndReturnIntBetween(0, 59);
            return new OurDateTime(year, month, day, hour, minute);
        }

        public static OurDateTime ICSFormatToOurDateTime(String string) {//todo spyros
            int year, month, day, hour = 0, minutes = 0;
            if(string.length() == 8) {
                year = Integer.parseInt(string.substring(0, 4));
                month = Integer.parseInt(string.substring(4, 6));
                day = Integer.parseInt(string.substring(6, 8));
            } else {
                year = Integer.parseInt(string.substring(0, 4));
                month = Integer.parseInt(string.substring(4, 6));
                day = Integer.parseInt(string.substring(6, 8));
                hour = Integer.parseInt(string.substring(10,12));
                minutes = Integer.parseInt(string.substring(12,14));
            }
            return new OurDateTime(year,month,day,hour,minutes);
        }
    }
}