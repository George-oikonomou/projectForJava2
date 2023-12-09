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

    public OurDateTime (int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.date = Validate.date(year,month,day,true);
        this.time = Validate.time(minute,hour,true);
        setCalculationFormat();
        setIcsFormat();
    }

    /**
     * constructor that creates a OurDateTime object that contains the current
     */
    public OurDateTime() {
        CustomCurrentTime timeTeller = new CustomCurrentTime(LocalDateTime.of(2024, 1, 1, 21, 40));
        this.dayOfWeek = timeTeller.now().getDayOfWeek();
        this.year = timeTeller.now().getYear();
        this.month = timeTeller.now().getMonthValue();
        this.day = timeTeller.now().getDayOfMonth();
        this.hour = timeTeller.now().getHour();
        this.minute = timeTeller.now().getMinute();
        this.date = Validate.date(year,month,day,true);
        this.time = Validate.time(minute,hour,true);
        setCalculationFormat();
        setIcsFormat();
    }

    public DateTime getIcsFormat() { return icsFormat; }
    public void setIcsFormat() {
        String format = String.format("%04d%02d%02dT%02d%02d00", getYear(), getMonth(), getDay(), getHour(), getMinute());
        try {
            this.icsFormat = new DateTime(format);
        } catch (ParseException e) {
            System.out.println("could not create icsDateTime format");
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
    public void setCalculationFormat() {
        String date = Validate.date(year,month,day,false);
        String time = Validate.time(minute,hour,false);
        this.CalculationFormat = Long.parseLong(date + time);
    }

    @Override
    public String toString(){ return getDate() + " " + getTime(); }

    public static class Functionality{
        public static OurDateTime dateAndTime() {
            int year   = Validate.getInput("\nDATE\n\tYear:\t", 2023 ,2100);
            int month  = Validate.getInput("\tMonth:\t", 1, 12);
            int day    = Validate.getInput("\tDay:\t", 1, Validate.getDaysInMonth(month, year));
            int hour   = Validate.getInput("\nTIME\n\tHour:\t", 0, 23);
            int minute = Validate.getInput("\tMinute:\t", 0, 59);
            return new OurDateTime(year, month, day, hour, minute);
        }

        public static OurDateTime ICSFormatToOurDateTime(String string) {
            int year    = Integer.parseInt(string.substring(0, 4));
            int month   = Integer.parseInt(string.substring(4, 6));
            int day     = Integer.parseInt(string.substring(6, 8));
            int hour    = (string.length() == 13) ? Integer.parseInt(string.substring(9, 11)) : 0;
            int minutes = (string.length() == 13) ? Integer.parseInt(string.substring(11, 13)) : 0;

            return new OurDateTime(year, month, day, hour, minutes);
        }
    }
}