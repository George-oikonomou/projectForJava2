import java.util.Scanner;

public class Validate {//Checking the input value

    //Checking if the String is empty
    public static String strInput() {
        Scanner scanner = new Scanner(System.in);
        String string;

        while ( (string = scanner.nextLine()).isEmpty() )
            Validate.println("input can't be empty. Try again.");

        return string;
    }

    //Checking if the integer has valid value
    public static int checkAndReturnIntBetween(int param1, int param2) {
        int temp = -1;

        do {
            try {
                String input = strInput();  // Use the strInput method to get a valid string input
                temp = Integer.parseInt(input.trim());

                if (temp < param1 || temp > param2)
                    System.out.println("The number you typed is invalid, it should be between " + param1 + " and " + param2 + ". Try again.\n");
            } catch (NumberFormatException e) {
                System.out.println("Your input is not valid. Provide a valid number. Try again.\n");
            }
        } while (temp < param1 || temp > param2);

        return temp;
    }

    //Making an object DateTime & checking the value of date & time for the deadline
    public static OurDateTime deadline(OurDateTime dateTime) {
        int year = getInput("\nDATE\n\tYear:\t", dateTime.getYear(), 2024);
        int month = getInput("\tMonth:\t", (year == dateTime.getYear()) ? dateTime.getMonth() : 1, 12);
        int day = getInput("\tDay:\t", (month == dateTime.getMonth() && year == dateTime.getYear())  ? dateTime.getDay() : 1, getDaysInMonth(month, year));
        int hour = getInput("\nTIME\n\tHour:\t", (day == dateTime.getDay() && month == dateTime.getMonth() && year == dateTime.getYear()) ? dateTime.getHour() : 0, 23);
        int minute = getInput("\tMinute:\t", (day == dateTime.getDay() && month == dateTime.getMonth() && year == dateTime.getYear() && hour == dateTime.getHour()) ? dateTime.getMinute() : 0, 59);

        Validate.println("");
        return new OurDateTime(year, month, day, hour, minute);
    }

    private static int getInput(String prompt, int lowerBound, int upperBound) {
        Validate.print(prompt);
        return checkAndReturnIntBetween(lowerBound, upperBound);
    }

    private static int getDaysInMonth(int month, int year) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 2 -> (year % 4 == 0) ? 29 : 28;
            default -> 30;
        };
    }

    public static String time(int minute, int hour) { return String.format("%02d:%02d", hour, minute); }
    public static String date(int year, int month, int day) { return String.format("%02d/%02d/%d", day, month, year); }

    public static int day(int month, int year, int day) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> checkAndReturnIntBetween(day, 31);
            case 2 -> checkAndReturnIntBetween(day, (year % 4 == 0) ? 29 : 28);
            default -> checkAndReturnIntBetween(day, 30);
        };
    }

    public static String Title(OurCalendar calendar,int type){
       String title = strInput();
       while (checkIfTitleExists(calendar, title, type)) {
           Validate.println("This title already exists. Try again.");
           title = strInput();
       }
       return title;
    }

    public static void println(Object obj) { System.out.println(obj); }
    public static void print(Object obj) { System.out.print(obj); }
    public static void printf(String format, Object... args) { System.out.printf(format, args); }

    public static boolean checkIfTitleExists(OurCalendar ourCalendar, String title, int type) { return ourCalendar.eventSearch(title, type) != null; }
}