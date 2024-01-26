* [OurCalendar class General Information](#ourcalendar-class-general-information)
  * [Methods](#methods)
  * [sortList](#sortlistarraylistevent-events)
  * [compareEvents](#compareeventsevent-event1-event-event2)
  * [eventsBetween](#eventsBetweenint-minTime-int-MaxTime)
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
    - projects which is an ArrayList for the projects
    - version of the calendar
    - ProdId which is a unique id for each event
    - CalScale for the calendar

<hr>

## Methods

<hr>

- ### `sortList(ArrayList<Event> events)`
    - Gets the ArrayList of the events.
    - Sorts the ArrayList based on DateTime by comparing and swapping the events with the help of the method.
  [compareEvents](#compareeventsevent-event1-event-event2)
<hr>

- ### `eventsBetween(int minTime, int MaxTime)`
    - Gets 2 values a minimum time and a maximum time
    - based on the event, and the min, max time it returns the events, that are in an arrayList, between that time.

<hr>

- ### `compareEvents(Event event1, Event event2)`
    - Gets 2 events from the [sortList](#sortlistarraylistevent-events)
    - It returns a long number from the difference of the Calculation Format of the two events.
<hr>

- ### `printUpcomingEvents(App.AppChoices choice)`
    - it produces 2 values a minimum time and a maximum time
    - minimum time : this is the current time because we want to print future events
    - maximum time : this time depends on what the user wants to see
    - if its day : sets the time to be 23:59 and giving the end of the day
    - if its week : we set the time to be 23:59 and giving the day that's sunday 
    - if its month : we set the time to be 23:59 and giving the day that's the last day of the month
    - it calls the eventsBetween to print between min and max time

<hr>

- ### `printOldEvents(App.AppChoices choice)`
    - Gets a choice, if the user wants to see the Events that are sorted to see the pastday, week and month Events.
    - minimum time : this time depends on what the user wants to see
    - maximum time : this is the current time because we want to print past events
    - if its pastday : we set the minimum time to be 00:00 and giving the start of the day
    - if its pastweek : we set the minimum time to be 00:00 and giving the day that's monday
    - if its pastmonth : we set the minimum time to be 00:00 and giving the day that's the first day of the month
    - it calls the eventsBetween to print between min and max time

<hr>

- ### `printUnfinishedProject(App.AppChoices choice)`
    - Gets a choice, if the user wants to see the Projects that are sorted to see the todo and due Projects.
    - If a project is not Completed:
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