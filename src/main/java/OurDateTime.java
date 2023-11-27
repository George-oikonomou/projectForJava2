import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OurDateTime {
    private int year, month ,day ,hour ,minute;
    private String date, time;
    private DayOfWeek dayOfWeek;
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
            DateTimeFormatter formatter;
            if(string.length() == 8) {
                formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
                year = dateTime.getYear();
                month = dateTime.getMonthValue();
                day = dateTime.getDayOfMonth();
            } else {
                formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
                LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
                year = dateTime.getYear();
                month = dateTime.getMonthValue();
                day = dateTime.getDayOfMonth();
                hour = dateTime.getHour();
                minutes = dateTime.getMinute();
            }
            return new OurDateTime(year,month,day,hour,minutes);
        }
    }
}