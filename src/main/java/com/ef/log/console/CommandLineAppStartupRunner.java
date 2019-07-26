package com.ef.log.console;

import com.ef.log.application.service.LogService;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    private final LogService logService;

    private final Environment env;

    @Autowired
    public CommandLineAppStartupRunner(LogService logService, Environment env) {
        this.logService = logService;
        this.env = env;
    }

    @Override
    public void run(String... args) {

        if (args.length != 4 || env.getProperty("startDate") == null || env.getProperty("duration") == null || env.getProperty("threshold") == null) {
            logger.error("Application shutting down, 4 arguments are required. User passed {} . \n Try again by passing the correct args: For example; --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100", Arrays.toString(args));
            System.exit(0);
        }

        validateParams(env.getProperty("startDate"),
                env.getProperty("duration"),
                env.getProperty("threshold"),
                env.getProperty("accesslog"));


        String[] commandLineArguments = {env.getProperty("startDate"), env.getProperty("duration"), env.getProperty("threshold")};

        logger.info("startDate: {}, duration: {}, threshold: {}", commandLineArguments[0], commandLineArguments[1], commandLineArguments[2]);

        logService.load(env.getProperty("accesslog"));
        logService.getRequests(commandLineArguments);
        logger.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
    }

    private void validateParams(String startDate, String duration, String threshold, String filePath) {

        if (!new File(filePath).exists()) {
            logger.error("File does not exist: {} ", filePath);
            System.exit(0);
        }

        if (!GenericValidator.isDate(startDate, "yyyy-MM-dd.HH:mm:ss", true)) {
            logger.error("Wrong date format. Accepted format is {} ", "yyyy-MM-dd.HH:mm:ss");
            System.exit(0);
        }

        if (!GenericValidator.isInt(threshold)) {
            logger.error("Wrong threshold format. Accepted type is {} ", "int");
            System.exit(0);
        }

        switch (duration.toLowerCase()) {
            case "daily":
            case "hourly":
                break;
            default:
                logger.error("Duration can only be {} or {} ", "daily", "hourly");
                System.exit(0);

        }
    }

}
