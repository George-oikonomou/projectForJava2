import java.time.Duration;

public class Appointment extends Event {
    private int durationInMin;//how much the date will last

    public Appointment(OurDateTime dateTime, String title, String description, int durationInMin) {
        super(dateTime, title, description);
        this.durationInMin = durationInMin;
    }

    public int getDuration() {return durationInMin;}
    public void setDuration(int durationInMin) {this.durationInMin = durationInMin;}

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

        do {
            //Picking one option to change a field
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
                case 3 -> setDateTimePrompt(true);
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