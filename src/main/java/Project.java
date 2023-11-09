public class Project extends Event{
    private String deadline;    //date and time of project deadline
    private boolean isFinished;     // boolean value that tells if the project is finished

    public Project(String title, String description, String date, String time, String deadline, boolean isFinished) {
        super(title, description, date, time);
        this.deadline = deadline;
        this.isFinished = isFinished;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
