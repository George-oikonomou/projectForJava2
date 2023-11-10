public class Appointment extends Event {
    private int duration;                     //how much the date will last

    public Appointment(String title, String description, DateTime dateTime, int duration) {
        super(dateTime, description, title);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}