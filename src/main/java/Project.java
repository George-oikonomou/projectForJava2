import net.fortuna.ical4j.model.property.Status;

public class Project extends Event{
    private Status status;
    private final OurDateTime deadline;    //date and time of project deadline
    private final boolean isFinished = false;     // boolean value that tells if the project is finished

    public Project(String title, String description, OurDateTime deadline, Status status) {
        super(null, title, description);

        this.deadline = deadline;
        this.status = status;
        setFinished(status == Status.VTODO_COMPLETED);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        setFinished(status == Status.VTODO_COMPLETED);
    }

    public OurDateTime getDeadline() { return deadline; }
    public boolean getIsFinished() { return isFinished; }

    @Override
    public String toString() {
        return String.format("""
        Project:
            title: %s
            description: %s
            deadline: %s
            status: %s
        """, getTitle(), getDescription(), getDeadline(), getStatus().getValue());
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

      public void setFinished(boolean finished) { isFinished = finished; }
    public void setDeadline(OurDateTime deadline) { this.deadline = deadline; }
*/
