public class DateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String date;
    private String time;

    public DateTime (int year, int month, int day, int hour, int minute, String date, String time) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
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
}
