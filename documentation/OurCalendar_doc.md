* [OurCalendar class General Information](#ourcalendar-class-general-information)
  * [Methods](#methods)
    * [addEvents](#addevents)
    * [sortList](#sortlistarraylistevent-events)
    * [compareEvents](#compareeventsevent-event1-event-event2)
    * [printBetween]
    * [printUpcomingEvents](#printupcomingeventsappappchoices-choice)
    * [printOldEvents](#printoldeventsappappchoices-choice)
    * [printUnfinishedProject](#printoldeventsappappchoices-choice)
<hr>

## OurCalendar Class General Information
- This class has :
    - constructor
    - Setters and Getters

- In this class we have the field:
    - events which is an ArrayList for the calendar
    - version of the calendar
    - ProdId which is a unique id for each event
    - CalScale for the calendar

<hr>

## Methods

<hr>

- ### `addEvents()`
  - The user chooses to make a new event.
  - The user gives the event a title, description, the DateTime the event starts and ends or the DateTime of the deadline.
  - The new event is added to the ArrayList.

<hr>

- ### `sortList(ArrayList<Event> events)`
    - Gets the ArrayList of the events.
    - Sorts the ArrayList based on DateTime by comparing and swapping the events with the help of the method.
  [compareEvents](#compareeventsevent-event1-event-event2)
<hr>

- ### `compareEvents(Event event1, Event event2)`
    - Gets 2 events from the [sortList](#sortlistarraylistevent-events)
    - It returns a long number from the difference of the Calculation Format of the two events.
<hr>

- ### `printBetween(long minTime, long maxTime)`
    - gets 2 times a and prints any event between those 2 times. 
    - it uses the calculated format of the event object which we can easily do comparisons with


- ### `printUpcomingEvents(App.AppChoices choice)`
    - it produces to values a minimum time and a maximum time
    - minimum time : this is the current time because we want to print future event
    - maximum time : this time depends on what the user wants to see
    - it calls the printBetween to print between min and max time

- ### `printOldEvents(App.AppChoices choice)`
    - Gets a choice, if the user wants to see the Old Events that are sorted for today, this week or this month.
    - If the user chose, for today:
      The variable format, from the current time, changes the time to 00:00.
      Then we call [timePeriod](#timeperiodlong-maxtime-long-mintime-int-code) to print the events for today.
    - If the user chose, this week or this month:
      The variable format, changes the day to 1, and the time to 00:00.
      Then we call [timePeriod](#timeperiodlong-maxtime-long-mintime-int-code) to print the events for this week/month.
<hr>

- ### `printUnfinishedProject(App.AppChoices choice)`
    - Gets a choice, if the user wants to see the Projects that are sorted to see the todo and due Projects.
    - If a project is not Finished:
    The due becomes a Calculation Format, and we check if it surpasses the realDateTime.
      - If the user chose, todo:
      We check if the due format is greater than the realDateTime Format and then the project is printed.
      - Else:
      We check if the due format is less than the realDateTime Format and then the project is printed.
<hr>

- [Event Class General Information](Events_doc.md)
- [ICSFile Class General Information](ICSFile_doc.md)
- [OurDateTime Class General Information](OurDateTime_doc.md)
- [TimeTeller Class General Information](TimeTeller_doc.md)
- [App Class General Information](App_doc.md)
- [Validate Class General Information](Validate_doc.md)

[Back to top](#ourcalendar-class-general-information)