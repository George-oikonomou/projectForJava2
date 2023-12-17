import net.fortuna.ical4j.model.property.Duration;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Appointment extends Event {
    private String duration;
    private OurDateTime endDate;

    public Appointment(OurDateTime startDate, OurDateTime endDate, String title, String description) {
        super(startDate, title, description);
        this.endDate = endDate;
        setDurationWithDtend(startDate,endDate);
    }

    public Appointment(OurDateTime startDate, Duration icsDuration, String title, String description){
        super(startDate, title, description);
        setDurationWithIcsDuration(startDate ,icsDuration);
    }

    public OurDateTime getEndDate() {return endDate;}

    public String getDuration() {return duration;}
    public void setDurationWithDtend(OurDateTime dateTime, OurDateTime endDate) {
        LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute());
        LocalDateTime end   = LocalDateTime.of( endDate.getYear(),  endDate.getMonth(),  endDate.getDay(),  endDate.getHour(),  endDate.getMinute());

        long durationInMinutes = java.time.Duration.between(start, end).toMinutes();
        this.duration = calculateDurationInDays((int) durationInMinutes);
    }
    private void setDurationWithIcsDuration(OurDateTime dateTime , Duration icsDuration){

        // Define a pattern to match the iCal4j duration format
        Pattern pattern = Pattern.compile("P(?:(\\d+)D)?T(?:(\\d+)H)?(?:(\\d+)M)?");
        Matcher matcher = pattern.matcher(icsDuration.getValue());

        int days = 0 ,
            hours = 0 ,
            minutes = 0;


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
    }//todo

    private static int parseIntOrZero(String value) {
        return value != null ? Integer.parseInt(value)
                             : 0;
    }

    public String calculateDurationInDays(int durationInMin) {
        int days    = durationInMin / 1440;
        int hours   = (durationInMin % 1440) / 60;
        int minutes = (durationInMin % 1440) % 60;

        return String.format("%s%s%s",days  > 0 ? days  + (days  == 1 ? " day "  : " days " ) : "",
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