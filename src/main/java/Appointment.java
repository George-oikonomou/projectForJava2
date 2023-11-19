import java.time.Duration;

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
            System.out.println("You are editing the Appointment " + getTitle());
            //Picking one option to change a field
            System.out.println("Do you want to Change:\n1) Title\n2) Description\n3) Date & Time" +
                    "4) Duration or\n5) Exit");
            option = Validate.checkAndReturnIntBetween(1, 5);
            switch (option) {
                case 1: {
                    System.out.print("\nType the new title:\t");
                    setTitle(Validate.strInput());
                    break;
                }
                case 2: {
                    System.out.print("\nType the new description:\t");
                    setDescription(Validate.strInput());
                    break;
                }
                case 3: {
                    System.out.print("\nType the new date & time:\t");
                    setDateTime(OurDateTime.Functionality.dateAndTime(true));
                    break;
                }
                case 4: {
                    System.out.print("\nType the new duration:\t");
                    setDuration(Validate.checkAndReturnIntBetween(15, 6 * 60));  //duration is minimum 15 minutes & maximum 6 hours
                    break;
                }
            }
        } while (option != 5);
    }
    @Override
    public String toString() {
        return "Appointment:\n" +
                "\tdateTime:" + getDateTime() + "\n" +
                "\ttitle:" + getTitle() + "\n" +
                "\tdescription:" + getDescription() + "\n" +
                "\tdurationInMin:" + durationInMin + "\n";
    }
}