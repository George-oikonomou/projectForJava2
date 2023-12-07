import net.fortuna.ical4j.model.property.Status;

public class Project extends Event{
    private final Status status;
    private final OurDateTime due;    //date and time of project deadline
    private boolean isFinished = false;     // boolean value that tells if the project is finished
    public void setFinished(boolean finished) { isFinished = finished; }

    public Project(String title, String description, OurDateTime due, Status status) {
        super(null, title, description);
        this.due = due;
        this.status = status;
        setFinished(status == Status.VTODO_COMPLETED);
    }

    public Status getStatus() { return status; }
    public OurDateTime getDue() { return due; }
    public boolean getIsFinished() { return isFinished; }

    @Override
    public String toString() {
        return String.format("""
        Project:
            title: %s
            description: %s
            due: %s
            status: %s
        """, getTitle(), getDescription(), getDue(), getStatus().getValue());
    }
}
/*
    @Override
    public void editEvent() {
        int option;

        do {//Picking one option to change a field
            Validate.println("""
                    Do you want to Change:
                    1) Title
                    2) Description
                    3) Date & Time
                    4) Deadline
                    5) Or Exit""");
            option = Validate.checkAndReturnIntBetween(1, 5);
            switch (option) {
                case 1 -> setTitlePrompt();
                case 2 -> setDescriptionPrompt();
                case 3 -> setStartDatePrompt();
                case 4 -> setDeadlinePrompt();
            }
        } while (option != 5);
    }
     private void setDeadlinePrompt() {
        Validate.print("\nType the new deadline:\t");
        setDeadline(Validate.deadline(getStartDate()));

    }

    public void setDeadline(OurDateTime deadline) { this.deadline = deadline; }
*/
