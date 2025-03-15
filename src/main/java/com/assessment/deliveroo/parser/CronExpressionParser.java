package com.assessment.deliveroo.parser;


import com.assessment.deliveroo.model.CronExpression;

/**
 * Interface for different types of cron expression parsers.
 */
public interface CronExpressionParser<T> {
    /**
     * Parses a cron string and returns a CronExpression object.
     *
     * @param cronString The cron string to parse.
     * @return CronExpression instance containing parsed values.
     */
    CronExpression<T> parse(String cronString);
}

