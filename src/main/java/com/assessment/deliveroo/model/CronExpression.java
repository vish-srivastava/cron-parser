package com.assessment.deliveroo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a parsed cron expression with generic field types.
 */

public class CronExpression<T> {
    private final List<T> minutes;
    private final List<T> hours;
    private final List<T> daysOfMonth;
    private final List<T> months;
    private final List<T> daysOfWeek;
    private final List<T> years;
    private final String command;

    public CronExpression(List<T> minutes, List<T> hours, List<T> daysOfMonth,
                          List<T> months, List<T> daysOfWeek, List<T> years, String command) {
        this.minutes = minutes;
        this.hours = hours;
        this.daysOfMonth = daysOfMonth;
        this.months = months;
        this.daysOfWeek = daysOfWeek;
        this.years = Objects.isNull(years) ? new ArrayList<>() : years;
        this.command = command;
    }

    public void printExpanded() {
        StringBuilder output = new StringBuilder();

        output.append(String.format("%-14s%s%n", "minute", formatList(minutes)))
                .append(String.format("%-14s%s%n", "hour", formatList(hours)))
                .append(String.format("%-14s%s%n", "day of month", formatList(daysOfMonth)))
                .append(String.format("%-14s%s%n", "month", formatList(months)))
                .append(String.format("%-14s%s%n", "day of week", formatList(daysOfWeek)));

        if (!years.isEmpty()) {
            output.append(String.format("%-14s%s%n", "year", formatList(years)));
        }

        output.append(String.format("%-14s%s%n", "command", command));

        System.out.print(output);
    }


    private String formatList(List<T> values) {
        return values.stream().map(String::valueOf).collect(Collectors.joining(" "));
    }

    public String getMinutesAsString() {
        return formatList(minutes);
    }

    public String getHoursAsString() {
        return formatList(hours);
    }

    public String getDaysOfMonthAsString() {
        return formatList(daysOfMonth);
    }

    public String getMonthsAsString() {
        return formatList(months);
    }

    public String getDaysOfWeekAsString() {
        return formatList(daysOfWeek);
    }

    public String getCommand() {
        return command;
    }
}