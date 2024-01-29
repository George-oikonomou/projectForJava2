# *CurrentTimeGUI class extends JPanel implements TimeListener*

## Fields:
- JLabel timeLabel
- LocalDateTime currentTime

## Methods:
- CurrentTimeGUI(): **constructor that sets the size, adds a timeLabel calls and a time teller with a TimeListener.**
- updateAndDisplayTime(LocalDateTime dateTime): **makes a formatter for the hour and minutes to run in the ReminderPanel.**
- timeChanged(TimeEvent timeEvent): **updates the currentTime and prints it in the timeLabel.**

## Usage:
- This class is responsible for the current time panel. It prints the live time in the ReminderPanel.