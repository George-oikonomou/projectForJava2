import java.io.File;
import java.nio.file.Path;

public class App{

    private static final String[] arguments = {"day","week","month","pastday","pastweek","pastmonth","todo","due"};

    public static void main(String[] args) {
        /*
          check to see if the correct number of arguments was entered in the terminal
         */
        if (args.length != 4 && args.length != 5){
            throw new IllegalArgumentException("Incorrect argument input");
        }

        if (args.length == 4){
            // TODO: 9/11/23 code for editing the calendar
        }else{
            /*
                start of some code to load the file
             */
            Path fileExists;
            fileExists = ICSFile.ICSFilePath(args[4]);
            if (fileExists != null){
                ICSFile.LoadEvents(fileExists);
            }
        }
    }




}
