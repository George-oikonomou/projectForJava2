import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class Appointment extends Event {
    private int durationInMin;//how much the date will last
    private OurDateTime endDate;

    public Appointment(OurDateTime dateTime, OurDateTime endDate, String title, String description) {
        super(dateTime, title, description);
        this.endDate = endDate;
        setDurationInMin(dateTime,endDate);
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(OurDateTime dateTime, OurDateTime endDate) {
        LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute());
        LocalDateTime end = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDay(), endDate.getHour(), endDate.getMinute());
        long durationInMinutes = Duration.between(start, end).toMinutes();
        this.durationInMin = (int) durationInMinutes;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OurDateTime endDate) {
        this.endDate = endDate;
    }

    public int getDuration() { return durationInMin; }
    public void setDuration(int durationInMin) { this.durationInMin = durationInMin; }

    private void setDurationPrompt() {
        Validate.print("\nType the new duration:\t");
        setDuration(Validate.checkAndReturnIntBetween(15, 6 * 60));  //duration is minimum 15 minutes & maximum 6 hours
    }

    public static int ICSFormatToDuration(String string) {
        Duration duration = Duration.parse(string);
        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();
        minutes = minutes + hours * 60;
        return minutes;
    }

    @Override
    public void editEvent() {
        int option;

        do { //Picking one option to change a field
            Validate.println("""
                    Do you want to Change:
                    1) Title
                    2) Description
                    3) Date & Time
                    4) Duration
                    5) or Exit""");
            option = Validate.checkAndReturnIntBetween(1, 5);
            switch (option) {
                case 1 -> setTitlePrompt();
                case 2 -> setDescriptionPrompt();
                case 3 -> setDateTimePrompt();
                case 4 -> setDurationPrompt();
            }
        } while (option != 5);
    }

    @Override
    public String toString() {
        return """
            Appointment:
                dateTime: %s
                title: %s
                description: %s
                durationInMin: %d
            """.formatted(getDateTime(), getTitle(), getDescription(), durationInMin);
    }
}