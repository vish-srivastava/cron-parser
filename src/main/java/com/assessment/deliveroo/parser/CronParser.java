package com.assessment.deliveroo.parser;

import com.assessment.deliveroo.model.CronExpression;

public class CronParser {

    /**
     * Detects the type of cron expression and returns the appropriate parser.
     */
    public static CronExpressionParser getParser(String cronString) {
        int fieldCount = cronString.split("\\s+").length;

        if (fieldCount == 6) {
            return new StandardCronExpressionParser(); // 5 fields + command
        } else {
            throw new UnsupportedOperationException("Unsupported cron format. Expected 6 fields.");
        }
    }

    /**
     * Parses and prints the cron expression.
     */
    public static void parse(String cronString) {
        CronExpressionParser parser = getParser(cronString);
        CronExpression cron = parser.parse(cronString);
        cron.printExpanded();
    }
}
