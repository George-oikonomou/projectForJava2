package Models;

import net.fortuna.ical4j.model.property.Duration;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Appointment extends Event {
    private String duration;
    private OurDateTime endDate;
    private JPanel panel;

    public Appointment(OurDateTime startDate, OurDateTime endDate, String title, String description, String fileName) {
        super(startDate, title, description, fileName);
        this.endDate = endDate;
        setDurationWithDtend(startDate,endDate);
        setPanel();
    }

    public Appointment(OurDateTime startDate, Duration icsDuration, String title, String description, String fileName){
        super(startDate, title, description,fileName);
        setDurationWithIcsDuration(startDate ,icsDuration);
    }

    public OurDateTime getEndDate() {return endDate;}

    public String getDuration() {return duration;}


    /**
     * @param startDate start date
     * @param endDate end date
     * description: calculates the duration of the event in days, hours and minutes by subtracting the start date from the end date
     */
    public void setDurationWithDtend(OurDateTime startDate, OurDateTime endDate) {
        LocalDateTime start = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDay(), startDate.getHour(), startDate.getMinute());
        LocalDateTime end   = LocalDateTime.of( endDate.getYear(),  endDate.getMonth(),  endDate.getDay(),  endDate.getHour(),  endDate.getMinute());

        long durationInMinutes = java.time.Duration.between(start, end).toMinutes();
        this.duration = calculateDurationInDays((int) durationInMinutes);
    }

    /**
     * @param dateTime start date
     * @param icsDuration duration of the event
     * description: calculates the duration of the event in days, hours and minutes by adding the duration to the start date
     */
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
    }

    /**
     * @param value string to be parsed
     * @return the parsed value or 0 if the value is null
     */
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
    /*
     * description: creates a panel with the event's info
     * usage: used to display the event's info in the main page
     */
    public void setPanel() {
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(100, 85)); // Adjust the size as needed
        panel.add(new JLabel("Title: " + getTitle()));
        panel.add(new JLabel("Description: " + getDescription()));
        panel.add(new JLabel("Start Date: " + getStartDate().toString()));
        panel.add(new JLabel("End Date: " + getEndDate().toString()));
        panel.add(new JLabel("Calendar Name: " + getFileName()));
        panel.putClientProperty("uid", getUuid());
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    public void setEndDate(OurDateTime endDateTime) {
        this.endDate = endDateTime;
    }
}