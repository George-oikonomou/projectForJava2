import gr.hua.dit.oop2.calendar.TimeService;
import net.fortuna.ical4j.data.ParserException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {


        System.out.println("add an event enter (1)");

        int choice = input.nextInt();

        switch (choice){
            case 1:
                OurCalendar calendar = new OurCalendar();
                calendar.addEvents();
        }
        TimeService.stop();


    }
}





