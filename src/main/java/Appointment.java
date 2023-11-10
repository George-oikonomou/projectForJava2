public class Appointment extends Event {
    private int duration;                     //how much the date will last

    public Appointment(Datetime dateTime, String title, String description, int duration) {
        super(dateTime, title, description);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}