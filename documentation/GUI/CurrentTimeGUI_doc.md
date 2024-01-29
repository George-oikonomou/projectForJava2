# *CurrentTimeGUI class extends JPanel implements TimeListener*

## Fields:
- JLabel timeLabel
- LocalDateTime currentTime

## Methods:
- CurrentTimeGUI(): **sets the size, adds a timeLabel calls and a time teller with a TimeListener.**
- timeChanged(TimeEvent timeEvent): **updates the currentTime and prints it in the timeLabel.**
- updateTimeLabel(LocalDateTime dateTime): **makes a formatter for the hour,time and minutes to run in the ReminderPanel.**

## Usage:
- This class is responsible for the current time panel. It prints the live time in the ReminderPanel.