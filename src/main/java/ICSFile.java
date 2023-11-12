import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ICSFile {

    String filePath;

    public ICSFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * method that will load the contents of the file int the array list of events
     * in the calendar folder
     * fileName the path of the file we want to load
     */
    public static void LoadEvents(String fileName) {
    }
    public void StoreEvents(ArrayList<Event> events){

        try{
            File file = new File(filePath);
            if(file.createNewFile()){
                System.out.println("File created " + file.getName());
            }else {
                System.out.println("File already exists. Overwriting");
            }

            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write("BEGIN:VCALENDAR\n");
            fileWriter.write("VERSION:2.0\n");
            fileWriter.write("PRODID:-//My Calendar//EN\n");
            fileWriter.write("CALSCALE:GREGORIAN\n");

            for (Event event : events){
                fileWriter.write("BEGIN:VEVENT\n");
                fileWriter.write("DTSTART:" + event.getDateTime() + "\n");
                fileWriter.write("SUMMARY:" + event.getTitle() + "\n");
                fileWriter.write("DESCRIPTION:" + event.getDescription() + "\n");

                if (event instanceof Project){
                    fileWriter.write("DEADLINE:" + ((Project) event).getDeadline() + "\n");
                    fileWriter.write("PROJECT_STATUS" + (((Project) event).isFinished() ? "Finished" : "Ongoing") + "\n");
                }

                if (event instanceof Appointment){
                    fileWriter.write("DURATION:" + ((Appointment) event).getDuration());
                }

                fileWriter.write("END:VEVENT\n");
            }

            fileWriter.write("END:VCALENDAR\n");

            fileWriter.close();
            System.out.println("successfully exported events to " + filePath);

        }catch (IOException e){
            System.out.println("error could not save file");
        }

    }
    /**
     * returns true if file exists
     * @param fileName the file name
     * @return returns its path
     */
    public static boolean ICSFilePath(String fileName) {
        Path path = Paths.get(fileName);

        if (Files.exists(path)) {
            System.out.println("file exists");
            return true;
        }
        return false;
    }
}
