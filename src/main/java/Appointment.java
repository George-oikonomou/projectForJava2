import java.time.Duration;
import java.time.LocalDateTime;

public class Appointment extends Event {
    private String duration;
    private OurDateTime endDate;

    public Appointment(OurDateTime dateTime, OurDateTime endDate, String title, String description) {
        super(dateTime, title, description);
        this.endDate = endDate;
        setDuration(dateTime,endDate);
    }

    public String getDuration() {return duration;}
    public void setDuration(OurDateTime dateTime, OurDateTime endDate) {
        LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute());
        LocalDateTime end = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDay(), endDate.getHour(), endDate.getMinute());
        long durationInMinutes = Duration.between(start, end).toMinutes();
        this.duration = calculateDurationInDays((int) durationInMinutes);
    }

    public OurDateTime getEndDate() {return endDate;}
    public void setEndDate(OurDateTime endDate) {this.endDate = endDate;}

    private void setEndDatePrompt() {
        while (true) {
            Validate.print("\nType the new, end date & time\t");
            OurDateTime endDate = OurDateTime.Functionality.dateAndTime();

            if (endDate.getCalculationFormat() >= getDateTime().getCalculationFormat()) {
                setEndDate(endDate);
                setDuration(getDateTime(), endDate);
                break;
            }

            Validate.println("End date can't be before start date");
        }
    }

    public String calculateDurationInDays(int durationInMin) {
        int days = durationInMin / 1440;
        int hours = (durationInMin % 1440) / 60;
        int minutes = (durationInMin % 1440) % 60;

        StringBuilder result = new StringBuilder();

        return  result.append(days > 0 ? days + (days == 1 ? " day " : " days ") : "")
                      .append(hours > 0 ? hours + (hours == 1 ? " hour " : " hours ") : "")
                      .append(minutes > 0 || result.isEmpty() ? minutes + (minutes == 1 ? " minute" : " minutes") : "")
                      .toString()
                      .trim();
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
                case 3 -> setDateTimePrompt();
                case 4 -> setEndDatePrompt();
            }
        } while (option != 5);
    }
*/
    @Override
    public String toString() {
        return """
            Appointment:
                title: %s
                description: %s
                dateTime: %s
                end date & time: %s
                duration: %s
            """.formatted(getTitle(), getDescription(), getDateTime(), getEndDate(), getDuration());
    }
}