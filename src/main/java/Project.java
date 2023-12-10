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