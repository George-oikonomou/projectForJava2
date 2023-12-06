import java.time.Duration;
import java.time.LocalDateTime;

public class Appointment extends Event {
    private String duration;
    private final OurDateTime endDate;

    public Appointment(OurDateTime startDate, OurDateTime endDate, String title, String description) {
        super(startDate, title, description);
        this.endDate = endDate;
        setDuration(startDate,endDate);
    }

    public String getDuration() {return duration;}
    public void setDuration(OurDateTime startDate, OurDateTime endDate) {
        LocalDateTime start = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDay(), startDate.getHour(), startDate.getMinute());
        LocalDateTime end   = LocalDateTime.of(endDate.getYear()  , endDate.getMonth(), endDate.getDay(), endDate.getHour(), endDate.getMinute());
        long durationInMinutes = Duration.between(start, end).toMinutes();
        this.duration = calculateDurationInDays((int) durationInMinutes);
    }

    public OurDateTime getEndDate() {return endDate;}

    public String calculateDurationInDays(int durationInMin) {
        int days    = durationInMin / 1440;
        int hours   = (durationInMin % 1440) / 60;
        int minutes = (durationInMin % 1440) % 60;

        return String.format("%s%s%s",days > 0 ? days + (days == 1 ? " day " : " days ") : "",
                                      hours > 0 ? hours + (hours == 1 ? " hour " : " hours ") : "",
                                      (days == 0 && hours == 0) || minutes > 0 ? minutes + (minutes == 1 ? " minute" : " minutes") : "").trim();
    }

    @Override
    public String toString() {
        return """
            Appointment:
                title: %s
                description: %s
                start date & time: %s
                end date & time: %s
                duration: %s
            """.formatted(getTitle(), getDescription(), getStartDate(), getEndDate(), getDuration());
    }
}
/*
    @Override
    public void editEvent() {
        int option;

        do { //Picking one option to change a field
            Validate.println("""
                    Do you want to Change:
                    1) Title
                    2) Description
                    3) Date & Time
                    4) End Date & Time
                    5) or Exit""");
            option = Validate.checkAndReturnIntBetween(1, 5);
            switch (option) {
                case 1 -> setTitlePrompt();
                case 2 -> setDescriptionPrompt();
                case 3 -> setStartDatePrompt();
                case 4 -> setEndDatePrompt();
            }
        } while (option != 5);
    }
*/
    /*
    private void setEndDatePrompt() {
        OurDateTime endDate;
        do {
            Validate.print("\nType the new, end date & time\t");
            endDate = OurDateTime.Functionality.dateAndTime();
            if (endDate.getCalculationFormat() < getStartDate().getCalculationFormat())
                 Validate.println("End date can't be before start date");

        } while (endDate.getCalculationFormat() < getStartDate().getCalculationFormat());

        setEndDate(endDate);
        setDuration(getStartDate(), endDate);
    }
    public void setEndDate(OurDateTime endDate) {this.endDate = endDate;}

*/