# *DateTimeManager class*

## Methods:
- configureTime(int width, int height): **creates time and sets its size for the events for the GUI with a JSpinner.**
- configureDate(int width, int height): **creates date and sets its size for the events for the GUI with a JDateChooser.**
- extractDateTime(JDateChooser dateChooser, JSpinner timeSpinner): **extracts the date and time from the GUI and returns a LocalDateTime object.**

### Inner Class: StartDateChangeListener implements PropertyChangeListener
- *Fields*: 
    - JDateChooser endDateChooser
- StartDateChangeListener(JDateChooser endDateChooser): **Constructor**
- propertyChange(PropertyChangeEvent evt): **sets the minimum date of the end date chooser to the start date chooser. Also, if the user adds both dates and then tries to change the start date to a date that's after the EndDate, the endDate value becomes null.**

## Usage:
- This class is used for the classes with GUI events, to create a date and time and to convert a DateChooser and the JSpinner to a DateTime.