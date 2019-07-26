package com.ef.log.console;

import com.ef.log.application.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    LogService logService;

    @Autowired
    private Environment env;

    @Override
    public void run(String...args) throws Exception {

        String[] commandLineArguments = {env.getProperty("startDate"), env.getProperty("duration"), env.getProperty("threshold")};

        logger.info("startDate: {}, duration: {}, threshold: {}", commandLineArguments[0], commandLineArguments[1], commandLineArguments[2]);

        logService.load();
        logService.getRequests(commandLineArguments);
        logger.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
    }

}
