import java.io.*;
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
    public static void LoadEvents(String filePath) {
        ArrayList<Event> events = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean insideEvent = false;

            String title, summary, dateTime = null;
            OurDateTime ourDateTime = null;
            String type = null;

            while ((line = br.readLine()) != null) {
                if((line.startsWith("TYPE:"))){
                    type = line.substring("TYPE:".length());
                }
                if(line.startsWith("BEGIN:VEVENT")){
                    insideEvent = true;
                }else if(line.startsWith("END:VEVENT")){
                    insideEvent = false;
                }else if(insideEvent && type != null){

                        if(line.startsWith("DTSTART:")){
                            dateTime = line.substring("DTSTART:".length());
                            ourDateTime = OurDateTime.Functionality.StringToOurDateTime(dateTime);
                        }else if(line.startsWith("SUMMARY:")){
                            title = line.substring("SUMMARY:".length());
                        }else if(line.startsWith("DESCRIPTION:")){
                            summary = line.substring("DESCRIPTION:".length());
                        }

                        if(type.equals("PROJECT")){

                            if(line.startsWith("DEADLINE:")){

                            }
                        }
                    }
                }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public void StoreEvents(ArrayList<Event> events){

        try{
            /*
             * checks if the File Class found an existing file with the same name
             * of if it did not find and created a new one
             */
            File file = new File(filePath);
            if(file.createNewFile()){
                System.out.println("File created " + file.getName());
            }else {
                System.out.println("File already exists. Overwriting");
            }

            FileWriter fileWriter = new FileWriter(file);
            /*
                these contents must be at the start of every calendar
             */
            fileWriter.write("BEGIN:VCALENDAR\n");
            fileWriter.write("VERSION:2.0\n");
            fileWriter.write("PRODID:-//My Calendar//EN\n");
            fileWriter.write("CALSCALE:GREGORIAN\n");
                /*
                we loop throw all of the events saving their information depending on the type of event
                 */
            for (Event event : events){
                fileWriter.write("BEGIN:VEVENT\n");

                if (event instanceof Project){
                    fileWriter.write("TYPE:PROJECT\n");
                    fileWriter.write("DEADLINE:" + ((Project) event).getDeadline().toString() + "\n");
                    fileWriter.write("PROJECT_STATUS" + (((Project) event).isFinished() ? "Finished" : "Ongoing") + "\n");
                } else if (event instanceof Appointment){
                    fileWriter.write("TYPE:APPOINTMENT\n");
                    fileWriter.write("DURATION:" + ((Appointment) event).getDuration());
                }else {
                    fileWriter.write("TYPE:EVENT\n");
                }
                fileWriter.write("DTSTART:" + event.getDateTime().toString() + "\n");
                fileWriter.write("SUMMARY:" + event.getTitle() + "\n");
                fileWriter.write("DESCRIPTION:" + event.getDescription() + "\n");
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
