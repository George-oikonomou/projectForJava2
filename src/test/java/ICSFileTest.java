//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class ICSFileTest{
//@Test
//public void icsFormatToOurDateTimeParsesDateOnly() {
//    ICSFile icsFile = new ICSFile("string.ics");
//    OurDateTime result = OurDateTime.Functionality.ICSFormatToOurDateTime("20220101");
//    OurDateTime expected = new OurDateTime(2022, 1, 1, 0, 0);
//
//    // Compare individual fields
//    assertEquals(expected.getYear(), result.getYear());
//    assertEquals(expected.getMonth(), result.getMonth());
//    assertEquals(expected.getDay(), result.getDay());
//    assertEquals(expected.getHour(), result.getHour());
//    assertEquals(expected.getMinute(), result.getMinute());
//}
//
//
//@Test
//public void icsFormatToOurDateTimeParsesDateAndTime() {
//    OurDateTime result = OurDateTime.Functionality.ICSFormatToOurDateTime("20220101T1030");
//    OurDateTime expected = new  OurDateTime(2022, 1, 1, 10, 30);
//    // Compare individual fields
//    assertEquals(expected.getYear(), result.getYear());
//    assertEquals(expected.getMonth(), result.getMonth());
//    assertEquals(expected.getDay(), result.getDay());
//    assertEquals(expected.getHour(), result.getHour());
//    assertEquals(expected.getMinute(), result.getMinute());
//}
//
//@Test
//public void icsFormatToOurDateTimeThrowsExceptionForInvalidFormat() {
//    assertThrows(NumberFormatException.class, () -> OurDateTime.Functionality.ICSFormatToOurDateTime("invalid"));
//}
//}