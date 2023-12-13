import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import net.fortuna.ical4j.model.DateTime;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class OurDateTime {
    private final int year,month,day,hour,minute;
    private final String date,time;
    private DayOfWeek dayOfWeek;
    private DateTime icsFormat;
    private long CalculationFormat;
    //constructor to create an ourDateTime object with the values we give
    // it gives us nice format versions of  datetime which we use
    public OurDateTime (int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.date = date(year,month,day,true);
        this.time = time(minute,hour,true);
        setCalculationFormat();
        setIcsFormat();
    }
    /**
     * constructor that creates a OurDateTime object that contains the current datetime change this
     * to change the current time
     */
    public OurDateTime() {
        TimeTeller teller = TimeService.getTeller();
        //CHANGE HERE example
        //LocalDateTime realTime = LocalDateTime.of(2023,12,9,18,11);
        LocalDateTime realTime = teller.now();
        this.dayOfWeek = realTime.getDayOfWeek();
        this.year = realTime.getYear();
        this.month = realTime.getMonthValue();
        this.day = realTime.getDayOfMonth();
        this.hour = realTime.getHour();
        this.minute = realTime.getMinute();
        this.date = date(year,month,day,true);
        this.time = time(minute,hour,true);
        setCalculationFormat();
        setIcsFormat();
    }
    /**
     * creates a format version of time, so it is nicely printed
     */
    public static String time(int minute, int hour, boolean includeSeparators) {
        return includeSeparators ? String.format("%02d:%02d", hour, minute)
                                 : String.format("%02d%02d" , hour, minute);
    }
    //same but for date
    public static String date(int year, int month, int day, boolean includeSeparators) {
        return includeSeparators ? String.format("%02d/%02d/%d", day, month, year)
                                 : String.format("%d%02d%02d"  , year, month, day);
    }
    public DateTime getIcsFormat() { return icsFormat; }

    //this created the icsFormat of a date thats used in dtStart dtEnd due ..
    public void setIcsFormat() {
        String format = String.format("%04d%02d%02dT%02d%02d00", getYear(), getMonth(), getDay(), getHour(), getMinute());
        try {
            this.icsFormat = new DateTime(format);
        } catch (ParseException e) {
            Validate.println("could not create icsDateTime format");
        }
    }

    public String getDate() { return date; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public int getDay() { return day; }

    public String getTime() { return time; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }

    public Long getCalculationFormat() { return CalculationFormat; }
    //sets the calculation format which is used for figuring out which DateTime is first
    public void setCalculationFormat() {
        String date = date(year,month,day,false);
        String time = time(minute,hour,false);
        this.CalculationFormat = Long.parseLong(date + time);
    }
    @Override
    public String toString(){ return getDate() + " " + getTime(); }
    public static class Functionality{
        /**
         * method that gets creates a valid datetime object based on the users input it has the proper restrictions
         * @return returns a ourDateTime object
         */
        public static OurDateTime dateAndTime() {
            int year   = Validate.getInput("\nDATE\n\tYear:\t", 2023 ,2100);
            int month  = Validate.getInput("\tMonth:\t", 1, 12);
            int day    = Validate.getInput("\tDay:\t", 1, Validate.getDaysInMonth(month, year));
            int hour   = Validate.getInput("\nTIME\n\tHour:\t", 0, 23);
            int minute = Validate.getInput("\tMinute:\t", 0, 59);
            return new OurDateTime(year, month, day, hour, minute);
        }
    }
}
