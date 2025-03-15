package com.assessment.deliveroo;

import com.assessment.deliveroo.parser.CronParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CronParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CronParserApplication.class, args);


        if (args.length != 1) {
            System.out.println("Usage: java -jar cron-parser.jar \"<cron_expression>\"");
            return;
        }

        try {
            CronParser.parse(args[0]);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

}
