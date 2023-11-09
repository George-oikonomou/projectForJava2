import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ICSFile {

    /**
     * method that returns the path of the file entered in the command line if the file
     * exists
     * @param fileName    the file name
     * @return   returns its path
     */
    public static Path ICSFilePath(String fileName){
        Path path = Paths.get(fileName);

        if(Files.exists(path)){
            System.out.println("file exists");
            return  path;
        }
        return null;
    }


    /**
     * method that will load the contents of the file int the array list of events
     * in the calendar folder
     * @param path the path of the file we want to load
     */
    public static void LoadEvents(Path path){


    }

}
