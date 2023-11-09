import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;


public class App {

    public static void main(String[] args) {
        /*
          check to see if the correct number of arguments was entered in the terminal
         */
        if (args.length != 4 && args.length != 5) {
            System.out.println("Incorrect argument input");
            System.exit(1);
        }

        if (args.length == 4) {
            // TODO: 9/11/23 code for editing the calendar
        } else {
            /*
                start of some code to load the file
             */
            boolean fileExists;
            fileExists = ICSFile.ICSFilePath(args[4]);
            if (fileExists) {
                try {
                    ICSFile.LoadEvents(args[4]);
                } catch (ParserException | IOException e) {
                    // TODO: 9/11/23 handle exception 
                }
            }
        }
    }
}




