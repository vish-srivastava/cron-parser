package com.assessment.deliveroo.parser;

import java.util.HashMap;
import java.util.Map;

// static template/ configuration
public class CronFieldTemplate {
    private static final Map<String, Integer[]> INTEGER_RANGES = new HashMap<>();
    private static final Map<String, Map<String, Integer>> STRING_MAPPINGS = new HashMap<>();

    static {
        // Integer-based fields
        INTEGER_RANGES.put("minute", new Integer[]{0, 59});
        INTEGER_RANGES.put("hour", new Integer[]{0, 23});
        INTEGER_RANGES.put("dayOfMonth", new Integer[]{1, 31});
        INTEGER_RANGES.put("month", new Integer[]{1, 12});
        INTEGER_RANGES.put("year", new Integer[]{2001, 2025});
        INTEGER_RANGES.put("dayOfWeek", new Integer[]{0, 6}); // Sunday = 0, Saturday = 6

        // String-based mappings
        Map<String, Integer> monthMapping = new HashMap<>();
        monthMapping.put("JAN", 1);
        monthMapping.put("FEB", 2);
        monthMapping.put("MAR", 3);
        monthMapping.put("APR", 4);
        monthMapping.put("MAY", 5);
        monthMapping.put("JUN", 6);
        monthMapping.put("JUL", 7);
        monthMapping.put("AUG", 8);
        monthMapping.put("SEP", 9);
        monthMapping.put("OCT", 10);
        monthMapping.put("NOV", 11);
        monthMapping.put("DEC", 12);
        STRING_MAPPINGS.put("month", monthMapping);

        Map<String, Integer> dayOfWeekMapping = new HashMap<>();
        dayOfWeekMapping.put("SUN", 0);
        dayOfWeekMapping.put("MON", 1);
        dayOfWeekMapping.put("TUE", 2);
        dayOfWeekMapping.put("WED", 3);
        dayOfWeekMapping.put("THU", 4);
        dayOfWeekMapping.put("FRI", 5);
        dayOfWeekMapping.put("SAT", 6);
        STRING_MAPPINGS.put("dayOfWeek", dayOfWeekMapping);
    }

    /**
     * Gets the integer range for a field.
     */
    public static Integer[] getIntegerRange(String field) {
        return INTEGER_RANGES.get(field);
    }

    /**
     * Gets the string-to-integer mapping for a field.
     */
    public static Map<String, Integer> getStringMapping(String field) {
        return STRING_MAPPINGS.get(field);
    }
}
