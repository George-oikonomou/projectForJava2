# TimeTeller Usage
- ## Object:
    `teller`
- ## Methods:
  - ### `teller.now()`:
    This method prints the current DateTime in a Calculation Format and its type LocalDateTime with the name `realTime`.
    - If its necessary to change the current DateTime, we can use the `LocalDateTime.of()`
    and we give the below arguments:
        - year
        - month
        - dayOfMonth
        - hour
        - minute
  - ### `TimeService.stop()`:
    This method close the TimeTeller at the end of main class.