
public class Project extends Event{
    private OurDateTime deadline;    //date and time of project deadline
    private boolean isFinished = false;     // boolean value that tells if the project is finished

    public Project(OurDateTime dateTime, String title, String description, OurDateTime deadline) {
        super(dateTime, title, description);
        this.deadline = deadline;
    }

    public OurDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(OurDateTime deadline) {this.deadline = deadline;}

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public void editEvent() {
        int option;

        do {
            //Picking one option to change a field
            System.out.println("Do you want to Change:\n1) Title\n2) Description\n3) Date & Time" +
                    "4) Deadline\n5) Finished Project or\n6) Exit");
            option = Validate.checkAndReturnIntBetween(1, 4);
            switch (option) {
                case 1: {
                    System.out.println("\nType the new title:\t");
                    setTitle(Validate.strInput());
                    break;
                }
                case 2: {
                    System.out.println("\nType the new description:\t");
                    setDescription(Validate.strInput());
                    break;
                }
                case 3: {
                    System.out.println("\nType the new date & time:\t");
                    setTitle(Validate.strInput());
                    break;
                }
                case 4: {
                    System.out.println("\nType the new duration:\t");
                    setDeadline(Validate.deadline(getDateTime()));
                    break;
                }
                case 5: {
                    System.out.println("\nType the new duration:\t");
                    setFinished(!isFinished());
                    break;
                }
            }
        } while (option != 6);
    }

    @Override
    public String toString() {
        return "Project{" +
                "dateTime:" + super.getDateTime() +
                "title:" + super.getTitle() +
                "description:" + super.getDescription() +
                "deadline" + deadline +
                "isFinished" + isFinished +
                '}';
    }
}
