import net.fortuna.ical4j.model.DateTime;

public class Project extends Event{
    private OurDateTime deadline;    //date and time of project deadline
    private boolean isFinished;     // boolean value that tells if the project is finished

    public Project(OurDateTime dateTime, String title, String description, OurDateTime deadline, boolean isFinished) {
        super(dateTime, title, description);
        this.deadline = deadline;
        this.isFinished = isFinished;
    }

    public OurDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(OurDateTime deadline) {this.deadline = deadline;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
