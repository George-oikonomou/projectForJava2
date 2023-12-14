
* [SuperClass `Events` general information](#superclass-general-information)
    * [SubClasses](#subclasses)
        * [Appointment](#appointment)
           * [Methods](#methods)
             * [setDurationWithDtend](#setdurationwithdtendourdatetime-datetime-ourdatetime-enddate)
             * [setDurationWithIcsDuration](#setdurationwithicsdurationourdatetime-datetime-ourdatetime-enddate)
             * [parseIntOrZero](#parseintorzerostring-value)
             * [calculateDurationInDays](#calculatedurationindaysint-durationinmin)
       

## Superclass general information:

The superclass is named `Event`.
   - This class has :
        - a constructor
        - Setters and Getters
        - A method that generates the UUID for the event.

   - In this class we have the fields:
        - dateTime for the new event
        - title and description for the event
        - uuid which is an id that is given to each event, in the file

<hr>

## Subclasses:
    
  - ### Appointment
      - In this class we have a field for:
         - duration of each appointment, 
         - and an endDateTime of each appointment.
        
      - This class has :
        - `2 constructors `
          - one for `duration` 
          - one for `start` and` end` `DateTime`
        - Setters and Getters and 
        - toString().

  - ### Methods

    - ### `setDurationWithDtend(OurDateTime dateTime, OurDateTime endDate)`

      - Gets the start and the end of DateTime
      - It calculates the duration in minutes between the two DateTimes
      - it calls [calculateDurationInDays](#calculatedurationindaysint-durationinmin) and  stores the duration.

    - ### `setDurationWithIcsDuration(OurDateTime dateTime, OurDateTime endDate)`

    - ### `parseIntOrZero(String value)`

        - gets a String value, 
        - if it's not null it returns the value as a string 
        - else returns 0.

    - ### `calculateDurationInDays(int durationInMin)`

        - gets the duration in minutes,
        - it transforms it to a string of numbers for days, hours and minutes.
        - it returns the String.

<hr>

- ### Project:
    - This class has 
      - a constructor, 
      - Setters and Getters 
      - and toString().

    - In this class we have the fields:
      - status
      - due for the deadline of the project
      - isFinished to check if the project is finished or not

<hr>

- [ICSFile Class General Information](ICSFile_doc.md)
- [OurCalendar Class General Information](OurCalendar_doc.md)
- [OurDateTime Class General Information](OurDateTime_doc.md)
- [TimeTeller Class General Information](TimeTeller_doc.md)
- [Validate Class General Information](Validate_doc.md)


[Back to top](#superclass-general-information)
