import java.util.Scanner;

public class Validate {  //Checking the input value

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
    public static OurDateTime dateAndTime() {
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
        day = day(month ,year);

        //Create Date
        date = date(year, month, day);

        //hour
        System.out.print("\nTIME\nHour:\t");
        hour = checkAndReturnIntBetween(0, 23);

        //minute
        System.out.print("\tMinute:\t");
        minute = checkAndReturnIntBetween(0, 59);

        //create time
        time = time(minute,hour);
        System.out.println();

        //return object
        return new OurDateTime(year, month, day, hour, minute);
    }

    //Making an object DateTime & checking the value of date & time for the deadline
    public static OurDateTime deadline (OurDateTime dateTime) {//todo needs to count for hours even if the projects deadline is not on the same day
        int year, month, day;
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
        day = day(month,year);

        if (year == dateTime.getYear() && month == dateTime.getMonth() && day == dateTime.getDay()) {
            //TIME
            System.out.print("\nTIME\nHour:\t");
            hour = checkAndReturnIntBetween(0, 23);

            if (hour == dateTime.getHour()) {
                System.out.print("\tMinute:\t");
                minute = checkAndReturnIntBetween(dateTime.getMinute() + 15, 59);  // TODO: 11/11/23 check if it goes above 59 
            } else {
                System.out.print("\tMinute:\t");
                minute = checkAndReturnIntBetween(0, 59);
            }
        }

        date = date(year, month, day);
        time = time(minute,hour);

        System.out.println();
        return new OurDateTime(year, month, day, hour, minute);
    }

    public static String time(int minute, int hour){

        if (minute >= 10) {
            return Integer.toString(hour).concat(":").concat(Integer.toString(minute));
        } else {
            if (hour >= 10)
                return Integer.toString(hour).concat(":0").concat(Integer.toString(minute));
            else
                return  "0".concat(Integer.toString(hour)).concat(":0").concat(Integer.toString(minute));
        }
    }
    public static String date(int year, int month, int day) {
        if (day >= 10) {
            if (month >= 10)
                 return Integer.toString(day).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
            else
                return Integer.toString(day).concat("/0").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
        } else {
            if (month >= 10)
                return  "0".concat(Integer.toString(day)).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
            else
                return  "0".concat(Integer.toString(day)).concat("/0").concat(Integer.toString(month)).concat("/").concat(Integer.toString(year));
        }
    }
    public static int day(int month, int year){
        if (month == 1 || month == 3 || month == 5 || month == 6 || month == 8 || month == 10 || month == 12) {
            return  checkAndReturnIntBetween(1, 31);
        } else if (month != 2) {
            return checkAndReturnIntBetween(1, 30);
        } else {
            if (year % 4 == 0) {
                return checkAndReturnIntBetween(1, 29);
            } else {
                return checkAndReturnIntBetween(1, 28);
            }
        }
    }

    // TODO: 11/11/23 make a method that checks if a title already exists in the same type of an event
}