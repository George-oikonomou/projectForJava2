OurCalendar:

Fields:
    ArrayList of events

Methods:
    
    addEvents: 
    It makes new Appointments/Projects and adds them to the ArrayList events.

    sortList:
    It sorts the ArrayList of the events.

    compareEvents:
    It returns the difference between the two DateTimes.

    timePeriod:
    It gets two Calculation Formats, to check if the event is between this time period, and it gets a code.
    If the code is 2, it's for Upcoming Events for this week, if it's 3, its for Old Events this week and code 1 is for the other prints.
    If the event in the enhanced for-loop is between this time period, it prints the event.
    For code 2: We check if the difference between the day of the event and the currentDay ,plus, the Day of the week, is not greater than 7
    For code 3: We check if the difference between the day of the event and the currentDay plus 1, is not greater than the Day of the week.
        If thats true, we print the event.

    printUpcomingEvents:
    It gets a choice, if the user wants to see the Upcoming Events for today, this week or this month.
    If the user chose, for today:
        The variable format, from the current time, the day gets increaced by 1 and the time is 00:00.
        Then we call timePeriod to print the events for today.
    This week & this month:
        The variable format, increaces the month by 1, the day becomes 1, and the time is 00:00.
        If the month is 12, the year is increaced by 1 and the month is January.
        Then we call timePeriod to print the events for this week/month.

    printOldEvents:
    It gets a choice, if the user wants to see the Old Events for today, this week or this month.
    If the user chose, for today:
        The variable format, from the current time, changes the time to 00:00.
        Then we call timePeriod to print the events for today.
    This week & this month:
        The variable format, changes the day to 1, and the time to 00:00.
        Then we call timePeriod to print the events for this week/month.

    printUnfinishdProject:
    