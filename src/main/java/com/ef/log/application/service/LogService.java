package com.ef.log.application.service;

import com.ef.log.domain.model.Log;
import com.ef.log.domain.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Stream;

@Service
public class LogService {

    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private Environment env;

    private Log createLog(String[] line) {
        Log log = new Log();
        log.setDateTime(LocalDateTime.parse(line[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        log.setHttpStatusCode(Integer.valueOf(line[3]));
        log.setIp(line[1]);
        log.setMethodAndProtocol(line[2]);
        log.setUserAgent(line[4]);
        return log;
    }

    private long fileSize(String fileLocation) throws IOException {
        long numberOfLines;
        try (Stream<String> s = Files.lines(Paths.get(fileLocation),
                Charset.forName("UTF-8"))) {
            numberOfLines = s.count();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return numberOfLines;
    }

    public void load() {
        String fileName = "access.log";

        logger.info("read started");

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName),
                Charset.forName("UTF-8"))) {

            long size = fileSize(fileName);

            logger.info("Total number of lines is {}", size);

            // read line by line
            String line;
            int counter = 0;
            ArrayList<Log> logs = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                logs.add(createLog(data));

                if ((counter + 1) % 500 == 0 || (counter + 1) == size) {
                    logger.info("Running batch ......");
                    logRepository.saveAll(logs);
                    logs.clear();
                    logger.info("Batch completed ......");
                }
                counter++;
            }
            logger.info("records written");

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
