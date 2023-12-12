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
    It gets a choice, if the user wants to see the Upcoming Events that are sorted for today, this week or this month.
    If the user chose, for today:
        The variable format, from the current time, the day gets increaced by 1 and the time is 00:00.
        If it's necessary, it changes month or/and year and the day becomes 1.
        Then we call timePeriod to print the events for today.
    This week & this month:
        The variable format, increaces the month by 1, the day becomes 1, and the time is 00:00.
        If the month is 12, the year is increaced by 1 and the month is January.
        Then we call timePeriod to print the events for this week/month.

    printOldEvents:
    It gets a choice, if the user wants to see the Old Events that are sorted for today, this week or this month.
    If the user chose, for today:
        The variable format, from the current time, changes the time to 00:00.
        Then we call timePeriod to print the events for today.
    This week & this month:
        The variable format, changes the day to 1, and the time to 00:00.
        Then we call timePeriod to print the events for this week/month.

    printUnfinishdProject:
    It gets a choice, if the user wants to see the Projects that are sorted to see the todo and due Projects.
    If a project is not Finished:
        The due becomes a Calculation Format, and we check if it surpasses the realDateTime
        If the user chose, todo:
            We check if the due format is greater than the realDateTime Format and then the project is printed
        Else:
            We check if the due format is less than the realDateTime Format and we print this project.

    eventSearch:
