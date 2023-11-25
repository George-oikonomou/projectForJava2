
public class Project extends Event{
    private OurDateTime deadline;    //date and time of project deadline
    private boolean isFinished = false;     // boolean value that tells if the project is finished

    public Project(OurDateTime dateTime, String title, String description, OurDateTime deadline) {
        super(dateTime, title, description);
        this.deadline = deadline;
    }

    public OurDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(OurDateTime deadline) {this.deadline = deadline;}

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
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
                    4) Deadline
                    5) Or Exit""");
            option = Validate.checkAndReturnIntBetween(1, 5);
            switch (option) {
                case 1: {
                    Validate.print("\nType the new title:\t");
                    setTitle(Validate.strInput());
                    break;
                }
                case 2: {
                    Validate.print("\nType the new description:\t");
                    setDescription(Validate.strInput());
                    break;
                }
                case 3: {
                    Validate.print("\nType the new date & time:\t");
                    setDateTime(OurDateTime.Functionality.dateAndTime(true));
                    break;
                }
                case 4: {
                    Validate.print("\nType the new duration:\t");
                    setDeadline(Validate.deadline(getDateTime()));
                    break;
                }
            }
        } while (option != 5);
    }

    @Override
    public String toString() {
        return "Project:\n" +
                "\tdateTime:" + getDateTime() + "\n" +
                "\ttitle:" + getTitle() + "\n" +
                "\tdescription:" + getDescription() + "\n" +
                "\tdeadline:" + deadline + "\n" +
                "\tisFinished:" + isFinished + "\n";
    }
}
