package Utilities;


import Models.OurDateTime;
import com.toedter.calendar.JDateChooser;
import org.apache.commons.lang3.time.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeManager {

    /**
     * Returns a JSpinner that allows the user to select a time.
     * @param width The width of the JSpinner.
     * @param height The height of the JSpinner.
     * @return A JSpinner that allows the user to select a time.
     */
    public static JSpinner configureTime(int width, int height) {
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

        JFormattedTextField timeTextField = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
        timeTextField.setEditable(false);
        timeTextField.setPreferredSize(new Dimension(width, height));//40 20

        return timeSpinner;
    }

    /**
     * Returns a JDateChooser that allows the user to select a date.
     * @param width The width of the JDateChooser.
     * @param height The height of the JDateChooser.
     * @return A JDateChooser that allows the user to select a date.
     */
    public  static JDateChooser configureDate(int width, int height) {
        JDateChooser DateChooser = new JDateChooser();

        DateChooser.setPreferredSize(new Dimension(width, height)); // 100 20

        JTextField startDateTextField = ((JTextField) DateChooser.getDateEditor().getUiComponent());
        startDateTextField.setEditable(false);

        return DateChooser;
    }


    /**
     * @param dateChooser The JDateChooser that allows the user to select a date.
     * @param timeSpinner The JSpinner that allows the user to select a time.
     * @return An OurDateTime object that represents the date and time selected by the user.
     */
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

    public static class StartDateChangeListener implements PropertyChangeListener {
        private final JDateChooser endDateChooser;
        public StartDateChangeListener(JDateChooser endDateChooser) {
            this.endDateChooser = endDateChooser;
        }

        /**
         * Sets the minimum selectable date of the end date JDateChooser to the date selected by the user in the start date JDateChooser.
         * @param evt The PropertyChangeEvent that indicates that the user has selected a date in the start date JDateChooser.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Date newStartDate = (Date) evt.getNewValue();// Get the NEW SELECTED start date

            if (newStartDate == null) return;

            Date startDate = DateUtils.truncate(newStartDate, Calendar.DATE);// Truncate the NEW SELECTED start date to the date only
            if (startDate != null && endDateChooser.getDate() != null) {
                Date endDate = DateUtils.truncate(endDateChooser.getDate(), Calendar.DATE);// Truncate the end date to the date only

                if (endDate.before(startDate)) { // If the end date is before the NEW SELECTED start date, clear the end date
                    endDateChooser.setDate(null);
                }
            }
            endDateChooser.setMinSelectableDate(newStartDate); // Set the minimum selectable date of the end date JDateChooser to the NEW SELECTED start date
        }
    }
}