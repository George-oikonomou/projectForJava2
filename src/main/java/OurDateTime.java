import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;

public class OurDateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String date;
    private String time;

    public OurDateTime (int year, int month, int day, int hour, int minute, String date, String time) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.date = Integer.toString(day).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
        this.time = Integer.toString(hour).concat(":").concat(Integer.toString(minute));
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
        this.date = Integer.toString(day).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
        this.time = Integer.toString(hour).concat(":").concat(Integer.toString(minute));
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

    public static class OurDateTimeFunctionality{
        public static OurDateTime dateAndTime() {
            int year, month, day, hour, minute;
            String date, time;
            //Year:
            System.out.print("\nDATE\nYear:\t");
            year =  Validate.checkAndReturnIntBetween(2023,2024);
            //Month:
            System.out.print("\tMonth:\t");
            month = Validate.checkAndReturnIntBetween(1, 12);
            //Day:
            System.out.print("\tDay:\t");
            day = Validate.day(month ,year);

            //Create Date
            date = Validate.date(year, month, day);

            //hour
            System.out.print("\nTIME\nHour:\t");
            hour = Validate.checkAndReturnIntBetween(0, 23);

            //minute
            System.out.print("\tMinute:\t");
            minute = Validate.checkAndReturnIntBetween(0, 59);

            //create time
            time = Validate.time(minute,hour);
            System.out.println();

            //return object
            return new OurDateTime(year, month, day, hour, minute, date, time);
        }
    }
}
