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

    private final LogService logService;

    private final Environment env;

    @Autowired
    public CommandLineAppStartupRunner(LogService logService, Environment env) {
        this.logService = logService;
        this.env = env;
    }

    @Override
    public void run(String...args) throws Exception {

        String[] commandLineArguments = {env.getProperty("startDate"), env.getProperty("duration"), env.getProperty("threshold")};

        logger.info("startDate: {}, duration: {}, threshold: {}", commandLineArguments[0], commandLineArguments[1], commandLineArguments[2]);

        logService.load(env.getProperty("accesslog"));
        logService.getRequests(commandLineArguments);
        logger.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
    }

}
