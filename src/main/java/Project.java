public class Project extends Event{
    private OurDateTime deadline;    //date and time of project deadline
    private boolean isFinished = false;     // boolean value that tells if the project is finished

    public Project(OurDateTime dateTime, String title, String description, OurDateTime deadline) {
        super(dateTime, title, description);
        this.deadline = deadline;
    }

    public OurDateTime getDeadline() { return deadline; }
    public void setDeadline(OurDateTime deadline) { this.deadline = deadline; }

    public boolean getIsFinished() { return isFinished; }
    public void setFinished(boolean finished) { isFinished = finished; }

    private void setDeadlinePrompt() {
        Validate.print("\nType the new deadline:\t");
        setDeadline(Validate.deadline(getDateTime()));
    }
    @Override
    public String toString() {
        return """
            Project:
                dateTime: %s
                title: %s
                description: %s
                deadline: %s
                isFinished: %s
            """.formatted(getDateTime(), getTitle(), getDescription(), deadline, isFinished);
    }
}