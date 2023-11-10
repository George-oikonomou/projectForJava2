import java.time.Year;
import java.util.Scanner;

public class Validation {  //Checking the input value

    //Checking if the String is empty
    public static String strInput() {
        Scanner scanner = new Scanner(System.in);
        String string;
        do {
            string = scanner.nextLine();
            if (string.isEmpty()) {
                System.out.println("You typed something wrong. Try again.");
            }
        } while(string.isEmpty());
        return string;
    }

    //Checking if the integer has valid value
    public static int checkAndReturnIntBetween(int param1, int param2) {
        Scanner scanner = new Scanner(System.in);
        int temp = 0;
        boolean check = false;

        while (!check) {
            try {
                do {
                    temp = scanner.nextInt();
                    if (temp < param1 || temp > param2) {
                        System.out.println("The number you typed is invalid, it should be between " + param1 + " and " +
                                 param2 + ". Try again.");
                    } else {
                        scanner.nextLine();
                        check = true;
                    }
                }while (temp < param1 || temp > param2);
            } catch (Exception e) {
                System.out.println("You didnt type a number.Try again.");
                scanner.nextLine();
            }
        }
        return temp;
    }

    //Making an object DateTime & checking the value of date & time
    public static Datetime dateAndTime() {
        int year, month, day, hour, minute;
        String date, time;

        //Year:
        System.out.print("\nDATE\nYear:\t");
        year = checkAndReturnIntBetween(2023,2024);
        //Month:
        System.out.print("\tMonth:\t");
        month = checkAndReturnIntBetween(1, 12);
        //Day:
        System.out.print("\tDay:\t");
        if (month == 1 || month == 3 || month == 5 || month == 6 || month == 8 || month == 10 || month == 12) {
            day = checkAndReturnIntBetween(1, 31);
        } else if (month != 2) {
            day = checkAndReturnIntBetween(1, 30);
        } else {
            if (year % 4 == 0) {
                day = checkAndReturnIntBetween(1, 29);
            } else {
                day = checkAndReturnIntBetween(1, 28);
            }
        }

        if (day >= 10) {
            if (month >= 10)
                date = Integer.toString(day).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
            else
                date = Integer.toString(day).concat("/0").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
        } else {
            if (month >= 10)
                date = "0".concat(Integer.toString(day)).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
            else
                date = "0".concat(Integer.toString(day)).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
        }

        //TIME
        System.out.print("\nTIME\nHour:\t");
        hour = checkAndReturnIntBetween(0, 23);

        System.out.print("\tMinute:\t");
        minute = checkAndReturnIntBetween(0, 59);

        if (minute >= 10) {
            time = Integer.toString(hour).concat(":").concat(Integer.toString(minute));
        } else {
            if (hour >= 10)
                time = Integer.toString(hour).concat(":0").concat(Integer.toString(minute));
            else
                time = "0".concat(Integer.toString(hour)).concat(":0").concat(Integer.toString(minute));
        }
        System.out.println();
        return new Datetime(year, month, day, hour, minute, date, time);
    }

    //Making an object DateTime & checking the value of date & time for the deadline
    public static Datetime deadline (Datetime dateTime) {
        int year, month;
        int day = 0;
        int hour = 0;
        int minute = 0;
        String date, time;

        //Year:
        System.out.print("\nDATE\nYear:\t");
        year = checkAndReturnIntBetween(dateTime.getYear(), 2024);
        //Month:
        System.out.print("\tMonth:\t");
        month = checkAndReturnIntBetween(1, 12);
        //Day:
        System.out.print("\tDay:\t");

        if (month == 1 || month == 3 || month == 5 || month == 6 || month == 8 || month == 10 || month == 12) {
            day = checkAndReturnIntBetween(1, 31);

        } else if (month != 2) {
            day = checkAndReturnIntBetween(1, 30);

        } else {

            if (year % 4 == 0) {
                day = checkAndReturnIntBetween(1, 29);

            } else {
                day = checkAndReturnIntBetween(1, 28);

            }
        }
        if (year == dateTime.getYear() && month == dateTime.getMonth() && day == dateTime.getDay()) {
            //TIME
            System.out.print("\nTIME\nHour:\t");
            hour = checkAndReturnIntBetween(0, 23);

            if (hour == dateTime.getHour()) {
                System.out.print("\tMinute:\t");
                minute = checkAndReturnIntBetween(dateTime.getMinute() + 15, 59);
            } else {
                System.out.print("\tMinute:\t");
                minute = checkAndReturnIntBetween(0, 59);
            }
        }

        if (day >= 10) {
            if (month >= 10)
                date = Integer.toString(day).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
            else
                date = Integer.toString(day).concat("/0").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
         } else {
             if (month >= 10)
                 date = "0".concat(Integer.toString(day)).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
             else
                 date = "0".concat(Integer.toString(day)).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
         }

         if (minute >= 10) {
             time = Integer.toString(hour).concat(":").concat(Integer.toString(minute));
         } else {
             if (hour >= 10)
                 time = Integer.toString(hour).concat(":0").concat(Integer.toString(minute));
             else
                 time = "0".concat(Integer.toString(hour)).concat(":0").concat(Integer.toString(minute));
         }
        System.out.println();
         return new Datetime(year, month, day, hour, minute, date, time);
    }

}