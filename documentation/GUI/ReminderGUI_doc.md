# *ReminderGUI class extends JPanel implements TimeListener*

## Fields:
- DefaultListModel<JPanel> listModel
- ArrayList<Event> nextEvent
- JList<JPanel> printList
- OurDateTime liveTime
- JScrollPane scrollPane

## Methods:
- timeChanged(TimeEvent timeEvent): **makes a livetime from OurDateTime and calls the method setNextEvent() and printEvents().**
- ReminderGUI(): **if the arrayList with the files is not empty, it creates a new ArrayList with events sets layout, size, background color and calls the methods setNextEvent(OurMenuGUI.getAllFiles()), printEvents() and registerAsTimeListener().**
- registerAsTimeListener(): **creates a timeTeller and adds the object adds a timeListener.**
- setNextEvent(ArrayList<ICSFile> allFiles): **updates the live time, clears the newEvent arrayList, and it goes through all the files. If the event isn't notified yet and the startDate or the due 
have a time distance of 0 to 30 minutes with the liveTime Format, then we add the event to nextEvent arrayList, the event becomes notified, calls the notification method to play and adds the event to nextEvent.**
- printEvents(): **each event from nextEvent is added to the listModel and the listModel is added to the JList to print the events.**
- playNotificationSound(): **plays the notification sound from reminder.wav.**

## Usage:
- This class is responsible for the reminder panel. It prints the events that are due in the next 30 minutes and plays a notification sound.