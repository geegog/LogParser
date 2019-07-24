package com.ef.log.domain.model;

import com.ef.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Log extends BaseEntity {

    private LocalDateTime dateTime;

    private String ip;

    private String methodAndProtocol;

    private Integer httpStatusCode;

    private String userAgent;

}
