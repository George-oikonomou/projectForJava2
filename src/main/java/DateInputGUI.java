import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class DateInputGUI extends JPanel {
    JComboBox<String> yearsComboBox;
    JComboBox<String> monthsComboBox;
    JComboBox<String> daysComboBox;
    JComboBox<String> hourComboBox;
    JComboBox<String> minutesComboBox;

    private static final String[] years = {"Year","2024", "2025","2026","2027","2028","2029",
            "2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040"};
    private static final String[] months = {"Month","January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
    private static final String[] days = {"Day","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private static final String[] hours = {"Hour","00","01","02","03","04",
            "06","07","08","09","10","11","12","13","14","15","16",
            "17", "18","19","20","21","22","23"};
    private static final String[] minutes = {"Minute","00","01","02","03","04",
            "06","07","08","09","10","11","12","13","14","15","16",
            "17", "18","19","20","21","22","23","24","25","26","27","28",
            "29","30","31","32","33","34","35","36", "37", "38","39","40",
            "41","42","43","44","45","46", "47", "48","49","50","51","52",
            "53","54","55","56","57","58", "59"};

    public DateInputGUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        yearsComboBox = new JComboBox<>();
        monthsComboBox = new JComboBox<>();
        daysComboBox = new JComboBox<>();
        hourComboBox = new JComboBox<>();
        minutesComboBox = new JComboBox<>();

        configureSingleSelection(yearsComboBox, years);
        configureSingleSelection(monthsComboBox, months);
        configureSingleSelection(daysComboBox, days);
        configureSingleSelection(hourComboBox, hours);
        configureSingleSelection(minutesComboBox, minutes);

        // Set preferred size to ensure the combo boxes have enough space to display
        for (JComboBox<?> comboBox : new JComboBox[]{yearsComboBox, monthsComboBox, daysComboBox, hourComboBox, minutesComboBox}) {
            comboBox.setPreferredSize(new Dimension(70, 25)); // Adjust the size as needed
            comboBox.setEditable(false); // Make the combo box non-editable
            add(comboBox);
        }
        setPreferredSize(new Dimension(410, 47));
    }

        public OurDateTime dateTime() {
            String selectedYear = (String) yearsComboBox.getSelectedItem();
            String selectedMonth = (String) monthsComboBox.getSelectedItem();
            String selectedDay = (String) daysComboBox.getSelectedItem();
            String selectedHour = (String) hourComboBox.getSelectedItem();
            String selectedMinute = (String) minutesComboBox.getSelectedItem();


            if (Objects.equals(selectedYear, years[0]) || Objects.equals(selectedMonth, months[0]) || Objects.equals(selectedDay, days[0])
                    || Objects.equals(selectedHour, hours[0]) || Objects.equals(selectedMinute, minutes[0])) {
                JOptionPane.showMessageDialog(this, "Please select values for all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
                return null; // or throw an exception if you prefer
            }

            try {
                // Parse selected values to integers
                int year = Integer.parseInt(Objects.requireNonNull(selectedYear));
                int month = parseMonth(Objects.requireNonNull(selectedMonth));
                int day = Integer.parseInt(Objects.requireNonNull(selectedDay));
                int hour = Integer.parseInt(Objects.requireNonNull(selectedHour));
                int minute = Integer.parseInt(Objects.requireNonNull(selectedMinute));

                // Create and return OurDateTime object
                return new OurDateTime(year, month, day, hour, minute);
            } catch (NumberFormatException | NullPointerException e) {
                JOptionPane.showMessageDialog(this, "Invalid input format", "Input Error", JOptionPane.ERROR_MESSAGE);
                return null; // or throw an exception if you prefer
            }
        }

        // Helper method to parse month string to integer
        private int parseMonth(String month) {
            return switch (month) {
                case "January" -> 1;
                case "February" -> 2;
                case "March" -> 3;
                case "April" -> 4;
                case "May" -> 5;
                case "June" -> 6;
                case "July" -> 7;
                case "August" -> 8;
                case "September" -> 9;
                case "October" -> 10;
                case "November" -> 11;
                case "December" -> 12;
                default -> throw new IllegalArgumentException("Invalid month: " + month);
            };
        }

        // ... (existing code)



    private static void configureSingleSelection(JComboBox<String> comboBox,String[] items) {
        ComboBoxModel<String> model = new DefaultComboBoxModel<>(items);
        comboBox.setModel(model);
        comboBox.setSelectedIndex(0);
        comboBox.setMaximumRowCount(8); // Set the maximum number of visible items in the dropdown
    }
}
