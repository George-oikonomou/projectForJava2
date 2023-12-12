import java.util.Scanner;

public  class Validate {//General class for validating input

    /**
     * @return String that is not empty
     */
    public static String strInput() {
        Scanner scanner = new Scanner(System.in);
        String string;

        while ( (string = scanner.nextLine()).isEmpty() ) // while the String is empty repeat the loop asking for input
            Validate.println("input can't be empty. Try again.");//and print the message

        return string;
    }

    /**
     * @param param1 the lower bound
     * @param param2 the upper bound
     * @return int between lower and upper bound
     */
    public static int checkAndReturnIntBetween(int param1, int param2) {

        int temp = -1;

        do {
            try {
                String input = strInput();
                temp = Integer.parseInt(input.trim());//trims the input and parse it to int

                if (temp < param1 || temp > param2)
                    println("The number you typed is invalid, it should be between " + param1 + " and " + param2 + ". Try again.\n");
            } catch (NumberFormatException e) {
                    println("Your input is not valid. Provide a valid number. Try again.\n");
            }
        } while (temp < param1 || temp > param2);

        return temp;
    }


    /**
     * @param startDate the start date and time of the event that the user is creating
     * @return OurDateTime  object with a date and time after the startDate
     */
    public static OurDateTime DateTime(OurDateTime startDate) {
        int year = printMessageAndGetIntBetween("\nDATE\n\tYear:\t", startDate.getYear(), 2100);
        int month = printMessageAndGetIntBetween("\tMonth:\t", (year == startDate.getYear()) ? startDate.getMonth() : 1, 12);
        int day = printMessageAndGetIntBetween("\tDay:\t", (month == startDate.getMonth() && year == startDate.getYear())  ? startDate.getDay() : 1, getDaysInMonth(month, year));
        int hour = printMessageAndGetIntBetween("\nTIME\n\tHour:\t", (day == startDate.getDay() && month == startDate.getMonth() && year == startDate.getYear()) ? startDate.getHour() : 0, 23);
        int minute = printMessageAndGetIntBetween("\tMinute:\t", (day == startDate.getDay() && month == startDate.getMonth() && year == startDate.getYear() && hour == startDate.getHour()) ? startDate.getMinute() : 0, 59);

        println("");
        return new OurDateTime(year, month, day, hour, minute);
    }

    /**
     * @param prompt the message to be displayed to the user
     * @param lowerBound the lower bound of the input
     * @param upperBound the upper bound of the input
     * @return int between lower and upper bound
     */
    public static int printMessageAndGetIntBetween(String prompt, int lowerBound, int upperBound) {
        print(prompt);
        return checkAndReturnIntBetween(lowerBound, upperBound);
    }

    /**
     * @param month the month to get the days of
     * @param year the year to get the days of (used for leap years)
     * @return int the number of days in the month
     */
    public static int getDaysInMonth(int month, int year) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 2 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            default -> 30;
        };
    }

    /**
     * @param ourCalendar the calendar to search for the title
     * @param title the title to search for
     * @param type the type of event to search for
     * @return boolean true if a title exists for that type in given calendar, false otherwise
     */
    public static boolean checkIfTitleExists(OurCalendar ourCalendar, String title, int type) { return ourCalendar.eventSearch(title, type) != null; }


    /**
     * @param  calendar to search for the title
     * @param  type of event to search for
     * @return String the title of the event that doesn't exist for that type in that calendar
     */
    public static String Title(OurCalendar calendar,int type){
       String title = strInput();
       while (checkIfTitleExists(calendar, title, type)) {
           Validate.println("This title already exists. Try again.");
           title = strInput();
       }
       return title;//returns the unique title
    }

    //for stage 2
    public static void println(Object obj) { System.out.println(obj); }
    public static void print(Object obj) { System.out.print(obj); }
}
