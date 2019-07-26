# LogParser
Log parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration.
The app is built using Sprint Boot and gradle.

# How to run in IDE (Intellij)

1. Clone project to local system.
2. Import source code as gradle project into your IDE g.g. Intellij
3. Wait for all dependecies to be imported
4. Add arguments e.g. --accesslog=/path/to/access.log --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250
5. Run main class "com.ef.Parser"
6. You can also build project to create a runnable jar file of the application

# To run jar

java -jar /path/to/jar/parser-0.0.1-SNAPSHOT.jar --accesslog=/path/to/access.log --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250

# Run Test
1. In package "com.ef.log.application.service" run "LogServiceTest.java"
