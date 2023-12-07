
import net.fortuna.ical4j.model.property.Duration;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Appointment extends Event {
    private String duration;
  
    private final OurDateTime endDate;
    private Duration icsDuration;

    public Appointment(OurDateTime startDate, OurDateTime endDate, String title, String description) {
        super(startDate, title, description);
        this.endDate = endDate;
         setDurationWithDtend(startDate,endDate);
    }

    
    public Appointment(OurDateTime startDate, Duration icsDuration, String title, String description){
        super(startDate, title, description);
        this.icsDuration = icsDuration;
        setDurationWithIcsDuration(startDate ,icsDuration);
    }

    public String getDuration() {return duration;}
    public void setDurationWithDtend(OurDateTime dateTime, OurDateTime endDate) {
        LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute());
        LocalDateTime end = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDay(), endDate.getHour(), endDate.getMinute());
        long durationInMinutes = java.time.Duration.between(start, end).toMinutes();
        this.duration = calculateDurationInDays((int) durationInMinutes);
    }
    private void setDurationWithIcsDuration(OurDateTime dateTime , Duration icsDuration){

        // Define a pattern to match the iCal4j duration format
        Pattern pattern = Pattern.compile("P(?:(\\d+)D)?T(?:(\\d+)H)?(?:(\\d+)M)?");
        Matcher matcher = pattern.matcher(icsDuration.getValue());

        int days = 0;
        int hours = 0;
        int minutes = 0;

        if (matcher.find()) {
            days = parseIntOrZero(matcher.group(1));
            hours = parseIntOrZero(matcher.group(2));
            minutes = parseIntOrZero(matcher.group(3));
        }
        LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute());
        LocalDateTime end = start.plusDays(days).plusHours(hours).plusMinutes(minutes);
        long durationInMinutes = java.time.Duration.between(start, end).toMinutes();
        this.duration = calculateDurationInDays((int) durationInMinutes);
        this.endDate = new OurDateTime(end.getYear(),end.getDayOfMonth(),end.getDayOfMonth(), end.getHour(), end.getMinute());
    }
    private static int parseIntOrZero(String value) {
        return value != null ? Integer.parseInt(value) : 0;
    }

    public Duration getIcsDuration() {
        return icsDuration;
    }

    public void setIcsDuration(Duration icsDuration) {
        this.icsDuration = icsDuration;
    }

    public OurDateTime getEndDate() {return endDate;}

    public void setEndDate(OurDateTime endDate) {this.endDate = endDate;}

    private void setEndDatePrompt() {
        while (true) {
            Validate.print("\nType the new, end date & time\t");
            OurDateTime endDate = OurDateTime.Functionality.dateAndTime();

            if (endDate.getCalculationFormat() >= getDateTime().getCalculationFormat()) {
                setEndDate(endDate);
                setDurationWithDtend(getDateTime(), endDate);
                break;
            }

            Validate.println("End date can't be before start date");
        }
    }

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
