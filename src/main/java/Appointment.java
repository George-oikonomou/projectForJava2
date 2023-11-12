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
}