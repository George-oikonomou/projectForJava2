import net.fortuna.ical4j.model.DateTime;

public class Project extends Event{
    private Datetime deadline;    //date and time of project deadline
    private boolean isFinished;     // boolean value that tells if the project is finished

    public Project(Datetime dateTime, String title, String description, Datetime deadline, boolean isFinished) {
        super(dateTime, title, description);
        this.deadline = deadline;
        this.isFinished = isFinished;
    }

    public Datetime getDeadline() {
        return deadline;
    }

    public void setDeadline(Datetime deadline) {
        this.deadline = deadline;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
