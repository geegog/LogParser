package com.ef.log.domain.model;

import com.ef.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(indexes = {@Index(name = "ip", columnList = "ip"),
        @Index(name = "date_time", columnList = "dateTime"),
        @Index(name = "ip_date_time", columnList = "ip, dateTime")})
public class Log extends BaseEntity {

    private LocalDateTime dateTime;

    private String ip;

    private String methodAndProtocol;

    private Integer httpStatusCode;

    private String userAgent;

}
