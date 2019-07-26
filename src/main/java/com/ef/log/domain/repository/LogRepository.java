package com.ef.log.domain.repository;

import com.ef.log.domain.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    @Query("select l.ip, l.dateTime, count(l.ip) from Log l " +
            "where l.dateTime between :startDate and :endDate " +
            "group by l.ip " +
            "having count(l.ip) > :threshold")
    List<Object[]> getLogs( @Param("threshold") Long threshold,
                       @Param("startDate") LocalDateTime startDate,
                       @Param("endDate") LocalDateTime endDate);

}
