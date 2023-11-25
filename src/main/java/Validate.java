import java.util.Scanner;

public class Validate {  //Checking the input value

    //Checking if the String is empty
    public static String strInput() {
        Scanner scanner = new Scanner(System.in);
        String string;
        do {
            string = scanner.nextLine();
            if (string.isEmpty()) {
                Validate.println("You typed something wrong. Try again.");
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
                        Validate.println("The number you typed is invalid, it should be between " + param1 + " and " +
                                 param2 + ". Try again.\n");
                    } else {
                        scanner.nextLine();
                        check = true;
                    }
                }while (temp < param1 || temp > param2);
            } catch (Exception e) {
                Validate.println("You didnt type a number.Try again.");
                scanner.nextLine();
            }
        }
        return temp;
    }


    //Making an object DateTime & checking the value of date & time for the deadline
    public static OurDateTime deadline (OurDateTime dateTime) {
        int year, month, day, hour, minute;


        //Year:
        Validate.print("\nDATE\nYear:\t");
        year = checkAndReturnIntBetween(dateTime.getYear(), 2024);

        //Month:
        Validate.print("\tMonth:\t");
        if (year == dateTime.getYear()) {
            month = checkAndReturnIntBetween(dateTime.getMonth(), 12);

            //Day:
            Validate.print("\tDay:\t");
            if (month == dateTime.getMonth()) {
                day = day(month, year, dateTime.getDay());

                //TIME
                Validate.print("\nTIME\nHour:\t");
                if (day == dateTime.getDay()) {
                    if (dateTime.getMinute() + 15 > 59) {
                        hour = checkAndReturnIntBetween(dateTime.getHour() + 1, 23);

                        Validate.print("\tMinute:\t");
                        minute = checkAndReturnIntBetween(0, 59);
                    } else {
                        hour = checkAndReturnIntBetween(dateTime.getHour(), 23);

                        Validate.print("\tMinute:\t");
                        if (hour == dateTime.getHour()) {
                            minute = checkAndReturnIntBetween(dateTime.getMinute() + 15, 59);
                        } else {
                            minute = checkAndReturnIntBetween(0, 59);
                        }
                    }

                } else {
                    //TIME
                    hour = checkAndReturnIntBetween(0, 23);
                    Validate.print("\tMinute:\t");
                    minute = checkAndReturnIntBetween(0, 59);
                }
            } else {
                day = day(month, year, 1);
                //TIME
                Validate.print("\nTIME\nHour:\t");
                hour = checkAndReturnIntBetween(0, 23);
                Validate.print("\tMinute:\t");
                minute = checkAndReturnIntBetween(0, 59);
            }
        } else {
            month = checkAndReturnIntBetween(1, 12);
            //Day:
            Validate.print("\tDay:\t");
            day = day(month,year, 1);
            //TIME
            Validate.print("\nTIME\nHour:\t");
            hour = checkAndReturnIntBetween(0, 23);
            Validate.print("\tMinute:\t");
            minute = checkAndReturnIntBetween(0, 59);
        }

        Validate.println("");
        return new OurDateTime(year, month, day, hour, minute);
    }

    public static String time(int minute, int hour) {
        return String.format("%02d:%02d", hour, minute);
    }

    public static String date(int year, int month, int day) {
        return String.format("%02d/%02d/%d", day, month, year);
    }

    public static int day(int month, int year, int day){
        if (month == 1 || month == 3 || month == 5 || month == 6 || month == 8 || month == 10 || month == 12) {
            return  checkAndReturnIntBetween(day, 31);
        } else if (month != 2) {
            return checkAndReturnIntBetween(day, 30);
        } else {
            if (year % 4 == 0) {
                return checkAndReturnIntBetween(day, 29);
            } else {
                return checkAndReturnIntBetween(day, 28);
            }
        }
    }
    public static String Title(OurCalendar calendar,int type){
       String title = strInput();
       while (checkIfTitleExists(calendar, title, type)) {
           Validate.println("This title already exists. Try again.");
           title = strInput();
       }
       return title;
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }
    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public static boolean checkIfTitleExists(OurCalendar ourCalendar, String title, int type) {
        Event event = ourCalendar.eventSearch(title, type);
        return event != null;
    }


}