public class Project extends Event{
    private DateTime deadline;    //date and time of project deadline
    private boolean isFinished;     // boolean value that tells if the project is finished

    public Project(String title, String description, DateTime dateTime, DateTime deadline, boolean isFinished) {
        super(dateTime, description, title);
        this.deadline = deadline;
        this.isFinished = isFinished;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
