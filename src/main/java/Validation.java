import java.util.Scanner;

public class Validation {
    //Checking the input value
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

    public static int checkInt(int param1, int param2) {
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

    public static String date () {
        int year, month, day;
        String y, m, d;

        System.out.print("\nDate:\nYear:\t");
        year = checkInt(2023,2024);

        System.out.print("\tMonth:\t");
        month = checkInt(1, 12);

        System.out.print("\tDay:\t");
        if (month == 1 || month == 3 || month == 5 || month == 6 || month == 8 || month == 10 || month == 12) {
            day = checkInt(1, 31);
        } else if (month != 2) {
            day = checkInt(1, 30);
        } else {
            if (year % 4 == 0) {
                day = checkInt(1, 29);
            } else {
                day = checkInt(1, 28);
            }
        }
        y = Integer.toString(year);
        m = Integer.toString(month);
        d = Integer.toString(day);
        return d.concat("/").concat(m).concat("/").concat(y);
    }

    public static String time () {
        int hour, min;
        String h, m;

        System.out.print("\nTime:\nHour:\t");
        hour = checkInt(0, 23);

        System.out.print("\tMinute:\t");
        min = checkInt(0, 59);

        h = Integer.toString(hour);
        m = Integer.toString(min);
        if (min >= 10) {
            return h.concat(":").concat(m);
        } else {
            if (hour >= 10)
                return h.concat(":0").concat(m);
            else
                return "0".concat(h).concat(":0").concat(m);
        }
    }

}
