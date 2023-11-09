import net.fortuna.ical4j.model.DateTime;

public class Appointment extends Event {
    private int duration;                     //how much the date will last

    public Appointment(String title, String description, String date, String time, int duration) {
        super(title, description, date, time);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
