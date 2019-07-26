package com.ef.log.pojo;

import com.ef.log.domain.model.Duration;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ParamMapper {

    private Long threshold;

    private Duration duration;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public ParamMapper(String... args) {
        String startDate = args[0];
        String duration = args[1];
        String threshold = args[2];

        this.threshold = Long.valueOf(threshold);
        this.startDate = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
        this.endDate = calculateEndDate(Duration.valueOf(duration.toUpperCase()));
        this.duration = Duration.valueOf(duration.toUpperCase());

    }

    private LocalDateTime calculateEndDate(Duration duration) {
        switch (duration) {
            case HOURLY:
                endDate = startDate.plusHours(1L);
                break;
            case DAILY:
                endDate = startDate.plusDays(1L);
        }
        return endDate;
    }

}
