import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment extends Event {
    private int durationInMin;                     //how much the date will last

    public Appointment(OurDateTime dateTime, String title, String description, int durationInMin) {
        super(dateTime, title, description);
        this.durationInMin = durationInMin;
    }

    public int getDuration() {
        return durationInMin;
    }

    public void setDuration(int durationInMin) {
        this.durationInMin= durationInMin;
    }

    public static int ICSFormatToDuration(String string){
        Duration duration = Duration.parse(string);
        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();
        minutes = minutes + hours * 60;
        return minutes;
    }
}