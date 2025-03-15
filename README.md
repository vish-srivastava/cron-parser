# **Cron Parser**

## **Overview**
This project provides a cron expression parser that supports both standard and extended cron formats. It validates, parses, and extracts fields like minutes, hours, days, months, and commands from a given cron expression.

---

## **Prerequisites**
Ensure you have the following installed:
- **Java 11+** (Check with `java -version`)
- **Maven 3.6+** (Check with `mvn -version`)
- **Spring Boot 2.5+** (Included in dependencies)

---

## **Installation & Setup**
Clone the repository:
```sh
git clone https://github.com/your-repo/cron-parser.git
cd cron-parser
```

## **Build & Compile**
Install the dependencies
```sh
mvn clean install 
```

## **Run the Application**
The program expects a single cron string as an argument.
```sh
java -jar target/cron-parser-1.0.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"

```

## **Run Unit Tests**
Run all tests:
```sh
mvn test
```
Run Specific Tests: 
```sh 
mvn test -Dtest=CronParserApplicationTests
```

## **Code Structure**
```sh
cron-parser/
├── src/
│   ├── main/java/com/assessment/deliveroo/parser/
│   │   ├── CronExpressionParser.java  # Core parsing logic
│   │   ├── CronFieldExpander.java     # Expands cron fields
│   │   ├── CronParserApplication.java # Main entry point
│   ├── test/java/com/assessment/deliveroo/
│   │   ├── CronParserApplicationTests.java  # Unit tests
├── pom.xml   # Maven dependencies
├── README.md # Documentation

```