public class Project extends Event{
    private final OurDateTime deadline;    //date and time of project deadline
    private final boolean isFinished = false;     // boolean value that tells if the project is finished

    public Project(OurDateTime startDate, String title, String description, OurDateTime deadline) {
        super(startDate, title, description);
        this.deadline = deadline;
    }

    public OurDateTime getDeadline() { return deadline; }
    public boolean getIsFinished() { return isFinished; }

    @Override
    public String toString() {
        return """
            Project:
                title: %s
                description: %s
                Start date & time: %s
                deadline: %s
                isFinished: %s
            """.formatted(getTitle(), getDescription(),getStartDate(),  deadline, isFinished);
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
