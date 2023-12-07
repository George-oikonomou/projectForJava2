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
        setDeadline(Validate.DateTime(getDateTime()));
    }


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
                case 3 -> setDateTimePrompt();
                case 4 -> setDeadlinePrompt();
            }
        } while (option != 5);
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
