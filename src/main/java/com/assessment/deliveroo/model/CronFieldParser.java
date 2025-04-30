package com.assessment.deliveroo.model;

import com.assessment.deliveroo.parser.CronFieldTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Generic Cron Field Parser supporting:
 * - Integer-based ranges (0-59, 1-31, etc.)
 * - String mappings for extension (MON-THU, JAN-DEC, etc.)
 */
@SuppressWarnings("ALL")
public class CronFieldParser<T extends Comparable<T>> {

    private final T minValue; // to validate field's min value
    private final T maxValue; // to validate field's max value
    private final Function<String, T> parser; // to parse the generic type to concrete object : Integer, String etc
    private final Map<String, T> stringMappings; // integer to string mapping for month, day of week

    /**
     * Constructor for integer-based parsing using predefined ranges from CronFieldTemplate.
     */
    public CronFieldParser(String fieldName, Function<String, T> parser) {
        Integer[] range = CronFieldTemplate.getIntegerRange(fieldName);
        this.minValue = (T) range[0];
        this.maxValue = (T) range[1];
        this.parser = parser;
        this.stringMappings = (Map<String, T>) CronFieldTemplate.getStringMapping(fieldName);
    }

    /**
     * Parses a cron field into a list of valid values.
     */
    public List<T> parseField(String field) {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("Cron field cannot be null or empty.");
        }

        List<T> result = new ArrayList<>();

        try {
            if (field.equals("*")) {
                return minValue != null ? generateRange(minValue, maxValue) : new ArrayList<>(stringMappings.values());
            }

            for (String part : field.split(",")) {
                parsePart(part.trim(), result);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in cron expression: " + field, e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing cron field: " + e.getMessage() + field, e);
        }

        return result;
    }

    private void parsePart(String part, List<T> result) {
        if (part.contains("/")) { // step
            handleStepValues(part, result);
        } else if (part.contains("-")) { // range
            handleRangeValues(part, result);
        } else { // single value
            handleSingleValue(part, result);
        }
    }

    private void handleStepValues(String part, List<T> result) { // */5
        String[] stepParts = part.split("/");
        if (stepParts.length != 2) {
            throw new IllegalArgumentException("Invalid step syntax: " + part);
        }

        T start = stepParts[0].equals("*") ? minValue : parseValue(stepParts[0]); // 2001
        T step =  parser.apply(stepParts[1]);// 5

        if (compare(start, minValue) < 0 || compare(start, maxValue) > 0) {
            throw new IllegalArgumentException("Step start value out of bounds: " + start + ". Allowed range: " + minValue + " - " + maxValue);
        }

        for (T i = start; compare(i, maxValue) <= 0; i = increment(i, step)) {
            result.add(i);
        }
    }


    private void handleRangeValues(String part, List<T> result) {
        String[] rangeParts = part.split("-");
        if (rangeParts.length != 2) {
            throw new IllegalArgumentException("Invalid range syntax: " + part);
        }

        T start = parseValue(rangeParts[0]); // also validated self value within range
        T end = parseValue(rangeParts[1]); //

        if (compare(start, end) > 0) { // valid range
            throw new IllegalArgumentException("Invalid range: Start value " + start + " cannot be greater than end value " + end);
        }

        result.addAll(generateRange(start, end));
    }


    private void handleSingleValue(String part, List<T> result) {
        T value = parseValue(part);
        result.add(value);
    }

    private T parseValue(String value) {
        T parsedValue;

        // Handle string-based mappings (e.g., MON -> 1)
        if (stringMappings != null && stringMappings.containsKey(value.toUpperCase())) {
            parsedValue = stringMappings.get(value.toUpperCase());
        } else {
            parsedValue = parser.apply(value.trim());
        }

        // Validate range
        if (compare(parsedValue, minValue) < 0 || compare(parsedValue, maxValue) > 0) {
            throw new IllegalArgumentException("Value out of bounds: " + value + ". Allowed range: " + minValue + " - " + maxValue);
        }

        return parsedValue;
    }

    private List<T> generateRange(T start, T end) {
        List<T> range = new ArrayList<>();
        for (T i = start; compare(i, end) <= 0; i = increment(i, parser.apply("1"))) {
            range.add(i);
        }
        return range;
    }


    private T increment(T value, T step) {
        if (!(value instanceof Integer || value instanceof Double || value instanceof Float || value instanceof Long)) {
            throw new UnsupportedOperationException("Increment not supported for type: " + value.getClass());

        }
        Integer incrementValue = Integer.valueOf(((Integer) value) + ((Integer) step));

        if (incrementValue == 0)
            throw new UnsupportedOperationException("Increment not supported for type: " + value.getClass());

        return (T) incrementValue;
    }

    private int compare(T a, T b) {
        return a.compareTo(b);
    }
}