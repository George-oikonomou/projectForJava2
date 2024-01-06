import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeManager {
    public static JSpinner configureTime(int width, int height) {
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

        JFormattedTextField timeTextField = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
        timeTextField.setEditable(false);
        timeTextField.setPreferredSize(new Dimension(width, height));//40 20

        return timeSpinner;
    }

    public  static JDateChooser configureDate(int width, int height) {
        JDateChooser DateChooser = new JDateChooser();

        DateChooser.setPreferredSize(new Dimension(width, height)); // 100 20
        DateChooser.setMinSelectableDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());

        JTextField startDateTextField = ((JTextField) DateChooser.getDateEditor().getUiComponent());
        startDateTextField.setEditable(false);

        return DateChooser;
    }

    public static OurDateTime extractDateTime(JDateChooser dateChooser, JSpinner timeSpinner) {
        Date dateTime = (Date) timeSpinner.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);

        int year = dateChooser.getCalendar().get(Calendar.YEAR);
        int month = dateChooser.getCalendar().get(Calendar.MONTH) + 1; // Month is zero-based
        int day = dateChooser.getCalendar().get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new OurDateTime(year, month, day, hour, minute);
    }
}