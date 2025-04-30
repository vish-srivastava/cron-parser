package com.assessment.deliveroo.parser;

import com.assessment.deliveroo.model.CronExpression;
import com.assessment.deliveroo.model.CronFieldParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardCronExpressionParser implements CronExpressionParser<Integer> {

    // Declare type of field parsers for specific type of cron string to parse

    private final CronFieldParser<Integer> minuteParser = new CronFieldParser<>("minute", Integer::parseInt);
    private final CronFieldParser<Integer> hourParser = new CronFieldParser<>("hour", Integer::parseInt);
    private final CronFieldParser<Integer> dayOfMonthParser = new CronFieldParser<>("dayOfMonth", Integer::parseInt);
    private final CronFieldParser<Integer> monthParser = new CronFieldParser<>("month", Integer::parseInt);
    private final CronFieldParser<Integer> dayOfWeekParser = new CronFieldParser<>("dayOfWeek", Integer::parseInt);
    private final CronFieldParser<Integer> yearParser = new CronFieldParser<>("year", Integer::parseInt);


    @Override
    public CronExpression<Integer> parse(String cronString) {
        String[] parts = cronString.trim().split("\\s+");

        if (parts.length < 6 || parts.length > 7) {
            throw new IllegalArgumentException("Invalid cron format. Expected 6 fields (minute, hour, dayOfMonth, month, dayOfWeek, command).");
        }

        List<Integer> minutes = minuteParser.parseField(parts[0]);
        List<Integer> hours = hourParser.parseField(parts[1]);
        List<Integer> daysOfMonth = dayOfMonthParser.parseField(parts[2]);
        List<Integer> months = monthParser.parseField(parts[3]);  // Supports JAN-DEC
        List<Integer> daysOfWeek = dayOfWeekParser.parseField(parts[4]); // Supports MON-THU, SUN-SAT

        List<Integer> years = parts.length == 7 ? yearParser.parseField(parts[5]) : List.of();  // Optional year field
        String command = parts.length == 7 ? parts[6] : parts[5]; // Command is always the last field

        return new CronExpression<>(minutes, hours, daysOfMonth, months, daysOfWeek, years, command);
    }
}