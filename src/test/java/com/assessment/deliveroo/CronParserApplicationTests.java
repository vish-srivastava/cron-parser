package com.assessment.deliveroo;

import com.assessment.deliveroo.model.CronExpression;
import com.assessment.deliveroo.parser.CronExpressionParser;
import com.assessment.deliveroo.parser.CronParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CronParserApplicationTests {
    // ✅ Test standard cron parser with valid expressions
    @Test
    public void testValidStandardCronExpression() {
        String cronExpression = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronExpressionParser parser = CronParser.getParser(cronExpression);
        CronExpression cron = parser.parse(cronExpression);

        assertEquals("0 15 30 45", cron.getMinutesAsString());
        assertEquals("0", cron.getHoursAsString());
        assertEquals("1 15", cron.getDaysOfMonthAsString());
        assertEquals("1 2 3 4 5", cron.getDaysOfWeekAsString());
        assertEquals("/usr/bin/find", cron.getCommand());
    }

    //
    @Test
    public void testStandardCronWildcardParsing() {
        String cronExpression = "* * * * * /usr/bin/every_minute";
        CronExpressionParser parser = CronParser.getParser(cronExpression);
        CronExpression cron = parser.parse(cronExpression);

        assertEquals(60, cron.getMinutesAsString().split(" ").length);
        assertEquals(24, cron.getHoursAsString().split(" ").length);
        assertEquals("/usr/bin/every_minute", cron.getCommand());
    }

    // ✅ Test extended cron parser (Throws NotImplementedException for now)
    @Test
    public void testExtendedCronExpression_NotImplemented() {
        String cronExpression = "@yearly /usr/bin/yearly_task";
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> CronParser.getParser(cronExpression));
        assertTrue(exception.getMessage().contains("Unsupported cron format"));
    }

    // ❌ Negative test cases for ambiguous expressions
    @Test
    public void testAmbiguousCronExpression_ShouldThrowError() {
        String cronExpression = "0 12 ? * MON-FRI /usr/bin/script";
        CronExpressionParser parser = CronParser.getParser(cronExpression);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(cronExpression));
        assertTrue(exception.getMessage().contains("Invalid number format in cron expression"));
    }

    @Test
    public void testInvalidCronExpression_ExtraFields() {
        String cronExpression = "*/10 5 * * * * extra";
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> CronParser.getParser(cronExpression));
        assertTrue(exception.getMessage().contains("Unsupported cron format. Expected 6 fields."));
    }

    //
    @Test
    public void testInvalidRange() {
        String cronExpression = "5-2 * * * * /usr/bin/invalid_range";
        CronExpressionParser parser = CronParser.getParser(cronExpression);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(cronExpression));
        assertTrue(exception.getMessage().contains("Invalid range"));
    }

    //
    @Test
    public void testInvalidStepValue() {
        String cronExpression = "*/0 * * * * /usr/bin/invalid_step";
        CronExpressionParser parser = CronParser.getParser(cronExpression);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(cronExpression));
        assertTrue(exception.getMessage().contains("Error parsing cron field: Increment not supported"));
    }

    //
    @Test
    public void testOutOfRangeMinute() {
        String cronExpression = "70 * * * * /usr/bin/out_of_range_minute";
        CronExpressionParser parser = CronParser.getParser(cronExpression);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(cronExpression));
        assertTrue(exception.getMessage().contains("Error parsing cron field"));
    }

    //
    @Test
    public void testOutOfRangeHour() {
        String cronExpression = "* 25 * * * /usr/bin/out_of_range_hour";
        CronExpressionParser parser = CronParser.getParser(cronExpression);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(cronExpression));
        assertTrue(exception.getMessage().contains("Value out of bounds:"));
    }

    //
    @Test
    public void testInvalidCharacters() {
        String cronExpression = "abc * * * * /usr/bin/non_numeric";
        CronExpressionParser parser = CronParser.getParser(cronExpression);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(cronExpression));
        assertTrue(exception.getMessage().contains("Invalid number format in cron expression"));
    }
}