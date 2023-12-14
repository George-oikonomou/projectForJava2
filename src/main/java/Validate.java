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
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     * @return int between lower and upper bound
     */
    public static int checkAndReturnIntBetween(int lowerBound, int upperBound) {

        int temp = -1;

        do {
            try {
                String input = strInput();
                temp = Integer.parseInt(input.trim());//trims the input and parse it to int

                if (temp < lowerBound || temp > upperBound)
                    println("The number you typed is invalid, it should be between " + lowerBound + " and " + upperBound + ". Try again.\n");
            } catch (NumberFormatException e) {
                    println("Your input is not valid. Provide a valid number. Try again.\n");
            }
        } while (temp < lowerBound || temp > upperBound);

        return temp;
    }


    /**
     * @param startDate  of the event that the user is creating
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
     * @param prompt  to be displayed to the user
     * @param lowerBound  of the input
     * @param upperBound  of the input
     * @return int between lowerBound and upperBound
     */
    public static int printMessageAndGetIntBetween(String prompt, int lowerBound, int upperBound) {
        print(prompt);
        return checkAndReturnIntBetween(lowerBound, upperBound);
    }

    /**
     * @param month to get the days of
     * @param year to get the days of (used for leap years)
     * @return the number of days in the month
     */
    public static int getDaysInMonth(int month, int year) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 2) {
            return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
        } else {
            return 30;
        }
    }


    //for stage 2
    public static void println(Object obj) { System.out.println(obj); }
    public static void print(Object obj) { System.out.print(obj); }
}
