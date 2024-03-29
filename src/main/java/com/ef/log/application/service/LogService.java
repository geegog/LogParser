package com.ef.log.application.service;

import com.ef.log.domain.model.Comment;
import com.ef.log.domain.model.Log;
import com.ef.log.domain.repository.CommentRepository;
import com.ef.log.domain.repository.LogRepository;
import com.ef.log.pojo.ParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LogService {

    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    private final LogRepository logRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public LogService(LogRepository logRepository, CommentRepository commentRepository) {
        this.logRepository = logRepository;
        this.commentRepository = commentRepository;
    }

    private Log createLog(String[] line) {
        Log log = new Log();
        log.setDateTime(LocalDateTime.parse(line[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        log.setHttpStatusCode(Integer.valueOf(line[3]));
        log.setIp(line[1]);
        log.setMethodAndProtocol(line[2]);
        log.setUserAgent(line[4]);
        return log;
    }

    public Long load(String fileName) {

        logger.info("read started");
        long counter = 0;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName),
                Charset.forName("UTF-8"))) {

            // read line by line
            String line;
            List<Log> logs = new ArrayList<>();

            while (true) {
                line = br.readLine();

                if (line == null){
                    batch(logs);
                    break;
                }

                String[] data = line.split("\\|");

                if (data.length != 5) {
                    logger.warn("incorrect record size.... data not saved!");
                    continue;
                }

                logs.add(createLog(data));

                if ((counter + 1) % 500 == 0) {
                    batch(logs);
                }
                counter++;
            }
            logger.info("records written");
            logger.info("Total number of lines is {}", counter);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return counter;
    }

    private void batch(List<Log> logs) {
        logger.info("Running batch ......");
        logRepository.saveAll(logs);
        logs.clear();
        logger.info("Batch completed ......");
    }

    public Integer getRequests(String... args) {
        ParamMapper paramMapper = new ParamMapper(args);
        logger.info(paramMapper.toString());

        List<Object[]> logList = logRepository.getLogs(paramMapper.getThreshold(), paramMapper.getStartDate(), paramMapper.getEndDate());

        logger.info("Printing {} logs................................ ", logList.size());
        logList.forEach(l -> {
            logger.info("Request log ip and dateTime is: {} | Request count is: {} ", l[0].toString(), l[1].toString());
            Comment comment = new Comment();
            comment.setCount((Long) l[1]);
            comment.setIp((l[0]).toString());
            comment.setThreshold(paramMapper.getThreshold());
            comment.setStartDate(paramMapper.getStartDate());
            comment.setEndDate(paramMapper.getEndDate());
            comment.setDescription("You have made over " + paramMapper.getThreshold() + " requests.");
            commentRepository.save(comment);
        });

        return logList.size();

    }

}
